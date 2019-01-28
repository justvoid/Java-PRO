package com.company;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeAdapter extends XmlAdapter<String, LocalTime> {
    @Override
    public LocalTime unmarshal(String v) {
        return LocalTime.parse(v, DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public String marshal(LocalTime v) {
        return v.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
