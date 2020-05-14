package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import util.HibernateHelper;

import java.sql.SQLException;
import java.util.List;

public class UserHibernateDAO implements UserDAO{
    private Configuration configuration;

    public UserHibernateDAO(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public List<User> getAllUsers() {
        Session session = HibernateHelper.getSessionFactory().openSession();
        List<User> users = session.createQuery("FROM User").list();
        session.close();
        return users;
    }

    @Override
    public User getUserById(long id) {
        Session session = HibernateHelper.getSessionFactory().openSession();
        String sql = "SELECT u FROM User u WHERE u.id=:id";
        Query<User> query = session.createQuery(sql);
        query.setParameter("id", id);
        query.setMaxResults(1);
        User user = query.getSingleResult();
        session.close();
        return user;
    }

    @Override
    public void addUser(User user) throws SQLException {
        Session session = HibernateHelper.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void updateUser(User user) throws SQLException {
        Session session = HibernateHelper.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteUser(User user) throws SQLException {
        Session session = HibernateHelper.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

}
