package ru.itmo.wp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class MessageServlet extends HttpServlet {

    private final List<Message> messages = new ArrayList<>();

    private static class Message {
        private final String user;
        private final String text;

        private Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }

    private void printJSON(Object data, HttpServletResponse response) throws IOException {
        String json = new Gson().toJson(data);
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter writer = response.getWriter()) { 
            writer.print(json); 
            writer.flush();
        }
    }

    private void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getContentLength() > 0) {
            String user = request.getParameter("user");
            if (!MessageServlet.isEmptyString(user)) {
                request.getSession().setAttribute("user",  user);
            }
        }

        String name = (String) request.getSession().getAttribute("user");
        String user = "";
        if (name != null) 
            user = name;
        printJSON(user, response);
    }

    private void addMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = (String) request.getSession().getAttribute("user");
        if (user.equals(null)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (request.getContentLength() > 0) {
            String text = request.getParameter("text");
            if (!MessageServlet.isEmptyString(text))
                messages.add(new Message(user, text));
        }
    }

    private static boolean isEmptyString(String str) {
        return str.isBlank();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("auth")) {

            auth(request, response);

        } else if (uri.endsWith("findAll")) {

            printJSON(messages, response);

        } else if (uri.endsWith("add")) {

            addMessage(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
