package ru.itmo.wp.web;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import freemarker.template.*;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.NotFoundException;
import ru.itmo.wp.web.exception.RedirectException;
import ru.itmo.wp.web.exception.UnauthorizedException;
import ru.itmo.wp.web.page.IndexPage;
import ru.itmo.wp.web.page.NotFoundPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FrontServlet extends HttpServlet {
    private static final String BASE_PAGE_PACKAGE = FrontServlet.class.getName().substring(
            0, FrontServlet.class.getName().length() - FrontServlet.class.getSimpleName().length()
    ) + "page";

    private Configuration sourceFreemarkerConfiguration;
    private Configuration targetFreemarkerConfiguration;

    private static final String APPLICATION_JSON_MIME_TYPE = "application/json";

    private Configuration newFreemarkerConfiguration(
            String templateDirName, boolean debug) throws ServletException {
        File templateDir = new File(templateDirName);
        if (!templateDir.isDirectory()) {
            return null;
        }

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        try {
            configuration.setDirectoryForTemplateLoading(templateDir);
        } catch (IOException e) {
            throw new ServletException("Can't create freemarker configuration [templateDir=" + templateDir + "].", e);
        }

        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setTemplateExceptionHandler(debug
                ? TemplateExceptionHandler.HTML_DEBUG_HANDLER
                : TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);

        return configuration;
    }

    @Override
    public void init() throws ServletException {
        sourceFreemarkerConfiguration = newFreemarkerConfiguration(getServletContext().getRealPath(".") + "/../../src/main/webapp/WEB-INF/templates", true);
        targetFreemarkerConfiguration = newFreemarkerConfiguration(getServletContext().getRealPath("WEB-INF/templates"), false);
    }

    private Template newTemplate(String name) throws ServletException {
        Template template = null;
        if (sourceFreemarkerConfiguration != null) {
            try {
                template = sourceFreemarkerConfiguration.getTemplate(name);
            } catch (TemplateNotFoundException ignored) {
                // No operations.
            } catch (IOException e) {
                throw new ServletException("Can't load template [name=" + name + "].", e);
            }
        }

        if (template == null) {
            try {
                template = targetFreemarkerConfiguration.getTemplate(name);
            } catch (TemplateNotFoundException ignored) {
                // No operations.
            } catch (IOException e) {
                throw new ServletException("Can't load template [name=" + name + "].", e);
            }
        }

        if (template == null) {
            throw new ServletException("Unable to find template [template=" + name + "].");
        }

        return template;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Route route = Route.newRoute(request);
        try {
            process(route, request, response);
        } catch (NotFoundException e) {
            try {
                process(Route.newNotFoundRoute(), request, response);
            } catch (NotFoundException e1) {
                throw new ServletException(e1);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void process(Route route,
                         HttpServletRequest request,
                         HttpServletResponse response) throws NotFoundException, ServletException, IOException {
        Class<?> pageClass;
        try {
            pageClass = Class.forName(route.getClassName());
        } catch (ClassNotFoundException e) {
            throw new NotFoundException();
        }

        Method method = null;
        Method before = null;
        Method after = null;

        for (Class<?> clazz = pageClass; 
            (before == null || method == null || after == null) && clazz != null; 
            clazz = clazz.getSuperclass()) {
            if (before == null) {
                before = searchMethod("before", clazz);
            }
            if (method == null) {
                method = searchMethod(route.getAction(), clazz);
            }
            if (after == null) {
                after = searchMethod("after", clazz);
            }
        }

        if (before == null || method == null || after == null) {
            throw new NotFoundException();
        }

        String acceptRequestHeader = request.getHeader("Accept");
        boolean json = method.getAnnotation(Json.class) != null
                || (acceptRequestHeader != null && acceptRequestHeader.startsWith(APPLICATION_JSON_MIME_TYPE));

        Object page;
        try {
            //noinspection deprecation
            page = pageClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ServletException("Can't create page [pageClass=" + pageClass + "].", e);
        }

        Map<String, Object> view = new HashMap<>();

        try {
            before.setAccessible(true);
            method.setAccessible(true);
            after.setAccessible(true);

            before.invoke(page, request, view);
            method.invoke(page, request, view);
            after.invoke(page, request, view);
        } catch (IllegalAccessException e) {
            throw new ServletException("Unable to run action [pageClass=" + pageClass + ", method=" + method + "].", e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RedirectException) {
                RedirectException redirectException = (RedirectException) cause;
                if (json) {
                    view.put("redirect", redirectException.getLocation());
                    writeJson(response, view);
                } else {
                    response.sendRedirect(redirectException.getLocation());
                }
                return;
            } else if (cause instanceof ValidationException) {
                ValidationException validationException = (ValidationException) cause;
                view.put("error", validationException.getMessage());
            } else if (cause instanceof UnauthorizedException) {
                view.put("error", "You must be authorized to perform this action.");
            } else {
                throw new ServletException("Unable to run action [pageClass=" + pageClass + ", method=" + method + "].", e);
            }
        }

        if (json) {
            writeJson(response, view);
        } else {
            Template template = newTemplate(pageClass.getSimpleName() + ".ftlh");

            response.setContentType("text/html");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            try {
                template.process(view, response.getWriter());
            } catch (TemplateException e) {
                throw new ServletException("Can't render template [pageClass=" + pageClass + ", method=" + method + "].", e);
            }
        }
    }

    private void writeJson(HttpServletResponse response, Map<String, Object> view) throws IOException {
        response.setContentType(APPLICATION_JSON_MIME_TYPE);
        response.getWriter().print(new Gson().toJson(view));
    }

    private Method searchMethod(String name, Class<?> clazz) {
        try {
            return clazz.getDeclaredMethod(name, HttpServletRequest.class, Map.class);
        } catch (NoSuchMethodException ignored) { /* No operation */}
        return null;
    }

    private static final class Route {
        private static final String DEFAULT_ACTION = "action";

        private final String className;
        private final String action;

        private Route(String className, String action) {
            this.className = className;
            this.action = action;
        }

        private static Route newNotFoundRoute() {
            return new Route(
                    NotFoundPage.class.getName(),
                    DEFAULT_ACTION
            );
        }

        private static Route newIndexRoute() {
            return new Route(
                    IndexPage.class.getName(),
                    DEFAULT_ACTION
            );
        }

        private String getClassName() {
            return className;
        }

        private String getAction() {
            return action;
        }

        private static Route newRoute(HttpServletRequest request) {
            String uri = request.getRequestURI();

            StringBuilder className = new StringBuilder(BASE_PAGE_PACKAGE);
            Arrays.stream(uri.split("/")).filter(s -> !s.isEmpty()).forEach(s -> {
                className.append('.');
                className.append(s);
            });

            if (className.toString().equals(BASE_PAGE_PACKAGE)) {
                return newIndexRoute();
            }

            int lastPeriodPos = className.lastIndexOf(".");
            className.setCharAt(lastPeriodPos + 1, Character.toUpperCase(className.charAt(lastPeriodPos + 1)));
            className.append("Page");

            String action = request.getParameter("action");
            if (Strings.isNullOrEmpty(action)) {
                action = DEFAULT_ACTION;
            }

            return new Route(className.toString(), action);
        }
    }
}
