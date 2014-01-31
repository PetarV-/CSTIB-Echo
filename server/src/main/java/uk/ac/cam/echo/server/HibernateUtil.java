package uk.ac.cam.echo.server;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.net.URI;
import java.net.URISyntaxException;

public class HibernateUtil {

    private static SessionFactory sf=configureSessionFactory();
    private static void applyHerokuConfigs(Configuration config) {
        URI dbUri = null;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ;
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        config.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        config.setProperty("hibernate.connection.url", dbUrl);
        config.setProperty("hibernate.connection.username", username);
        config.setProperty("hibernate.connection.password", password);
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");

    }

    private static Configuration getConfiguration() {
        Configuration config = new Configuration();
        config.configure();
        if (System.getenv("PLATFORM") != null && System.getenv("PLATFORM").equals("heroku")) {
            applyHerokuConfigs(config);
        }
        return config;
    }

    private static SessionFactory configureSessionFactory()
            throws HibernateException {
        Configuration configuration = getConfiguration();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        SessionFactory sessionFactory = configuration
                .buildSessionFactory(serviceRegistry);

        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sf;
    }

    public static Session getSession() {
        return sf.getCurrentSession();
    }
    public static Session getTransaction() {
        Session session = sf.getCurrentSession();
        Transaction transaction = session.getTransaction();
        if (!transaction.isActive())
            session.beginTransaction();
        return session;
    }

}
