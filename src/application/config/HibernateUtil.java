package application.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by java-user on 27.01.2017.
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if ( sessionFactory == null ) {
            final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("/application/resources/hibernate.cfg.xml").build();

            try {
                sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
            }
        }

        return sessionFactory;
    }

}
