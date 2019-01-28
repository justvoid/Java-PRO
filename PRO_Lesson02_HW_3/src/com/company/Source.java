package com.company;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "source")
public class Source {

    @XmlElement
    private Organizations organizations;

    @XmlAttribute
    private String id;

    @XmlAttribute
    private Date date;

    public Organizations getOrganizations() {
        return organizations;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
}
