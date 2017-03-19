package application.services;

import application.config.IGenericDAO;
import application.entities.Service;

import java.util.List;

public class EntityService {

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
}
