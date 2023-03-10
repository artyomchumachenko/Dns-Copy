package ru.mai.coursework.dns.helpers;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.User;

import java.util.LinkedHashMap;
import java.util.List;

public class UserHelper {

    private SessionFactory sessionFactory;

    public UserHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public User getUsersByLoginAndPassword(String login, String pass) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq
                .select(root)
                .where(cb.and(cb.equal(root.get("login"), login), cb.equal(root.get("password"), pass)));
        Query query = session.createQuery(cq);
        List<User> users = query.getResultList();
        User currUser = null;
        if (users.size() == 1) {
            currUser = users.get(0);
        }
        session.close();
        return currUser;
    }

    public boolean isUserByLoginExist(String login) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq
                .select(root)
                .where(cb.equal(root.get("login"), login));
        Query query = session.createQuery(cq);
        List<User> users = query.getResultList();
        if (users.size() == 0) {
            session.close();
            return false;
        }
        session.close();
        return true;
    }

    public List<Long> getInformationAmounts() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(
                "SELECT COUNT(up.product) FROM UserProducts up\n" +
                        "GROUP BY up.product\n" +
                        "ORDER BY COUNT(up.product) DESC"
        );
        List<Long> amounts = query.getResultList();
        session.close();
        return amounts;
    }

    public List<Product> getInformationProducts() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(
                "SELECT up.product FROM UserProducts up\n" +
                        "GROUP BY up.product\n" +
                        "ORDER BY COUNT(up.product) DESC"
        );
        List<Product> products = query.getResultList();
        session.close();
        return products;
    }

    public void save(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }
}
