package application.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by a1 on 02.05.17.
 */
@Entity
public class Backup {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "hostname")
    private String hostname;

    public Backup() {
    }

    public Backup(String hostname) {
        this.hostname = hostname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
