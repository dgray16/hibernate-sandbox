package application.config;

import java.util.List;

/**
 * Created by java-user on 27.01.2017.
 */
public interface IGenericDAO<T> {

    List<T> list(Class<T> type);

    void update(T instance);

    void add(T instance);

    void remove(T instance);

}
