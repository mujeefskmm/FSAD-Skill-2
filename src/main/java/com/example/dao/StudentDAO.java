package com.example.dao;

import com.example.entity.Student;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDAO {

    // Create
    public void saveStudent(Student s) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(s);
            tx.commit();
        }
    }

    // Read by ID
    public Student getStudent(int id) {
        try (Session session = HibernateUtil.getFactory().openSession()) {
            return session.get(Student.class, id);
        }
    }

    // Read All
    public List<Student> getAllStudents() {
        try (Session session = HibernateUtil.getFactory().openSession()) {
            return session.createQuery("from Student", Student.class).list();
        }
    }

    // Update
    public void updateStudent(Student s) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(s);
            tx.commit();
        }
    }

    // Delete
    public boolean deleteStudent(int id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getFactory().openSession()) {
            Student s = session.get(Student.class, id);

            if (s == null) {
                return false;     // Student not found
            }

            tx = session.beginTransaction();
            session.delete(s);
            tx.commit();

            return true;          // Successfully deleted

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
