package ru.itmo.wp.web.page;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Strings;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.UnauthorizedException;

public abstract class AbstractPage {

    protected final UserService userService = new UserService();
    protected HttpServletRequest request;

    protected void action(HttpServletRequest request, Map<String, Object> view) { /* No operation */ }

    protected void before(HttpServletRequest request, Map<String, Object> view) throws UnauthorizedException {
        this.request = request;
        User user = getUser();
        if (user != null) {
            setUser(userService.find(user.getId()));
        }
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) { 
        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }
        String message = getMessage();
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
        view.put("userCount", userService.getUserCount());
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    protected String getMessage() {
        return (String) request.getSession().getAttribute("message");
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    protected User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    protected void checkAuthorization() throws UnauthorizedException {
        if (getUser() == null) {
            throw new UnauthorizedException();
        }
    }

}
