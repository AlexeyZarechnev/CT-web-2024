package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.Random;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CaptchaFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request.getMethod().equals("GET")) {
            String captcha = (String) request.getSession().getAttribute("captcha");

            if (captcha == null) {
                sendCaptcha(request, response);
                return;
            }

            if (captcha.equals("accepted")) {
                chain.doFilter(request, response);
            } else {
                String userAnswer = request.getParameter("captcha");
                String correctAnswer = (String) request.getSession().getAttribute("captcha");
                if (correctAnswer.equals(userAnswer)) {
                    request.getSession().setAttribute("captcha", "accepted");
                    chain.doFilter(request, response);
                } else {
                    sendCaptcha(request, response);
                    return;
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private void sendCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        response.setContentType("text/html");
        String captchaAnswer = Integer.toString(new Random(System.currentTimeMillis()).nextInt(899) + 100);
        request.getSession().setAttribute("captcha", captchaAnswer);
        String base64 = Base64.getEncoder().encodeToString(
            ImageUtils.toPng(
                captchaAnswer
            )
        );
        response.getWriter().print(
            "<!DOCTYPE html>\n" + //
            "<html lang=\"en\">\n" + //
            "<head>\n" + //
            "    <meta charset=\"UTF-8\">\n" + //
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + //
            "    <title>Captcha Form</title>\n" + //
            "    <link rel=\"icon\" href=\"data:;base64,=\">\n" + //
            "</head>\n" + //
            "<body>\n" + //
            "    <div class=\"captcha-form\">\n" + //
            "        <img src=\"data:image/png;base64," + base64 + "\" alt=\"Пример изображения\">\n" + //
            "        <form action=\"" + request.getRequestURI() + "\" method=\"get\">\n" + //
            "            <input type=\"text\" name=\"captcha\" placeholder=\"Enter Captcha\" required>\n" + //
            "            <button type=\"submit\">Submit</button>\n" + //
            "        </form>\n" + //
            "</body>\n" + //
            "</html>"
        );
    }
}