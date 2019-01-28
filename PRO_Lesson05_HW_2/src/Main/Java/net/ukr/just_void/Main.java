package net.ukr.just_void;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Connection conn;

    public static void main(String[] args) {
        DbCredentials dbc = new DbCredentials();
        try {
            try {
                conn = DriverManager.getConnection(dbc.getDbUrl(), dbc.getDbUser(), dbc.getDbPassword());
                menu();
            } finally {
                if (conn != null) { 
                    conn.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void menu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String selection;
        do {
            System.out.println();
            System.out.println("======= Menu ======");
            System.out.println("(0) Initialize DB");
            System.out.println("(1) Add new client");
            System.out.println("(2) Add new item");
            System.out.println("(3) Add new order");
            System.out.println("(4) Show clients");
            System.out.println("(5) Show items");
            System.out.println("(6) Show orders (raw)");
            System.out.println("(7) Show orders (processed)");
            System.out.println("(8) Show all orders for a client (processed)");
            System.out.println("(9) Exit");
            selection = sc.nextLine();
            switch (selection) {
                case "0": {
                    initializeDB();
                    populateClients(50);
                    populateItems(50);
                }
                case "1": {
                    addClient();
                    break;
                }
                case "2": {
                    addItem();
                    break;
                }
                case "3": {
                    addOrder();
                    break;
                }
                case "4": {
                    displaySQLTable("SELECT * FROM CLIENTS");
                    break;
                }
                case "5": {
                    displaySQLTable("SELECT * FROM ITEMS");
                    break;
                }
                case "6": {
                    displaySQLTable("SELECT * FROM ORDERS");
                    break;
                }
                case "7": {
                    displaySQLTable("SELECT orders.id AS order_id, clients.id AS client_id, clients.name AS client_name, clients.phone AS phone, " +
                            "items.id AS item_id, items.name AS item, orders.price from orders, clients, items where " +
                            "orders.client_id = clients.id AND clients.phone = (SELECT phone FROM clients where id=client_id) " +
                            "AND items.id = orders.item_id");
                    break;
                }
                case "8": {
                    showOrdersForClient();
                    break;
                }
                default: {
                    break;
                }
            }
        } while (!selection.equals("9"));
    }

    private static void initializeDB() throws SQLException {
        initializeClients();
        initializeItems();
        initializeOrders();
    }

    private static void initializeClients() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS clients");
            st.execute("CREATE TABLE clients (" +
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(30) DEFAULT NULL," +
                    "phone VARCHAR(14) NOT NULL)");
        }
    }

    private static void initializeItems() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS items");
            st.execute("CREATE TABLE items (" +
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(30) NOT NULL," +
                    "price DOUBLE(8,2) UNSIGNED DEFAULT NULL)");
        }
    }

    private static void initializeOrders() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS orders");
            st.execute("CREATE TABLE orders (" +
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "client_id INT NOT NULL," +
                    "item_id INT NOT NULL," +
                    "price DOUBLE(8,2) UNSIGNED NOT NULL)");
        }
    }

    private static void populateClients(int n) throws SQLException {
        Random rng = new Random();
        conn.setAutoCommit(false);
        try {
            try {
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO clients (name, phone) VALUES (?, ?)")) {
                    for (int i = 0; i < n; i++) {
                        String name = "Bla", phone;
                        for (int j = 0; j < rng.nextInt(2); j++) name += "bla";
                        name += " Bla";
                        for (int j = 0; j < rng.nextInt(2); j++) name += "bla";
                        name += randomizer("vich", "vko", "yev", "blenko");
                        phone = randomizer("(050)", "(066)", "(099)", "(098)", "(044)", "(067)");
                        phone += (rng.nextInt(900) + 100) + "-" + (rng.nextInt(10)) +
                                (rng.nextInt(10)) + "-" + (rng.nextInt(10)) + (rng.nextInt(10));
                        ps.setString(1, name);
                        ps.setString(2, phone);
                        ps.executeUpdate();
                    }
                    conn.commit();
                }
            } finally {
                conn.rollback();
            }
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private static void addClient() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the client's name:");
        String name = sc.nextLine();
        System.out.println("Please enter the client's phone number:");
        String phone = sc.nextLine();
        try (Statement st = conn.createStatement()) {
            st.execute("INSERT INTO clients (name, phone) VALUES (\"" + name + "\", \"" + phone + "\")");
        }
    }

    private static void addItem() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter item name:");
        String name = sc.nextLine();
        System.out.println("Please enter item price:");
        double price = sc.nextDouble();
        try (Statement st = conn.createStatement()) {
            st.execute("INSERT INTO items (name, price) VALUES (\"" + name + "\", " + price + ")");
        }
    }

    private static void addOrder() throws SQLException {
        displaySQLTable("SELECT * FROM CLIENTS");
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the client id:");
        int clientID = sc.nextInt();
        displaySQLTable("SELECT * FROM ITEMS");
        System.out.println("Please enter item id:");
        int itemID = sc.nextInt();
        try (Statement st = conn.createStatement()) {
            st.execute("INSERT INTO orders (client_id, item_id, price) VALUES (\"" + clientID + "\", \""
                    + itemID + "\", " + getSingleDataPoint("SELECT price FROM items WHERE id="+ itemID) + ")");
        }
    }

    private static void populateItems(int n) throws SQLException {
        Random rng = new Random();
        conn.setAutoCommit(false);
        try {
            try {
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO items (name, price) VALUES (?, ?)")) {
                    for (int i = 0; i < n; i++) {
                        String name;
                        double price;
                        name = randomizer("Samsung ", "LG ", "Xiaomi ", "Apple ", "Nokia ", "Huawei ", "Sony ");
                        name += randomizer("Super", "Ultra", "Power");
                        name += randomizer("phone ", "book ", "note ");
                        name += rng.nextInt(7) != 0 ? rng.nextInt(10) + 5 : "X";
                        name += randomizer(" Pro", " XL", "S", " Turbo", "", "+", " Plus");
                        price = (rng.nextInt(150000) + 15000) / 100.0;
                        ps.setString(1, name);
                        ps.setDouble(2, price);
                        ps.executeUpdate();
                    }
                    conn.commit();
                }
            } finally {
                conn.rollback();
            }
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private static void displaySQLTable(String sqlQuery) throws SQLException {
        try (Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery(sqlQuery)) {
                ResultSetMetaData md = rs.getMetaData();
                int[] colWidth = new int[md.getColumnCount()];
                System.out.println("==================" + md.getTableName(1) + "==================");
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    colWidth[i - 1] = Integer.parseInt(getSingleDataPoint("SELECT MAX(LENGTH(" +
                            md.getTableName(i) + "." + md.getColumnName(i) + ")) from " + md.getTableName(i))) + 10;
                    System.out.printf("%-" + (colWidth[i - 1]) + "s", md.getColumnLabel(i));
                }
                System.out.println();
                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.printf("%-" + (colWidth[i - 1]) + "s", rs.getString(i));
                    }
                    System.out.println();
                }
                System.out.println("================================================");
            }
        }
    }

    private static void showOrdersForClient() throws SQLException {
        displaySQLTable("SELECT * FROM CLIENTS");
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the client id:");
        int clientID = sc.nextInt();
        displaySQLTable("SELECT orders.id AS order_id, clients.id AS client_id, clients.name AS client_name, " +
                "clients.phone AS phone, items.id AS item_id, items.name AS item, orders.price from orders, clients, items where " +
                "orders.client_id = clients.id AND clients.phone = (SELECT phone FROM clients where id=client_id) " +
                "AND items.id = orders.item_id AND clients.id = " + clientID);
    }


    private static String getSingleDataPoint(String sqlQuery) throws SQLException {
        try (Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery(sqlQuery)) {
                rs.next();
                return rs.getString(1);
            }
        }
    }

    private static String randomizer(String... args) {
        Random rng = new Random();
        return args[rng.nextInt(args.length)];
    }
}


