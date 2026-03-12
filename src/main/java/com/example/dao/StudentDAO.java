package com.example.dao;

import com.example.entity.Student;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAO {

    // CREATE
    public void saveStudent(Student s) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(s);
            tx.commit();
        }
    }

    // READ by ID
    public Student getStudent(int id) {
        try (Session session = HibernateUtil.getFactory().openSession()) {
            return session.get(Student.class, id);
        }
    }

    // READ ALL
    public List<Student> getAllStudents() {
        try (Session session = HibernateUtil.getFactory().openSession()) {
            return session.createQuery("from Student", Student.class).list();
        }
    }

    // UPDATE
    public void updateStudent(Student s) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(s);
            tx.commit();
        }
    }

    // DELETE
    public boolean deleteStudent(int id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getFactory().openSession()) {
            Student s = session.get(Student.class, id);

            if (s == null) {
                return false;
            }

            tx = session.beginTransaction();
            session.delete(s);
            tx.commit();

            return true;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- HQL QUERIES ----------------

    // Sort by Name
    public void sortStudentsByName() {

        try (Session session = HibernateUtil.getFactory().openSession()) {

            String hql = "FROM Student s ORDER BY s.name ASC";

            Query<Student> query = session.createQuery(hql, Student.class);

            List<Student> students = query.list();

            System.out.println("---- Students Sorted by Name ----");

            for (Student s : students) {
                System.out.println(s.getId() + " " + s.getName() + " " + s.getCity());
            }
        }
    }

    // Pagination (First 3 students)
    public void getFirstThreeStudents() {

        try (Session session = HibernateUtil.getFactory().openSession()) {

            String hql = "FROM Student";

            Query<Student> query = session.createQuery(hql, Student.class);

            query.setFirstResult(0);
            query.setMaxResults(3);

            List<Student> students = query.list();

            System.out.println("---- First 3 Students ----");

            for (Student s : students) {
                System.out.println(s.getName());
            }
        }
    }

    // COUNT Aggregate
    public void countStudents() {

        try (Session session = HibernateUtil.getFactory().openSession()) {

            String hql = "SELECT COUNT(s) FROM Student s";

            Query<Long> query = session.createQuery(hql, Long.class);

            Long count = query.uniqueResult();

            System.out.println("Total Students = " + count);
        }
    }

    // LIKE Query
    public void findStudentsStartingWithA() {

        try (Session session = HibernateUtil.getFactory().openSession()) {

            String hql = "FROM Student s WHERE s.name LIKE 'A%'";

            Query<Student> query = session.createQuery(hql, Student.class);

            List<Student> students = query.list();

            System.out.println("---- Students Starting With A ----");

            for (Student s : students) {
                System.out.println(s.getName());
            }
        }
    }

    // Sort by City (DESC)
    public void sortStudentsByCityDesc() {

        try (Session session = HibernateUtil.getFactory().openSession()) {

            String hql = "FROM Student s ORDER BY s.city DESC";

            Query<Student> query = session.createQuery(hql, Student.class);

            List<Student> students = query.list();

            System.out.println("---- Students Sorted by City DESC ----");

            for (Student s : students) {
                System.out.println(s.getName() + " - " + s.getCity());
            }
        }
    }

    // GROUP BY City
    public void groupStudentsByCity() {

        try (Session session = HibernateUtil.getFactory().openSession()) {

            String hql = "SELECT s.city, COUNT(s) FROM Student s GROUP BY s.city";

            Query<Object[]> query = session.createQuery(hql, Object[].class);

            List<Object[]> list = query.list();

            System.out.println("---- Student Count by City ----");

            for (Object[] row : list) {
                System.out.println(row[0] + " : " + row[1]);
            }
        }
    }

    // MIN and MAX
    public void findMinMaxId() {

        try (Session session = HibernateUtil.getFactory().openSession()) {

            String hql = "SELECT MIN(s.id), MAX(s.id) FROM Student s";

            Query<Object[]> query = session.createQuery(hql, Object[].class);

            Object[] result = query.uniqueResult();

            System.out.println("Minimum ID = " + result[0]);
            System.out.println("Maximum ID = " + result[1]);
        }
    }
}