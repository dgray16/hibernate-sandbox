package application.entities;

import javax.persistence.*;

/**
 * Availability entity
 */
@Table(name = "availability")
@Entity
public class Availability {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    private Service service;

    @Column(name = "is_available")
    private Boolean isAvailable;

    public Availability() {

    }

    public Availability(Service service, Boolean isAvailable) {
        this.service = service;
        this.isAvailable = isAvailable;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }
    public void setService(Service service) {
        this.service = service;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }
    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
