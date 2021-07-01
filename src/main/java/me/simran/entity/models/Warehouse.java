package me.simran.entity.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import me.simran.entity.enums.WarehouseZone;

import javax.persistence.*;

@Entity
@Table(name="warehouse")
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name="warehouse_zone")
    private WarehouseZone warehouseZone;

    public Warehouse(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WarehouseZone getWarehouseZone() {
        return warehouseZone;
    }

    public void setWarehouseZone(WarehouseZone warehouseZone) {
        this.warehouseZone = warehouseZone;
    }
}
