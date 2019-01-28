package net.ukr.just_void;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    private static String login;
    private static String password;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter your login: ");
            login = scanner.nextLine();
            System.out.println("Enter your password: ");
            password = scanner.nextLine();
            // Auth request via POST
            if (Utils.sendPostRequest("/login", "login", login, "password", password) != 200) {
                System.out.println("ERROR! Incorrect login and/or password");
                return;
            }
            startGetThread();
            System.out.println("Enter your message: ");
            while (true) {
                String text = scanner.nextLine();
                if (text.isEmpty()) break;
                int res = parseMessage(text);
                if (res != 200) { // 200 OK
                    System.out.println("HTTP error occurred: " + res);
                    return;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void startGetThread() {
        Thread th = new Thread(new GetThread(login, password));
        th.setDaemon(true);
        th.start();
    }

    /*
    Parse input message for chat commands:
    ? - List all chat users
    ?Name - Check if user Name is online
    @Name Msg - send a private message Msg to Name OR post a message Msg to group Name
    @Group+Name(+Name2...) Msg - Creates a group Group if does not exist, adds the sender and all
                                 specified users to it and sends a message Msg to group Group
    Example:
    @Group1+admin+guest Hello - Creates a group Group1, adds the sender, admin and guest to it
                                and sends a message "Hello" to everyone in the group
    */
    private static int parseMessage(String input) throws IOException {
        char command = input.charAt(0);
        if (command == '@' || command == '?') {
            int delim = input.indexOf(" ");
            String param = input.substring(1, delim > 0 ? delim : input.length());
            String addressee = param;
            String messageText = delim > 0 ? input.substring(delim + 1) : null;

            switch (command) {
                case '?': {
                    return checkStatus(param);
                }
                case '@': {
                    int n = param.indexOf('+');
                    if (n != -1) {
                        addressee = param.substring(0, n);
                        Utils.printGroupErrorCodes(Utils.sendPostRequest("/group", "login", login,
                                "password", password, "group_name",
                                param.substring(0, n), "param", param.substring(n + 1)));
                    }
                    return sendMessage(messageText, addressee);
                }
            }
        }
        return sendMessage(input, null);
    }


    private static int sendMessage(String text, String to) throws IOException {
        if (text == null || text.trim().equals("")) return 200;
        Message m = new Message(login, to, text);
        int res = m.send(Utils.getURL() + "/add");
        return res;
    }

    // Checks for Online stats
    private static int checkStatus(String param) throws IOException {
        URL url = new URL(Utils.getURL() + "/status?who=" + param + "&login=" + login + "&password=" + password);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String resp;
        try (InputStream is = conn.getInputStream()) {
            Gson gson = new GsonBuilder().create();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buff = new byte[10240];
            int r;
            do {
                r = is.read(buff);
                if (r > 0) bos.write(buff, 0, r);
            } while (r != -1);
            resp = new String(bos.toByteArray(), StandardCharsets.UTF_8);
            Map<String, Boolean> onlineMap = gson.fromJson(resp, TreeMap.class);
            Utils.printOnlineStats(onlineMap);
        }
        return conn.getResponseCode();
    }
}
