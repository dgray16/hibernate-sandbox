package application.config;

import application.entities.Service;

import java.util.List;

/**
 * Created by java-user on 27.01.2017.
 */
public interface IGenericDAO<T> {

    List<T> list(Class<T> type);

    Long count(Class<T> type);

    void update(T instance);

    void add(T instance);

    void remove(T instance);

    /**
     * @param filters are column names (please use only one column name)
     */
    List<T> groupBy(Class<T> type, String... filters);

    /**
     * Returns row where max(port) > {@code number}
     */
    List<T> having(Class<T> type, Integer number, String... columns);

    List<T> in(Class<T> type, String column, Integer ... values);

    List<T> notIn(Class<T> type, String column, Integer ... values);

    void dropIndex(String indexName);

    List<T> notExists(Class parent, Class child);

    List<T> subquery(Class parent, Class child);

    Number min(Class type, String field);

    Number max(Class type, String field);

    Number avg(Class type, String field);

    void callStoredProcedure(String name, String value);
}
