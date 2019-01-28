package net.ukr.just_void;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;
    static Random rng;

    public static void main(String[] args) {
        rng = new Random();
        emf = Persistence.createEntityManagerFactory("Lesson06_HW_1_1");
        em = emf.createEntityManager();
        try {
            generateGroupList(rng.nextInt(10) + 5);
            viewGroupsCriteria();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void viewGroups() {
        Query query = em.createQuery("SELECT g FROM Group g", Group.class);
        List<Group> groupList = (List<Group>) query.getResultList();
        System.out.println("Groups:");
        System.out.println("#\tName\tNumber of Students");
        for (Group group : groupList) {
            System.out.printf("#%d\t%s\t\t%d", group.getId(), group.getName(), group.getStudents().size());
            System.out.println();
        }
    }

    private static void viewGroupsCriteria() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Group> cq = cb.createQuery(Group.class);
        Root<Group> from = cq.from(Group.class);
        CriteriaQuery<Group> select = cq.select(from);
        TypedQuery<Group> query = em.createQuery(select);
        List<Group> groupList = query.getResultList();
        System.out.println("Groups:");
        System.out.println("#\tName\tNumber of Students");
        for (Group group : groupList) {
            System.out.printf("%d\t%s\t\t%d", group.getId(), group.getName(), group.getStudents().size());
            System.out.println();
        }
    }
    private static void populateGroup(Group group, int n) {
        for (int i = 0; i < n; i++) {
            String name = "Bla", phone;
            for (int j = 0; j < rng.nextInt(2); j++) name += "bla";
            name += " Bla";
            for (int j = 0; j < rng.nextInt(2); j++) name += "bla";
            name += randomizer("vich", "vko", "yev", "blenko");
            phone = randomizer("(050)", "(066)", "(099)", "(098)", "(044)", "(067)");
            phone += (rng.nextInt(900) + 100) + "-" + (rng.nextInt(10)) +
                    (rng.nextInt(10)) + "-" + (rng.nextInt(10)) + (rng.nextInt(10));
            group.addStudent(new Student(name, phone));
        }
    }

    private static List<Group> generateGroupList(int n) {
        List<Group> groupList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Group group = new Group("Gr" + (i + 401));
            populateGroup(group, rng.nextInt(25));
            groupList.add(group);
            em.getTransaction().begin();
            try {
                em.persist(group);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
            }
        }
        return groupList;
    }

    private static String randomizer(String... args) {
        Random rng = new Random();
        return args[rng.nextInt(args.length)];
    }
}