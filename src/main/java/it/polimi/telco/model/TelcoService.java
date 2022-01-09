package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "telco_service", schema = "telco")
@NamedQuery(name = "TelcoService.getTelcoServiceById", query = "SELECT s FROM TelcoService s  WHERE s.id = ?1")
public class TelcoService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String serviceType;

    @NotNull
    @OneToMany
    private List<ServiceEntry> serviceEntries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<ServiceEntry> getServiceEntries() {
        return serviceEntries;
    }

    public void setServiceEntries(List<ServiceEntry> serviceEntries) {
        this.serviceEntries = serviceEntries;
    }
}
