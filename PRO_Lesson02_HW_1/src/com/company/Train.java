package com.company;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalTime;

//@XmlRootElement(name = "train")
@XmlType(name = "train", propOrder = {"from", "to", "date", "time"})
public class Train {

    private int id;
    private String from;
    private String to;
    private LocalDate date;
    private LocalTime time;

    public Train(int id, String from, String to, LocalDate date, LocalTime time) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
    }

    public Train() {
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @XmlElement
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @XmlJavaTypeAdapter(DateAdapter.class)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @XmlElement(name = "departure")
    @XmlJavaTypeAdapter(TimeAdapter.class)
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Train #" + id + " from " + from + " to " + to + " departing at " + date + " " + time;
    }
}
