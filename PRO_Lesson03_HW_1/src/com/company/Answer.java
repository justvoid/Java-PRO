package com.company;

public class Answer {
    private String name;
    private String surname;
    private int age;
    private boolean hasPet;
    private PetType petType;
    private boolean[] petAdvantage;

    public Answer(String name, String surname, int age, boolean hasPet, PetType petType, boolean[] petAdvantage) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.hasPet = hasPet;
        this.petType = petType;
        this.petAdvantage = petAdvantage;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public boolean isHasPet() {
        return hasPet;
    }

    public PetType getPetType() {
        return petType;
    }

    public boolean[] getPetAdvantage() {
        return petAdvantage;
    }

}
