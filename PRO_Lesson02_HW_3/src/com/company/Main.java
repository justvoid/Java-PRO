package com.company;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String url = "http://resources.finance.ua/ru/public/currency-cash.xml";
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Source.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Source source = (Source) unmarshaller.unmarshal(new URL(url));
            marshaller.marshal(source, new File("currency-cash.xml"));
//            marshaller.marshal(source, System.out);
            showRates(source, "Артада");
        } catch (JAXBException | MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public static void showRates(Source source, String bankName) {
        Organization bank = source.getOrganizations().getOrganization(bankName);
        if (bank != null) {
            System.out.println(bankName);
            List<Currency> currencyList = bank.getCurrencies().getCurrencyList();
            for (Currency currency : currencyList) {
                System.out.println("UAH/" + currency.getId() + ": Купівля " + currency.getSellRate() + " Продаж " +currency.getBuyRate());
            }
        } else {
            System.out.println("Ошибка! Такой банк не найден");
        }
    }
}
