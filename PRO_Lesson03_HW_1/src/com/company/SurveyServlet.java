package com.company;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Survey", urlPatterns = "/survey")
public class SurveyServlet extends HttpServlet {
    private static final String ANSW = "<html>" +
            "<head><title>Survey Results</title></head>" +
            "<body><h3>Survey Results</h3>" +
            "<p>Total number of surveys taken: %d<br><br>" +
            "Pet owners: %s<br><br>" +
            "Pets by Type: <br>" +
            "Dogs: %s, Cats: %s, Parrots: %s, Other: %s<br><br>" +
            "Pet Advantages: <br>" +
            "Protection: %s, Love: %s, Convenience: %s, Stress Relief: %s, Leasure: %s</p></body></html>";
    private ArrayList<Answer> answers = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addAnswer(readAnswer(req));
        writeResponse(resp);
    }

    private synchronized void addAnswer(Answer answer) {
        if (answer != null) {
            answers.add(answer);
        }
    }


    private void writeResponse(HttpServletResponse resp) throws IOException {
        int n = answers.size();
        Map<String, Integer> resMap = generateResultMap();
        resp.getWriter().printf(ANSW, n,
                getFromMap("petOwners", resMap, n), getFromMap("dogOwners", resMap, n),
                getFromMap("catOwners", resMap, n), getFromMap("parrotOwners", resMap, n),
                getFromMap("otherOwners", resMap, n), getFromMap("petAdvantage0", resMap, n),
                getFromMap("petAdvantage1", resMap, n), getFromMap("petAdvantage2", resMap, n),
                getFromMap("petAdvantage3", resMap, n), getFromMap("petAdvantage4", resMap, n));

    }

    private String getFromMap(String key, Map<String, Integer> map, int total) {
        String str = String.format(map.getOrDefault(key, 0) + " ( %.1f%% )", map.getOrDefault(key, 0) * 100.0 / total);
        return str;
    }

    private Map<String, Integer> generateResultMap() {
        Map<String, Integer> resultsMap = new HashMap<>();
        countPetOwners(resultsMap);
        countPetOwnersByType(resultsMap);
        countPetAdvantages(resultsMap);
        return resultsMap;
    }

    private void countPetOwners(Map<String, Integer> resultsMap) {
        for (Answer answer : answers) {
            if (answer.isHasPet()) {
                resultsMap.compute("petOwners", (k, v) -> v == null ? 1 : (resultsMap.get(k) + 1));
            }
        }
    }

    private void countPetOwnersByType(Map<String, Integer> resultsMap) {
        for (Answer answer : answers) {
            switch (answer.getPetType()) {
                case CAT: {
                    resultsMap.compute("catOwners", (k, v) -> v == null ? 1 : (resultsMap.get(k) + 1));
                    break;
                }
                case DOG: {
                    resultsMap.compute("dogOwners", (k, v) -> v == null ? 1 : (resultsMap.get(k) + 1));
                    break;
                }
                case PARROT: {
                    resultsMap.compute("parrotOwners", (k, v) -> v == null ? 1 : (resultsMap.get(k) + 1));
                    break;
                }
                case OTHER: {
                    resultsMap.compute("otherOwners", (k, v) -> v == null ? 1 : (resultsMap.get(k) + 1));
                    break;
                }
            }
        }
    }

    private void countPetAdvantages(Map<String, Integer> resultsMap) {
        for (Answer answer : answers) {
            for (int i = 0; i < answer.getPetAdvantage().length; i++) {
                if (answer.getPetAdvantage()[i]) {
                    resultsMap.compute("petAdvantage" + i, (k, v) -> v == null ? 1 : (resultsMap.get(k) + 1));
                }
            }
        }
    }

    private Answer readAnswer(HttpServletRequest req) {
        try {
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            int age = Integer.parseInt(req.getParameter("age"));
            boolean hasPet = Boolean.valueOf(req.getParameter("hasPet"));
            PetType petType = PetType.valueOf(req.getParameter("petType"));
            int petAdvantageNumber = Integer.parseInt(req.getParameter("petAdvantageNumber"));
            boolean[] petAdvantage = new boolean[petAdvantageNumber];
            for (int i = 0; i < petAdvantageNumber; i++) {
                petAdvantage[i] = Boolean.valueOf(req.getParameter("petAdvantage" + i));
            }
            return new Answer(name, surname, age, hasPet, petType, petAdvantage);
        } catch (Exception e) {
            return null;
        }
    }
}
