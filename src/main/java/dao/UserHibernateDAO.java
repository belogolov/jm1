package dao;

import model.Role;
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
import java.util.Set;

public class UserHibernateDAO implements UserDAO{
    private SessionFactory sessionFactory = createSessionFactory();

    private static SessionFactory createSessionFactory() {
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
    public User getUserByLogin(String login) {
        Session session = sessionFactory.openSession();
        String sql = "SELECT u FROM User u WHERE u.email=:login";
        Query<User> query = session.createQuery(sql);
        query.setParameter("login", login);
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

    @Override
    public boolean isValidUser(String login, String password) throws SQLException {
        Session session = sessionFactory.openSession();
        String sql = "SELECT u FROM User u WHERE u.email=:login";
        Query<User> query = session.createQuery(sql);
        query.setParameter("login", login);
        query.setMaxResults(1);
        User user = query.getSingleResult();
        session.close();
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    @Override
    public Set<Role> getUserRoles(User user) {
        Session session = sessionFactory.openSession();
        String sql = "SELECT u FROM User u WHERE u.id=:id";
        Query<User> query = session.createQuery(sql);
        query.setParameter("id", user.getId());
        query.setMaxResults(1);
        Set<Role> roles = query.getSingleResult().getRoles();
        session.close();
        return roles;
    }

    @Override
    public Role getRoleById(long id) {
        Session session = sessionFactory.openSession();
        String sql = "SELECT r FROM Role r WHERE r.id=:id";
        Query<Role> query = session.createQuery(sql);
        query.setParameter("id", id);
        query.setMaxResults(1);
        Role role = query.getSingleResult();
        session.close();
        return role;
    }
}
