package com.example.test3.service;

import com.example.test3.model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDao implements IStudentDao{
    private String jdbcURL = "jdbc:mysql://localhost:3306/thithuchanh3";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Hiepga123@";
    private static final String INSERT_STUDENT_SQL = "insert into student(name, dateOfBirth, address, phoneNumber, email, classroom) values (?,?,?,?,?,?)";
    private static final String SELECT_STUDENT_BY_NAME = "select id, name, dateOfBirth, address, phoneNumber, email, classroom from student where name like ?";
    private static final String SELECT_STUDENT_BY_ID = "select * from student where id = ?";
    private static final String SELECT_ALL_STUDENTS = "select * from student";
    private static final String DELETE_STUDENT_SQL = "delete from student where id = ?";
    private static final String UPDATE_STUDENT_SQL = "update student set name = ?, dateOfBirth = ?, address = ?, phoneNumber = ?, email = ?, classroom = ? where id = ?";
    public StudentDao(){
    }
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void insertStudent(Student student) throws SQLException {
        System.out.println(INSERT_STUDENT_SQL);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, String.valueOf(student.getDateOfBirth()));
            preparedStatement.setString(3, student.getAddress());
            preparedStatement.setInt(4, student.getPhoneNumber());
            preparedStatement.setString(5, student.getEmail());
            preparedStatement.setString(6, student.getClassroom());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student findStudentByName(String name) {
        Student student = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_NAME)) {
            preparedStatement.setString(1, name);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String studentName = rs.getString("name");
                LocalDate dateOfBirth = LocalDate.parse(rs.getString("dateOfBirth"));
                String address = rs.getString("address");
                int phoneNumber = rs.getInt("phoneNumber");
                String email = rs.getString("email");
                String classroom = rs.getString("classroom");
                student = new Student(id, studentName, dateOfBirth, address, phoneNumber, email, classroom);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    @Override
    public List<Student> selectAllStudent() {
        List<Student> studentList = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STUDENTS);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String classroom = rs.getString("classroom");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }


    @Override
    public boolean deleteStudent(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateStudent(Student student) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT_SQL)) {
            statement.setString(1, student.getName());
            statement.setString(2, String.valueOf(student.getDateOfBirth()));
            statement.setString(3, student.getAddress());
            statement.setInt(4, student.getPhoneNumber());
            statement.setString(5, student.getEmail());
            statement.setString(6, student.getClassroom());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public Student findById(int id) {
        Student student = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_BY_ID))
        {
            statement.setInt(1, id);
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                LocalDate birth = resultSet.getDate("birthDate").toLocalDate();
                String address = resultSet.getString("address");
                int phone = resultSet.getInt("phone");
                String email = resultSet.getString("email");
                String classroom = resultSet.getString("classroom");
                student =  new Student(id, name, birth, address, phone, email, classroom);
            }
            System.out.println("Successully Connected!");
        } catch (SQLException e) {
            System.err.println(e);
        }
        return student;
    }
}
