package application.model;


import javax.persistence.*;

/**
 * Created by java-user on 27.01.2017.
 */
@Table(
        name = "services",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"host", "port"})}
        )
@Entity
public class Service {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "host", unique = true)
    private String host;

    @Column(name = "port", unique = true)
    private String port;

    public Service() {

    }

    public Service(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
}
