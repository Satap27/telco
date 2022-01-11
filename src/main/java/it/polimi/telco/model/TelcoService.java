package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "service", schema = "telco")
public class TelcoService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String type;

    @NotNull
    @ManyToMany
    @JoinTable(name = "service_entry", schema = "telco", joinColumns = @JoinColumn(name = "fk_id_service"), inverseJoinColumns = @JoinColumn(name = "fk_id_entry"))
    private List<ServiceEntry> serviceEntries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ServiceEntry> getServiceEntries() {
        return serviceEntries;
    }

    public void setServiceEntries(List<ServiceEntry> serviceEntries) {
        this.serviceEntries = serviceEntries;
    }
}
