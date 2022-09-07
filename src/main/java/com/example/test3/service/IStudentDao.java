package com.example.test3.service;

import com.example.test3.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface IStudentDao {
    List<Student> selectAllStudent();
    Student findStudentByName(String name);

    void insertStudent(Student student) throws SQLException;
    boolean deleteStudent(int id) throws SQLException;
    boolean updateStudent(Student student) throws SQLException;
    Student findById(int id);
}
