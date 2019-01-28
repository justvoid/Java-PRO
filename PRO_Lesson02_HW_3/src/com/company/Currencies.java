package com.company;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "currencies")
public class Currencies {

    private List<Currency> currencyList = new ArrayList<>();

    public void add(Currency currency) {
        currencyList.add(currency);
    }

    @XmlElement(name = "c")
    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }
}
