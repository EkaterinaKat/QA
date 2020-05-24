package database;

import model.QA;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBC {
    private static JDBC instance;
    private Connection connection;
    private Statement statement;

    private JDBC() {
        connect();
        if (!tableExists("sections"))
            createSectionsTable();
        if (!tableExists("qa"))
            createQATable();

    }

    private void createSectionsTable() {
        String query = "CREATE TABLE sections (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "name STRING \n" +
                ");";
        executeUpdate(query);
    }

    private void createQATable() {
        String query = "CREATE TABLE qa (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    question TEXT,\n" +
                "    answer   TEXT,\n" +
                "    level    INTEGER,\n" +
                "    section  STRING,\n" +
                "    day      INT,\n" +
                "    month    INT,\n" +
                "    year     INT\n" +
                ");";
        executeUpdate(query);
    }


    private void executeUpdate(String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static JDBC getInstance() {
        if (instance == null) {
            instance = new JDBC();
        }
        return instance;
    }

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:QA.db");
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean tableExists(String tableToFind) {
        String query = "SELECT name FROM sqlite_master WHERE type = \"table\"";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                if (tableName.equals(tableToFind))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addSection(String sectionName) {
        String query = String.format("INSERT INTO sections (name)\n" +
                "VALUES (\"%s\")", sectionName);
        executeUpdate(query);
    }

    public List<String> getSections() {
        List<String> result = new ArrayList<>();
        String query = "SELECT name FROM sections";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String sectionName = resultSet.getString(1);
                result.add(sectionName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addQA(String question, String answer, int level, String section, LocalDate date) {
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        String query = String.format(
                "INSERT INTO qa (question, answer, level, section, day, month, year)\n" +
                        "VALUES (\"%s\", \"%s\", \"%d\", \"%s\", \"%d\", \"%d\", \"%d\")",
                question, answer, level, section, day, month, year);
        executeUpdate(query);
    }

    public List<String> getQuestions() {
        List<String> result = new ArrayList<>();
        String query = "SELECT question FROM qa";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String question = resultSet.getString(1);
                result.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public QA getQA(String question) {
        QA qa = null;
        String query = String.format("SELECT id, answer, level, section, day, month, year FROM qa\n" +
                "WHERE question = \"%s\"", question);
        try {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            int id = resultSet.getInt(1);
            String answer = resultSet.getString(2);
            int level = resultSet.getInt(3);
            String section = resultSet.getString(4);
            int day = resultSet.getInt(5);
            int month = resultSet.getInt(6);
            int year = resultSet.getInt(7);
            qa = new QA(id, question, answer, level, section, day, month, year);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qa;
    }
}
