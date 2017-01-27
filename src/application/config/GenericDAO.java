package application.config;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by java-user on 27.01.2017.
 */
public class GenericDAO<T> implements IGenericDAO<T> {


    public List<T> list(Class<T> type) {
        List<T> list;
        getSession().beginTransaction();
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root);

        list = getSession().createQuery(query).getResultList();
        getSession().getTransaction().commit();

        return list;
    }

    public void update(T instance) {
        getSession().beginTransaction();
        getSession().update(instance);
        getSession().getTransaction().commit();
    }

    public void add(T instance) {
        getSession().beginTransaction();
        getSession().save(instance);
        getSession().getTransaction().commit();
    }

    public void remove(T instance) {
        getSession().beginTransaction();
        getSession().delete(instance);
        getSession().getTransaction().commit();
    }

    private Session getSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession() == null
                ? HibernateUtil.getSessionFactory().openSession()
                : HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
