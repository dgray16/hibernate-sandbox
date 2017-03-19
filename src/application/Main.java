package application;

import application.config.GenericDAO;
import application.entities.Service;
import application.services.EntityService;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        GenericDAO<Service> genericDAO = new GenericDAO<>();
        EntityService entityService = new EntityService(genericDAO);

        /* Count */
        System.out.println(String.format("\n-- COUNT: %s -- ", entityService.count()));

        /* Group by */
        System.out.println("\n-- GROUP BY: --");
        List<Service> list = entityService.groupBy("port");
        list.stream().forEach(service -> System.out.println("Port: " + service.getPort()));

        /* Having */
        System.out.println("\n-- HAVING: --");
        list = entityService.having(100, "port");
        list.stream().forEach(service -> System.out.println("Port: " + service.getPort()));

        /* TODO NEXT */

    }
}
