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
    List<T> groupBy(Class<T> type, String ... filters);

    /**
     * Returns row where max(port) > {@code number}
     */
    List<T> having(Class<T> type, Integer number, String ... columns);
}
