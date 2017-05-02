package application;

import application.config.GenericDAO;
import application.entities.Availability;
import application.entities.Backup;
import application.entities.Service;
import application.services.EntityService;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        GenericDAO<Service> genericDAO = new GenericDAO<>();
        EntityService entityService = new EntityService(genericDAO);
        List<Service> list;
        List<Backup> backupList;
        Number number;

        /* Count */
        System.out.println(String.format("\n-- COUNT: %s -- ", entityService.count()));

        /* Group by */
        System.out.println("\n-- GROUP BY: --");
        list = entityService.groupBy("port");
        list.forEach(service -> System.out.println("Port: " + service.getPort()));

        /* Having */
        System.out.println("\n-- HAVING: --");
        list = entityService.having(100, "port");
        list.stream().forEach(service -> System.out.println("Port: " + service.getPort()));

        /* IN */
        System.out.println("\n-- IN: --");
        list = entityService.in("port", 100, 8080);
        list.stream().forEach(service -> System.out.println("Port: " + service.getPort()));

        /* NOT IN */
        System.out.println("\n-- NOT IN: --");
        list = entityService.notIn("port", 100, 8080);
        list.stream().forEach(service -> System.out.println("Port: " + service.getPort()));

        /* EXISTS */
        System.out.println("\n-- EXISTS --");
        entityService.dropIndex("SomeCoolIndex");

        /* NOT EXISTS */
        System.out.println("\n-- NOT EXISTS --");
        entityService.notExists(Service.class, Availability.class);

        /* SUBQUERY */
        System.out.println("\n-- SUBQUERY --");
        list = entityService.subquery(Service.class, Availability.class);
        list.stream().forEach(service -> System.out.println("ID: " + service.getId()));

        /* MIN */
        System.out.println("\n-- MIN --");
        number = entityService.min(Service.class, "port");
        System.out.println("Min port: " + number);

        /* MAX */
        System.out.println("\n-- MAX --");
        number = entityService.max(Service.class, "port");
        System.out.println("Max port: " + number);

        /* AVG */
        System.out.println("\n-- AVG --");
        number = entityService.avg(Service.class, "port");
        System.out.println("Avg port: " + number);

        /* CALL STORED PROCEDURE */
        /* FIXME not properly working */
        System.out.println("\n-- CALLING STORED PROCEDURE --");
        entityService.callStoredProcedure("BACKUPVALUE", "This is stored procedure", "SOMENAME");
        backupList = entityService.list(Backup.class);
        backupList.forEach(item -> System.out.println("ID: " + item.getId() + " NAME: " + item.getHostname()));


        /* CALL TRIGGER ON SERVICES UPDATE */
        /* FIXME not properly working */
        System.out.println("\n-- CALLING TRIGGER --");
        Service serviceObj = new Service();
        serviceObj.setId(1);
        serviceObj.setHost("TRIGGER!");
        serviceObj.setPort(666);
        entityService.update(serviceObj);

        list = entityService.list(Service.class);
        list.forEach(serviceItem -> System.out.println("Port: " + serviceItem.getPort() + " Host: " + serviceItem.getHost()));

        backupList = entityService.list(Backup.class);
        backupList.forEach(item -> System.out.println("ID: " + item.getId() + " NAME: " + item.getHostname()));


        System.exit(1);

    }
}
