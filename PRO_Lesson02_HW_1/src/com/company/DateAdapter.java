package com.company;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String v) {
        return LocalDate.parse(v, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Override
    public String marshal(LocalDate v) {
        return v.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
