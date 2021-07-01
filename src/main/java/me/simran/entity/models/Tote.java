package me.simran.entity.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import me.simran.entity.enums.ToteAssigned;

import javax.persistence.*;

@Entity
@Table(name="pickup_tote")
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name="tote_assigned")
    private ToteAssigned toteAssigned;

    @Column(name="tote_weight")
    private double toteWeight;

    public Tote(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ToteAssigned getToteAssigned() {
        return toteAssigned;
    }

    public void setToteAssigned(ToteAssigned toteAssigned) {
        this.toteAssigned = toteAssigned;
    }

    public double getToteWeight() {
        return toteWeight;
    }

    public void setToteWeight(double toteWeight) {
        this.toteWeight = toteWeight;
    }
}
