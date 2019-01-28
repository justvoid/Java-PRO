package net.ukr.just_void;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.*;

public class DishesController {
    private EntityManager em;
    private Random rng;

    public DishesController(EntityManagerFactory emf) {
        em = emf.createEntityManager();
        rng = new Random();
    }

    public void showDishSetWeightLimit(int weight) {
        List<Dish> menu = new ArrayList<>();
        int currentWeight = 0;
        Query query = em.createQuery("SELECT d FROM Dish d WHERE d.weight <= :weight", Dish.class);
        query.setParameter("weight", weight);
        List<Dish> dishList = query.getResultList();
        dishList.sort(Comparator.comparing(Dish::getWeight));
        for (int i = dishList.size() - 1; i >= 0; i--) {
            Dish dish = dishList.get(i);
            if (currentWeight + dish.getWeight() <= weight) {
                currentWeight += dish.getWeight();
                menu.add(dish);
            }
        }
        menu.sort(Comparator.comparing(Dish::getId));
        drawMenu(menu, "Dish set weighting " + currentWeight);
    }

    public void selectByPrice() {
        Scanner sc = new Scanner(System.in);
        double min, max;
        System.out.printf("Enter min price:");
        min = sc.nextDouble();
        System.out.printf("Enter max price:");
        max = sc.nextDouble();
        Query query = em.createQuery("SELECT d FROM Dish d WHERE d.price >= :min AND d.price <= :max", Dish.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        List<Dish> menu = (List<Dish>) query.getResultList();
        drawMenu(menu, "Dishes that cost between " + min + " and " + max);
    }

    public void selectAndShowMenu(String queryString, String title) {
        Query query = em.createQuery(queryString, Dish.class);
        List<Dish> menu = (List<Dish>) query.getResultList();
        drawMenu(menu, title);
    }

    public void generateMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of dishes you want to generate:");
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            String name = randomizer("Bla", "La");
            int weight;
            double price;
            Boolean discount = rng.nextBoolean();
            for (int j = 0; j < rng.nextInt(4); j++) name += randomizer("bla", "la", " Bla", "-la");
            weight = rng.nextInt(20) * 20 + 50;
            price = rng.nextInt(200) + 50 + rng.nextInt(100) / 100.0;
            addDishToDb(new Dish(name, weight, price, discount));
        }
    }

    public void manualAddDish() {
        Scanner sc = new Scanner(System.in);
        String name;
        int weight;
        double price;
        Boolean discount;
        System.out.println("Enter the dish name:");
        name = sc.nextLine();
        System.out.println("Enter the dish weight(in grams):");
        weight = sc.nextInt();
        System.out.println("Enter the dish price:");
        price = sc.nextDouble();
        System.out.println("Is the discount applicable? (true/false):");
        discount = sc.nextBoolean();
        addDishToDb(new Dish(name, weight, price, discount));
    }

    private void addDishToDb(Dish dish) {
        em.getTransaction().begin();
        try {
            em.persist(dish);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    private void drawMenu(List<Dish> menu, String title) {
        System.out.println("============ " + title + " ============");
        System.out.printf("%s\t%-15s\t%5s\t%7s\t\t%s", "#", "Name", "Weight", "Price", "Discount");
        System.out.println();
        for (Dish dish : menu) {
            System.out.printf("%d\t%-15s\t%5d\t%7.2f\t\t%s", dish.getId(), dish.getName(), dish.getWeight(), dish.getPrice(), (dish.isDiscount() ? "+" : ""));
            System.out.println();
        }
    }

    private String randomizer(String... args) {
        Random rng = new Random();
        return args[rng.nextInt(args.length)];
    }
}
