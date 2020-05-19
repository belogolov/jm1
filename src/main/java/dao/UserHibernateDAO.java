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
    private SessionFactory sessionFactory = DBHelper.getInstance().createSessionFactory();

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery("FROM User").list();
        }
        return users;
    }

    @Override
    public User getUserById(long id) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT u FROM User u WHERE u.id=:id";
            Query<User> query = session.createQuery(sql);
            query.setParameter("id", id);
            query.setMaxResults(1);
            user = query.getSingleResult();
        }
        return user;
    }

    @Override
    public User getUserByLogin(String login) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT u FROM User u WHERE u.email=:login";
            Query<User> query = session.createQuery(sql);
            query.setParameter("login", login);
            query.setMaxResults(1);
            user = query.getSingleResult();
        }
        return user;
    }

    @Override
    public void addUser(User user) throws SQLException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(user);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new SQLException(e);
            }
        }
    }

    @Override
    public void updateUser(User user) throws SQLException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(user);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new SQLException(e);
            }
        }
    }

    @Override
    public void deleteUser(User user) throws SQLException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.delete(user);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new SQLException(e);
            }
        }
    }

    @Override
    public User validUser(String login, String password) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT u FROM User u WHERE u.email=:login";
            Query<User> query = session.createQuery(sql);
            query.setParameter("login", login);
            query.setMaxResults(1);
            user = query.getSingleResult();
        }
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public Set<Role> getUserRoles(User user) {
        Set<Role> roles = null;
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT u FROM User u WHERE u.id=:id";
            Query<User> query = session.createQuery(sql);
            query.setParameter("id", user.getId());
            query.setMaxResults(1);
            roles = query.getSingleResult().getRoles();
        }
        return roles;
    }

    @Override
    public Role getRoleById(long id) {
        Role role = null;
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT r FROM Role r WHERE r.id=:id";
            Query<Role> query = session.createQuery(sql);
            query.setParameter("id", id);
            query.setMaxResults(1);
            role = query.getSingleResult();
        }
        return role;
    }
}
