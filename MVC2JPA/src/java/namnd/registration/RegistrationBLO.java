/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package namnd.registration;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Hi
 */
public class RegistrationBLO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MVC2JPAPU");

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Registration checkLogin(String username, String password) {
        Registration result = null;
        EntityManager em = emf.createEntityManager();
        String jpql = "Select  r "
                + "From Registration r "
                + "Where r.username = :username "
                + "And r.password = :password";
        try {
            Query query = em.createQuery(jpql);

            query.setParameter("username", username);
            query.setParameter("password", password);

            // lấy một dòng dùng single 
            result = (Registration) query.getSingleResult();

        } finally {
            em.close();
        }

        return result;
    }

    public List<Registration> searchLastname(String searchValue) {
        List<Registration> result = null;
        EntityManager em = emf.createEntityManager();
        String jpql = "Select  r "
                + "From Registration r "
                + "Where r.lastname like :lastname";

        try {
            Query query = em.createQuery(jpql);

            query.setParameter("lastname", "%" + searchValue + "%");
            result = query.getResultList();

        } finally {
            em.close();
        }

        return result;

    }

    public boolean deleteAccount(String username) {
        boolean result = false;
        EntityManager em = emf.createEntityManager();
        try {
            Registration req = em.find(Registration.class, username);
            if (req != null) {
                em.getTransaction().begin();
                em.remove(req);
                em.getTransaction().commit();
            }

            result = true;
        } finally {
            em.close();
        }
        return result;
    }

}
