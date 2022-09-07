package com.example.test3.controller;

import com.example.test3.model.Student;
import com.example.test3.service.IStudentDao;
import com.example.test3.service.StudentDao;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "StudentServlet", urlPatterns = "/students")
public class StudentServlet extends HttpServlet {
    private IStudentDao studentDao;
    public void init() {
        studentDao = new StudentDao();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        if (action == null) {
            action ="";
        }

        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    delete(request, response);
                    break;
                default:
                    listStudent(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void listStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> studentList = studentDao.selectAllStudent();
        request.setAttribute("list", studentList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student/list.jsp");
        dispatcher.forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDao.deleteStudent(id);
        listStudent(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student existingStudent = studentDao.findById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student/edit.jsp");
        request.setAttribute("user", existingStudent);
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("student/create.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                try {
                    insertStudent(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "edit":
                try {
                    editStudent(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            case "delete":
                try {
                    deleteStudent(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDao.deleteStudent(id);
        listStudent(request, response);
    }

    private void editStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        LocalDate birthDate = LocalDate.parse(request.getParameter("birthDate"));
        String address = request.getParameter("address");
        int phone = Integer.parseInt(request.getParameter("phone"));
        String email = request.getParameter("email");
        String classRoom = request.getParameter("classRoom");

        Student editStudent = new Student(id, name, birthDate, address, phone, email, classRoom);
        studentDao.updateStudent(editStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student/edit.jsp");
        request.setAttribute("attribute", "Student edited successful!");
        dispatcher.forward(request, response);
    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");
        LocalDate birthDate = LocalDate.parse(request.getParameter("birthDate"));
        String address = request.getParameter("address");
        int phone = Integer.parseInt(request.getParameter("phone"));
        String email = request.getParameter("email");
        String classRoom = request.getParameter("classRoom");
        Student students = new Student(name, birthDate, address,phone,email,classRoom);
        studentDao.insertStudent(students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student/create.jsp");
        request.setAttribute("iiiiii", "New user was created!");
        dispatcher.forward(request, response);
    }
}
