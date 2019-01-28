package net.ukr.just_void;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class StatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!LoginData.checkCredentials(req)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            String who = req.getParameter("who");
            Map<String, Boolean> responseMap = generateResponseMap(who);
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(responseMap);
            try (OutputStream os = resp.getOutputStream()) {
                byte[] buf = json.getBytes(StandardCharsets.UTF_8);
                os.write(buf);
            }
        }

    }

    private Map<String, Boolean> generateResponseMap(String who) {
        Map<String, Boolean> onlineMap = LoginData.getOnlineMap();
        Map<String, Boolean> responseMap = new TreeMap<>();
        if (who == "") {
            return onlineMap;
        } else if (!onlineMap.containsKey(who)){
            return null;
        }
        responseMap.put(who, onlineMap.get(who));
        return responseMap;
    }
}
