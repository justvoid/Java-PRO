package net.ukr.just_void;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GetListServlet extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();
    private static Map<String, Long> lastLoginMap;  // Map recording time when user was last seen online
    private static final int OFFLINE_TIMEOUT = 5000;    // Users will be considered Offline after ...ms

    // Initialize LastLoginMap
    static {
        lastLoginMap = new TreeMap<>();
        for (String user : LoginData.getUserList()) {
            lastLoginMap.put(user, 0L);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (!LoginData.checkCredentials(login, password)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            lastLoginMap.put(login, System.currentTimeMillis());
            LoginData.setOnlineMap(generateOnlineMap());

            String fromStr = req.getParameter("from");
            int from = 0;
            try {
                from = Integer.parseInt(fromStr);
            } catch (Exception ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String json = msgList.toJSON(from, login);
            if (json != null) {
                OutputStream os = resp.getOutputStream();
                byte[] buf = json.getBytes(StandardCharsets.UTF_8);
                os.write(buf);
            }
        }
    }

    // Generate User Online Map based on lastLoginMap and OFFLINE_TIMEOUT. true = ONLINE false = OFFLINE
    private Map<String, Boolean> generateOnlineMap() {
        Map<String, Boolean> onlineMap = new TreeMap<>();
        Set<String> loginSet = lastLoginMap.keySet();
        long timeNow = System.currentTimeMillis();
        for (String user : loginSet) {
            onlineMap.put(user, timeNow - lastLoginMap.get(user) < OFFLINE_TIMEOUT);
        }
        return onlineMap;
    }
}
