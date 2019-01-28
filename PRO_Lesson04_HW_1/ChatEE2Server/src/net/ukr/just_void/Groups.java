package net.ukr.just_void;

import java.util.*;

// Tracks all chat groups and their members
public class Groups {
    private static Map<String, TreeSet<String>> groupList = new HashMap<>();

    public static void addUser(String group, String login, String ... params) {
        groupList.putIfAbsent(group, new TreeSet<>(Arrays.asList(new String[]{login})));
        Set<String> groupMembers = groupList.get(group);
        for (String i : params) {
            if (LoginData.userExists(i)) {
                groupMembers.add(i);
            }
        }
    }

    public static boolean exists(String group) {
        return groupList.containsKey(group);
    }

    public static boolean userIsInGroup(String username, String group) {
        if (!groupList.containsKey(group)) {
            return false;
        }
        if (groupList.get(group).contains(username)) {
            return true;
        } else return false;
    }
}
