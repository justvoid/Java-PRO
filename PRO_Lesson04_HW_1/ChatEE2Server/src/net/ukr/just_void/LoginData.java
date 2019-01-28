package net.ukr.just_void;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// Class holds all user login data and tracks if users are online
public class LoginData {
    private static final Map<String, String> loginMap;
    private static Map<String, Boolean> onlineMap;

    static {
        loginMap = new HashMap<>();
        loginMap.put("user","1234");
        loginMap.put("user2","1234");
        loginMap.put("admin","admin");
        loginMap.put("guest","");
    }

    public static boolean checkCredentials(String login, String password) {
        return loginMap.get(login).equals(password);
    }

    public static boolean checkCredentials(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        return loginMap.get(login).equals(password);
    }

    public static boolean userExists(String userName) {
        return loginMap.containsKey(userName);
    }

    public static Set<String> getUserList() {
        return loginMap.keySet();
    }

    public static Map<String, Boolean> getOnlineMap() {
        return onlineMap;
    }

    public static void setOnlineMap(Map<String, Boolean> onlineMap) {
        LoginData.onlineMap = onlineMap;
    }
}
