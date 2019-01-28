package com.company;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Trains.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Unmarshall и вывод на экран
            Trains trains = (Trains) unmarshaller.unmarshal(new File("trains.xml"));
            marshaller.marshal(trains, System.out);

            // Выведем поезда, которые отправляются сегодя с 15 до 19
            System.out.println("Trains departing today from 15 to 19");
            printTrains(trains, LocalDate.now(), LocalTime.of(15, 0), LocalTime.of(19, 0));

            // Сгенерируем свое распсиание и выведем на экран и в файл
            Trains trainsRandom = generateTrains(5);
            marshaller.marshal(trainsRandom, System.out);
            marshaller.marshal(trainsRandom, new File("random-trains.xml"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printTrains(Trains trains, LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        List<Train> trainList = trains.getTrainList();
        for (Train train : trainList) {
            LocalDateTime departure = LocalDateTime.of(train.getDate(), train.getTime());
            if (departure.toLocalDate().equals(date)) {
                if (departure.toLocalTime().isAfter(timeStart) && departure.toLocalTime().isBefore(timeEnd)) {
                    System.out.println(train);
                }
            }
        }
    }

    public static Trains generateTrains(int n) {
        int trainNum = 0;
        Trains trains = new Trains();
        Random random = new Random();
        while (n-- > 0) {
            StringBuilder from = new StringBuilder("Bla");
            StringBuilder to = new StringBuilder("Bla");
            int month = random.nextInt(12) + 1;
            YearMonth yearMonth = YearMonth.of(2018, month);
            int day = random.nextInt(yearMonth.lengthOfMonth()) + 1;
            LocalDate date = LocalDate.of(2018, month, day);
            LocalTime time = LocalTime.of(random.nextInt(23), random.nextInt(60));
            int fromCityNameLength = random.nextInt(5);
            int toCityNameLength = random.nextInt(5);
            for (int i = 0; i <= Integer.max(fromCityNameLength, toCityNameLength); i++) {
                if (i <= fromCityNameLength) {
                    if (random.nextInt(15 - fromCityNameLength * 2) == 0) {
                        from.append("-Bla");
                    } else from.append("bla");
                }
                if (i <= toCityNameLength) {
                    if (random.nextInt(15 - toCityNameLength * 2) == 0) {
                        to.append("-Bla");
                    } else {
                        to.append("bla");
                    }
                }
            }
            trains.addTrain(new Train(++trainNum, from.toString(), to.toString(), date, time));
        }
        return trains;
    }

}
