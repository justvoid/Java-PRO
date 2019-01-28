package net.ukr.just_void;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroupServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!LoginData.checkCredentials(req)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            String login = req.getParameter("login");
            String groupName = req.getParameter("group_name");
            String param = req.getParameter("param");
            if (LoginData.userExists(groupName)) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else if (!Groups.exists(groupName) || Groups.userIsInGroup(login, groupName)) {
                Groups.addUser(groupName, login, param.split(" "));
            } else resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
