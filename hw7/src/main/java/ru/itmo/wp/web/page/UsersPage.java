package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class UsersPage extends AbstractPage {

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void isFromAdmin(Map<String, Object> view) throws UnauthorizedException {
        if (!getUser().isAdmin()) {
            view.put("error", "You are not an admin!");
            throw new UnauthorizedException();
        }
    }

    private void makeAdmin(HttpServletRequest request, Map<String, Object> view) throws UnauthorizedException{
        checkAuthorization();
        isFromAdmin(view);
        userService.setAdmin(Long.parseLong(request.getParameter("userId")), true);  
    }

    private void removeAdmin(HttpServletRequest request, Map<String, Object> view) throws UnauthorizedException {
        checkAuthorization();
        isFromAdmin(view);
        userService.setAdmin(Long.parseLong(request.getParameter("userId")), false);
    }
}
