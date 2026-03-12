package com.example;

import com.example.dao.StudentDAO;
import com.example.entity.Student;

public class MainApp {

    public static void main(String[] args) {

        StudentDAO dao = new StudentDAO();

        // Insert sample students
        dao.saveStudent(new Student("RAMA", "Hyderabad"));
        dao.saveStudent(new Student("SAIRAM", "Guntur"));
        dao.saveStudent(new Student("ARJUN", "Chennai"));
        dao.saveStudent(new Student("ANIL", "Vijayawada"));
        dao.saveStudent(new Student("MOHAN", "Delhi"));

        System.out.println("Students inserted successfully!");

        // HQL Operations
        dao.sortStudentsByName();
        dao.getFirstThreeStudents();
        dao.countStudents();
        dao.findStudentsStartingWithA();
        dao.sortStudentsByCityDesc();
        dao.groupStudentsByCity();
        dao.findMinMaxId();

        System.out.println("HQL Operations Completed!");
    }
}