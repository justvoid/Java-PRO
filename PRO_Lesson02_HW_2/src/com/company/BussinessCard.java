package com.company;

import java.util.Arrays;

public class BussinessCard {
    private String name;
    private String surname;
    private String[] phones;
    private String[] sites;
    private Address address;

    public BussinessCard(String name) {
        this.name = name;
    }

    public BussinessCard(String name, String surname, String[] phones, String[] sites, Address address) {
        this.name = name;
        this.surname = surname;
        this.phones = phones;
        sites = sites;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String[] getPhones() {
        return phones;
    }

    public void setPhones(String[] phones) {
        this.phones = phones;
    }

    public String[] getSites() {
        return sites;
    }

    public void setSites(String[] sites) {
        sites = sites;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "----BussinessCard----\n" +
                "name:\t\t" + name + '\n' +
                "surname:\t" + surname + '\n' +
                "phones:\t\t"  + Arrays.toString(phones) + '\n' +
                "sites:\t\t" + Arrays.toString(sites) + '\n' +
                address + '\n' +
                "---------------------";
    }
}
