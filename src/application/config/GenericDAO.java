package application.config;

import org.hibernate.Session;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public List<T> in(Class<T> type, String column, Integer ... values) {
        List<T> list;
        getSession().beginTransaction();
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
        Root<T> root = query.from(type);

        query
                .where(root.get(column).in(values));

        list = getSession().createQuery(query).getResultList();
        getSession().getTransaction().commit();

        return list;
    }

    @Override
    public List<T> notIn(Class<T> type, String column, Integer... values) {
        List<T> list;
        getSession().beginTransaction();
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
        Root<T> root = query.from(type);

        query
                .where(criteriaBuilder.not(root.get(column).in(values)));

        list = getSession().createQuery(query).getResultList();
        getSession().getTransaction().commit();

        return list;
    }

    @Override
    public void dropIndex(String indexName) {
        getSession().beginTransaction();

        getSession().createNativeQuery("DROP INDEX IF EXISTS " + indexName + ";").executeUpdate();

        getSession().getTransaction().commit();
    }

    @Override
    public List<T> notExists(Class parent, Class child) {
        List<T> list;
        getSession().beginTransaction();

        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Object> query = criteriaBuilder.createQuery(parent);
        Root<Object> rootParent = query.from(parent);

        Path<Object> path = rootParent.get("id");

        CriteriaQuery<Object> select = query.select(rootParent);

        Subquery<Object> subquery = query.subquery(child);
        Root rootChild = subquery.from(child);

        subquery.select(rootChild.get("service"));

        select.where(criteriaBuilder.not(criteriaBuilder.exists(subquery)));

        list = (List<T>) getSession().createQuery(select).getResultList();
        getSession().getTransaction().commit();

        return list;
    }

    @Override
    public List<T> subquery(Class parent, Class child) {
        List<T> list;
        getSession().beginTransaction();

        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Object> query = criteriaBuilder.createQuery(parent);
        Root<Object> rootParent = query.from(parent);

        Path<Object> path = rootParent.get("id");

        CriteriaQuery<Object> select = query.select(rootParent);

        Subquery<Object> subquery = query.subquery(child);
        Root rootChild = subquery.from(child);

        subquery.select(rootChild.get("service"));

        select.where(criteriaBuilder.in(path).value(subquery));

        list = (List<T>) getSession().createQuery(select).getResultList();
        getSession().getTransaction().commit();

        return list;
    }

    @Override
    public Number min(Class type, String field) {
        Number result;
        getSession().beginTransaction();
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Number> query = criteriaBuilder.createQuery(type);
        Root<T> root = query.from(type);

        query.select(criteriaBuilder.min(root.get(field)));

        result = getSession().createQuery(query).getSingleResult();
        getSession().getTransaction().commit();

        return result;
    }

    @Override
    public Number max(Class type, String field) {
        Number result;
        getSession().beginTransaction();
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Number> query = criteriaBuilder.createQuery(type);
        Root<T> root = query.from(type);

        query.select(criteriaBuilder.max(root.get(field)));

        result = getSession().createQuery(query).getSingleResult();
        getSession().getTransaction().commit();

        return result;
    }

    @Override
    public Number avg(Class type, String field) {
        Number result;
        getSession().beginTransaction();
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Number> query = criteriaBuilder.createQuery(type);
        Root<T> root = query.from(type);

        query.select(criteriaBuilder.avg(root.get(field)));

        result = getSession().createQuery(query).getSingleResult();
        getSession().getTransaction().commit();

        return result;
    }

    @Override
    public void callStoredProcedure(String name, String value) {
        getSession().beginTransaction();

        getSession().createNativeQuery("CALL " + name + "('" + value + "')").executeUpdate();


        /*StoredProcedureQuery query = getSession().createStoredProcedureQuery(name);
        query.registerStoredProcedureParameter(arg, String.class, ParameterMode.IN);

        query.setParameter(arg, value);

        query.execute();*/

        getSession().getTransaction().commit();
    }

    private Session getSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession() == null
                ? HibernateUtil.getSessionFactory().openSession()
                : HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
