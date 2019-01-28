package com.company;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "organization")
public class Organization {

    private Currencies currencies;

    private Title title;

    private String id;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public Currencies getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Currencies currencies) {
        this.currencies = currencies;
    }

    @XmlElement
    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}
