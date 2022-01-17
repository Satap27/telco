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
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<ServiceEntry> serviceEntries;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_id_package")
    private ServicePackage servicePackage;

    public TelcoService(String type, List<ServiceEntry> serviceEntries) {
        this.type = type;
        setServiceEntries(serviceEntries);
    }

    public TelcoService() {

    }

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
        for (ServiceEntry entry: serviceEntries) {
            entry.setService(this);
        }
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }
}
