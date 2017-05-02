package application.services;

import application.config.IGenericDAO;
import application.entities.Service;

import java.util.List;

public class EntityService<T> {

    private IGenericDAO genericDAO;

    public EntityService(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public Long count() {
        return genericDAO.count(Service.class);
    }

    public List<Service> groupBy(String ... filters) {
        return genericDAO.groupBy(Service.class, filters);
    }


    public List<Service> having(int i, String ... columns) {
        return genericDAO.having(Service.class, i, columns);
    }

    public List<Service> in(String column, Integer ... values) {
        return genericDAO.in(Service.class, column, values);
    }

    public List<Service> notIn(String column, Integer ... values) {
        return genericDAO.notIn(Service.class, column, values);
    }

    public void dropIndex(String indexName) {
        genericDAO.dropIndex(indexName);
    }

    public void notExists(Class parent, Class child) {
        genericDAO.notExists(parent, child);
    }

    public List<T> subquery(Class parent, Class child) {
        return genericDAO.subquery(parent, child);
    }

    public Number min(Class type, String field) {
        return genericDAO.min(type, field);
    }

    public Number max(Class type, String field) {
        return genericDAO.max(type, field);
    }

    public Number avg(Class type, String field) {
        return genericDAO.avg(type, field);
    }

    public List<T> list(Class type) {
        return genericDAO.list(type);
    }

    public void callStoredProcedure(String name, String value, String arg) {
        genericDAO.callStoredProcedure(name, value);
    }

    public void update(Object obj) {
        genericDAO.update(obj);
    }
}
