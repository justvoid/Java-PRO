package net.ukr.just_void;

import java.util.ArrayList;
import java.util.List;

public class JsonMessages {
    private final List<Message> list;

    public JsonMessages(List<Message> sourceList, int fromIndex, String login) {
        this.list = new ArrayList<>();
        for (int i = fromIndex; i < sourceList.size(); i++){
            String to = sourceList.get(i).getTo();
            String from = sourceList.get(i).getFrom();
            if (to == null || to.equals(login) || from.equals(login) || Groups.userIsInGroup(login, to)) {
                list.add(sourceList.get(i));
            }
        }
    }
}
