package net.ukr.just_void;

import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    private static Connection conn;

    public static void main(String[] args) throws SQLException {
        DbCredentials dbc = new DbCredentials();
        try {
            conn = DriverManager.getConnection(dbc.getDbUrl(), dbc.getDbUser(), dbc.getDbPassword());
            initializeDB();
            populateDB(50);
            listFlats();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    private static void initializeDB() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS flats");
            st.execute("CREATE TABLE flats (" +
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "district VARCHAR(20) DEFAULT NULL," +
                    "address VARCHAR(60) NOT NULL," +
                    "area DOUBLE(5,1) UNSIGNED NOT NULL," +
                    "rooms TINYINT UNSIGNED NOT NULL," +
                    "price DOUBLE(8,2) UNSIGNED DEFAULT NULL)");
        }
    }

    private static void populateDB(int n) throws SQLException {
        Random rng = new Random();
        conn.setAutoCommit(false);
        try {
            try {
                try (PreparedStatement ps = conn.prepareStatement
                        ("INSERT INTO flats (district, address, area, rooms, price) VALUES (?, ?, ?, ?, ?)")) {
                    for (int i = 0; i < n; i++) {
                        String district = "Bla";
                        for (int j = 0; j < rng.nextInt(3); j++) district += "bla";
                        String address = district + " district, La";
                        for (int j = 0; j < rng.nextInt(3); j++) address += "la";
                        address += " street " + (rng.nextInt(30) + 1) + ", apartment " + (rng.nextInt(200) + 1);
                        int rooms = rng.nextInt(4) + 1;
                        double area = rooms * (rng.nextDouble() + 1) * 15;
                        double price = area * (rng.nextDouble() + 1) * 1000;
                        ps.setString(1, district);
                        ps.setString(2, address);
                        ps.setDouble(3, area);
                        ps.setInt(4, rooms);
                        ps.setDouble(5, price);
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

    public static void listFlats() throws SQLException {
        SortedSet<DbFields> columns = getColumnsToDisplay();
        Object[] filterSettings = new Object[2];
        DbFields filterBy = getFilterSettings(filterSettings);
        dbSelect(columns, filterBy, filterSettings);
    }

    private static SortedSet<DbFields> getColumnsToDisplay() {
        Scanner sc = new Scanner(System.in);
        String input;
        SortedSet<DbFields> columns = new TreeSet<>();
        System.out.println("Please Enter the fields you wish to view separated with comma:");
        System.out.println("(1)District (2)Address (3)Total Area (4)Number of Rooms (5)Price(USD) (6) All");
        input = sc.nextLine();
        if (input.equals("6")) return null;
        for (String i : input.split("[ ,;+]")) {
            if (Pattern.matches("[1-6]", i)) {
                if (i.equals("6")) {
                    return null;
                } else {
                    columns.add(DbFields.values()[Integer.parseInt(i) - 1]);
                }
            }
        }
        return columns;
    }

    private static DbFields getFilterSettings(Object[] filterSettings) {
        int input;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter a field you wish to filter by: ");
        System.out.println("(1)District (2)Total Area (3)Number of Rooms (4)Price(USD) (5) No Filter, Show all");
        try {
            input = sc.nextInt();
            switch (input) {
                case 1: {
                    Scanner sc1 = new Scanner(System.in);
                    System.out.println("Enter District name:");
                    filterSettings[0] = sc1.nextLine();
                    return DbFields.DISTRICT;
                }
                case 2:
                case 3:
                case 4: {
                    System.out.println("Enter minimum amount:");
                    filterSettings[0] = sc.nextInt();
                    System.out.println("Enter maximum amount:");
                    filterSettings[1] = sc.nextInt();
                    return DbFields.values()[input];
                }
                case 5: {
                    return null;
                }
                default: {
                    throw new InputMismatchException();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Input Error! No filter enabled");
            return null;
        }

    }

    private static String dbSelectString(SortedSet<DbFields> columns, DbFields filterBy, Object[] filterSettings) {
        String columnsString, filterString;
        if (columns == null || columns.isEmpty()) {
            columnsString = "* ";
        } else {
            StringBuilder sb = new StringBuilder();
            for (DbFields i : columns) {
                sb.append(i.name() + ", ");
            }
            columnsString = sb.delete(sb.length() - 2, sb.length() - 1).toString();
        }
        if (filterBy == null) {
            filterString = "";
        } else {
            filterString = "WHERE " + filterBy;
            switch (filterBy) {
                case DISTRICT: {
                    filterString += " = \"" + filterSettings[0] + "\"";
                    break;
                }
                case AREA:
                case ROOMS:
                case PRICE: {
                    filterString += " >= " + filterSettings[0] + " and " + filterBy + " <= " + filterSettings[1];
                }
            }
        }
        return "SELECT " + columnsString + "FROM flats " + filterString;
    }

    private static void dbSelect(SortedSet<DbFields> columns, DbFields filterBy, Object[] filterSettings) throws SQLException {
        try (Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery(dbSelectString(columns, filterBy, filterSettings))) {
                ResultSetMetaData md = rs.getMetaData();
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    System.out.printf("%-" + (md.getColumnName(i).equals("address") ? 60 : 12) + "s", md.getColumnName(i));
                }
                System.out.println();
                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.printf("%-" + (md.getColumnName(i).equals("address") ? 60 : 12) + "s", rs.getString(i));
                    }
                    System.out.println();
                }
            }
        }
    }
}
