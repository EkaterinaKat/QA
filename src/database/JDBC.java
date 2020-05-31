package database;

import model.QA;
import model.SubQuestion;

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
        if (!tableExists("subQ"))
            createSubQTable();
    }

    private void createSubQTable() {
        String query = "CREATE TABLE subQ (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    question TEXT,\n" +
                "    level    INTEGER,\n" +
                "    qa_id    INTEGER\n" +
                ");";
        executeUpdate(query);
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

    public Integer addQA(String question, String answer, int level, String section, LocalDate date, String image) {
        String query = String.format(
                "INSERT INTO qa (question, answer, level, section, date, image)\n" +
                        "VALUES (\"%s\", \"%s\", \"%d\", \"%s\", \"%s\", \"%s\")",
                question, answer, level, section, date.toString(), image);
        executeUpdate(query);
        return getLastInsertID();
    }

    private int getLastInsertID() {
        int id = 0;
        String query = "select last_insert_rowid();";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            id = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
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

    public List<QA> getQALevel_0_or_1() {
        String query = "SELECT id, question, answer, level, section, date, image FROM qa\n" +
                "WHERE level = 0 OR level = 1";
        return getQAListByQuery(query);
    }

    public List<QA> getQALevel_2() {
        String query = "SELECT id, question, answer, level, section, date, image FROM qa\n" +
                "WHERE level = 2";
        return getQAListByQuery(query);
    }

    public List<QA> getQALevel_3() {
        String query = "SELECT id, question, answer, level, section, date, image FROM qa\n" +
                "WHERE level = 3";
        return getQAListByQuery(query);
    }

    /* Запрос должен запрашивать все столбцы */
    private List<QA> getQAListByQuery(String query) {
        List<QA> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String question = resultSet.getString(2);
                String answer = resultSet.getString(3);
                int level = resultSet.getInt(4);
                String section = resultSet.getString(5);
                String date = resultSet.getString(6);
                String image = resultSet.getString(7);
                list.add(new QA(id, question, answer, level, section, date, image));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void editQA(QA qa, String newQ, String newA, String newIm) {
        String query = String.format("UPDATE qa \n" +
                "\t   SET question = \"%s\", answer = \"%s\", image = \"%s\" \n" +
                "\t   WHERE id = \"%d\"", newQ, newA, newIm, qa.getId());
        executeUpdate(query);
    }

    public void createSubQuestion(String question, int level, int qa_id) {
        String query = String.format(
                "INSERT INTO subQ (question, level, qa_id)\n" +
                        "VALUES (\"%s\", \"%d\", \"%d\")",
                question, level, qa_id);
        executeUpdate(query);
    }

    public List<SubQuestion> getSubQuestions(QA qa) {
        List<SubQuestion> result = new ArrayList<>();
        String query = String.format("SELECT id, question, level FROM subQ\n" +
                "WHERE qa_id = \"%d\"", qa.getId());
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int ID = resultSet.getInt(1);
                String question = resultSet.getString(2);
                int level = resultSet.getInt(3);
                result.add(new SubQuestion(ID, qa.getId(), question, level));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void updateSubQuestions(QA qa, List<SubQuestion> subQuestions) {
        deleteQAsubquestions(qa);
        for (SubQuestion subQuestion : subQuestions) {
            String query = String.format(
                    "INSERT INTO subQ (question, level, qa_id)\n" +
                            "VALUES (\"%s\", \"%d\", \"%d\")",
                    subQuestion.getQuestion(), subQuestion.getLevel(), qa.getId());
            executeUpdate(query);
        }
    }

    private void deleteQAsubquestions(QA qa) {
        String query = String.format("DELETE FROM subQ   \n" +
                "WHERE qa_id = \"%d\";", qa.getId());
        executeUpdate(query);
    }

    public void setQANewLevel(QA qa) {
        String query = String.format("UPDATE qa \n" +
                "\t   SET level = \"%d\" \n" +
                "\t   WHERE id = \"%d\"", qa.getLevel(), qa.getId());
        executeUpdate(query);
    }

    public void setSubQNewLevel(SubQuestion subQuestion) {
        String query = String.format("UPDATE subQ \n" +
                "\t   SET level = \"%d\" \n" +
                "\t   WHERE id = \"%d\"", subQuestion.getLevel(), subQuestion.getID());
        executeUpdate(query);
    }

    public int getQuestionLevel(QA qa) {
        int level = -5;
        String query = String.format("SELECT level FROM qa \n" +
                "WHERE id = \"%d\"", qa.getId());
        try {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            level = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return level;
    }

    public int getSubQLevel(SubQuestion subQuestion) {
        int level = -5;
        String query = String.format("SELECT level FROM subQ \n" +
                "WHERE id = \"%d\"", subQuestion.getID());
        try {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            level = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return level;
    }
}
