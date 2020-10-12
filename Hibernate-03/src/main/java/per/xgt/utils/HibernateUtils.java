package per.xgt.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * @Author: Yvonneflynn's husband
 * @Email：I don't know
 * @CreateTime: 2020-09-27 16:19:17
 * @Descirption:
 */
public class HibernateUtils {

    private HibernateUtils(){}

    private static HibernateUtils instance = new HibernateUtils();

    public static HibernateUtils getInstance(){
        return instance;
    }

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null){
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry
                    = new ServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    public Session getSession(){
        return getSessionFactory().getCurrentSession();
    }
}
