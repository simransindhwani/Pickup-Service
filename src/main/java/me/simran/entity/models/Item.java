package me.simran.entity.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="item_name")
    private String itemName;

    @Column(name="item_height")
    private double itemHeight;

    @Column(name="item_weight")
    private double itemWeight;

    @Column(name="substitute_allowed")
    private boolean isSubsAllowed;

    @Column(name="subs_name")
    private String subsName;

    public Item(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(double itemHeight) {
        this.itemHeight = itemHeight;
    }

    public double getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(double itemWeight) {
        this.itemWeight = itemWeight;
    }

    public boolean isSubsAllowed() {
        return isSubsAllowed;
    }

    public void setSubsAllowed(boolean subsAllowed) {
        isSubsAllowed = subsAllowed;
    }

    public String getSubsName() {
        return subsName;
    }

    public void setSubsName(String subsName) {
        this.subsName = subsName;
    }
}
