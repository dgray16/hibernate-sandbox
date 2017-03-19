package application.config;

import application.entities.Service;
import org.hibernate.Session;

import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by java-user on 27.01.2017.
 */
public class GenericDAO<T> implements IGenericDAO<T> {

    @Override
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

    @Override
    public Long count(Class<T> type) {
        Session session = getSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(type)));

        Long count = session.createQuery(criteriaQuery).getSingleResult();
        session.getTransaction().commit();

        return count;
    }

    @Override
    public void update(T instance) {
        getSession().beginTransaction();
        getSession().update(instance);
        getSession().getTransaction().commit();
    }

    @Override
    public void add(T instance) {
        getSession().beginTransaction();
        getSession().save(instance);
        getSession().getTransaction().commit();
    }

    @Override
    public void remove(T instance) {
        getSession().beginTransaction();
        getSession().delete(instance);
        getSession().getTransaction().commit();
    }

    @Override
    public List groupBy(Class<T> type, String ... filters) {
        List<T> list;
        getSession().beginTransaction();
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
        Root<T> root = query.from(type);

        List<Expression<?>> grouppedBy = Arrays.stream(filters).map(root::get).collect(Collectors.toList());
        List<Selection<?>> columns = Arrays.stream(filters).map(root::get).collect(Collectors.toList());

        query
                .multiselect(columns)
                .groupBy(grouppedBy);

        list = getSession().createQuery(query).getResultList();
        getSession().getTransaction().commit();

        return list;
    }

    @Override
    public List<T> having(Class<T> type, Integer number, String ... columns) {
        List<T> list;
        getSession().beginTransaction();
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
        Root<T> root = query.from(type);

        List<Selection<?>> columnsList = Arrays.stream(columns).map(root::get).collect(Collectors.toList());
        List<Expression<?>> grouppedBy = Arrays.stream(columns).map(root::get).collect(Collectors.toList());

        query
                .multiselect(columnsList)
                .groupBy(grouppedBy)
                .having(criteriaBuilder.gt(criteriaBuilder.max(root.get("port")), number));

        list = getSession().createQuery(query).getResultList();
        getSession().getTransaction().commit();

        return list;
    }

    private Session getSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession() == null
                ? HibernateUtil.getSessionFactory().openSession()
                : HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
