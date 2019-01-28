package com.company;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Trains {

    @XmlElement(name="train")
    private List<Train> trainList = new ArrayList<>();

    public void addTrain(Train train) {
        trainList.add(train);
    }

    public List<Train> getTrainList() {
        return trainList;
    }
}
