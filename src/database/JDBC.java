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
                "    date     STRING,\n" +
                "    image    STRING\n" +
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

    public void addQA(String question, String answer, int level, String section, LocalDate date, String image) {
        String query = String.format(
                "INSERT INTO qa (question, answer, level, section, date, image)\n" +
                        "VALUES (\"%s\", \"%s\", \"%d\", \"%s\", \"%s\", \"%s\")",
                question, answer, level, section, date.toString(), image);
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

    public QA getQAbyQuestion(String question) {
        QA qa = null;
        String query = String.format("SELECT id, answer, level, section, date, image FROM qa\n" +
                "WHERE question = \"%s\"", question);
        try {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            int id = resultSet.getInt(1);
            String answer = resultSet.getString(2);
            int level = resultSet.getInt(3);
            String section = resultSet.getString(4);
            String date = resultSet.getString(5);
            String image = resultSet.getString(6);
            qa = new QA(id, question, answer, level, section, date, image);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qa;
    }

    public List<QA> getQAlevel1(){
        List<QA> list = new ArrayList<>();
        String query = "SELECT id, question, answer, section, date, image FROM qa\n" +
                "WHERE level = 1";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String question = resultSet.getString(2);
                String answer = resultSet.getString(3);
                String section = resultSet.getString(4);
                String date = resultSet.getString(5);
                String image = resultSet.getString(6);
                list.add(new QA(id, question, answer, 1, section, date, image));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
