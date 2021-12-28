package ru.emdavl.services;

import java.sql.*;


/**
 * Перед тем как запускать программу в конструкторе вставьте ссылку на свой дата сурс, логин и пароль от БД.
 * Вроде перед заполнением все таблицы должны быть пустые, но можете попробовать и не с пустыми должно тоже работать
 */
public class Generator {

    private Connection connection;
    private String SQL_INSERT_INTO_DEPARTMENT = "INSERT INTO first_class.department(name) VALUES (?)";
    private String SQL_COUNT_DEPARTMENTS = "SELECT COUNT(id) as nums FROM first_class.department";
    private String SQL_COUNT_EMPLOYEES = "SELECT COUNT(id) as nums FROM first_class.employee";
    private String SQL_INSERT_INTO_EMPLOYEES = "INSERT INTO " +
            "first_class.employee(department_id," +
            " chief_id," +
            " name," +
            " salary," +
            " phone_number)" +
            "VALUES(?, ?, ?, ?, ?)";

    public static void main(String[] args) {
        Generator generator = new Generator();
        generator.createDepartments();
        int numOfDepartments = generator.getNumOfDepartments();
        int numOfEmployees = generator.getNumOfEmployees();
        generator.createEmployees(numOfDepartments, numOfEmployees);
    }

    private int getNumOfEmployees() {
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_COUNT_EMPLOYEES);
            resultSet.next();
            return resultSet.getInt("nums");
        }catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    private void createEmployees(int numOfDepartments, int numOfEmployees) {
        try {
            for (int i = 0; i < 1000; i++) {
                PreparedStatement statement =
                        connection.prepareStatement(SQL_INSERT_INTO_EMPLOYEES);
                for (int j = 0; j < 1000; j++) {
                    int departmentId = (int) (Math.random() * (numOfDepartments - 1)) + 1;
                    int chiefId = 1;
                    if(i!=0) {
                        chiefId = (int) (Math.random() * (numOfEmployees - 1)) + 1;
                    }
                    String employeeName = "Employee #" + i + j;
                    String phoneNumber = "89" + RandomThingsGenerator.getPhoneNumBody();
                    double salary = RandomThingsGenerator.getSalary();
                    statement.setInt(1, departmentId);
                    statement.setInt(2, chiefId);
                    statement.setString(3, employeeName);
                    statement.setDouble(4, salary);
                    statement.setString(5, phoneNumber);
                    statement.addBatch();
                }
                numOfEmployees+=1000;
                statement.executeBatch();
            }
        }catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    private int getNumOfDepartments() {
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_COUNT_DEPARTMENTS);
            resultSet.next();
            return resultSet.getInt("nums");
        }catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    private void createDepartments() {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_DEPARTMENT);
            for (int i = 2; i < 42; i++) {
                String departmentName = "Department №" + i;
                statement.setString(1, departmentName);
                statement.addBatch();
            }
            statement.executeBatch();
        }catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }



    public Generator() {
        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_lessons",
                    "postgres",
                    "databasepass");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
