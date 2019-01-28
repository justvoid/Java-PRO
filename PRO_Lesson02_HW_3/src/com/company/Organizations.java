package com.company;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "organizations")
public class Organizations {

    @XmlElement(name = "organization")
    private List<Organization> organization = new ArrayList<>();

    public void addOrganization(Organization org) {
        organization.add(org);
    }

    public Organization getOrganization(String name) {
        for (Organization i : organization) {
            if (i.getTitle().getOrganizationName().equals(name)) {
                return i;
            }
        }
        return null;
    }
}
