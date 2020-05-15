package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import util.DBHelper;

import java.sql.SQLException;
import java.util.List;

public class UserHibernateDAO implements UserDAO{
    private SessionFactory sessionFactory = createSessionFactory();

    private SessionFactory createSessionFactory() {
        Configuration configuration = DBHelper.getInstance().getConfiguration();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        List<User> users = session.createQuery("FROM User").list();
        session.close();
        return users;
    }

    @Override
    public User getUserById(long id) {
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void updateUser(User user) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteUser(User user) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

}
