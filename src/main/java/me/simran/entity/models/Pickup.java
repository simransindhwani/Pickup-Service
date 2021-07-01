package me.simran.entity.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.simran.entity.enums.PickupStatus;
import me.simran.entity.enums.PickupType;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name="order_pickup")
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pickup {

    @Id
    private long id;

    @Column(name="pickup_date")
    private Date pickupDate;

    @Enumerated(EnumType.STRING)
    @Column(name="pickup_status")
    private PickupStatus pickupStatus;

    @Column(name="pickup_height")
    private double pickupHeight;

    @Column(name="pickup_weight")
    private double pickupWeight;

    @Column(name="pickup_length")
    private double pickupLength;

    @Column(name="pickup_start_time")
    private Time pickupStartTime;

    @Column(name="pickup_end_time")
    private Time pickupEndTime;

    @Enumerated(EnumType.STRING)
    @Column(name="pickup_type")
    private PickupType pickupType;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private Employee employee;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private Tote tote;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Warehouse> warehouses;

    public Pickup(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public PickupStatus getPickupStatus() {
        return pickupStatus;
    }

    public void setPickupStatus(PickupStatus pickupStatus) {
        this.pickupStatus = pickupStatus;
    }

    public double getPickupHeight() {
        return pickupHeight;
    }

    public void setPickupHeight(double pickupHeight) {
        this.pickupHeight = pickupHeight;
    }

    public double getPickupWeight() {
        return pickupWeight;
    }

    public void setPickupWeight(double pickupWeight) {
        this.pickupWeight = pickupWeight;
    }

    public double getPickupLength() {
        return pickupLength;
    }

    public void setPickupLength(double pickupLength) {
        this.pickupLength = pickupLength;
    }

    public Time getPickupStartTime() {
        return pickupStartTime;
    }

    public void setPickupStartTime(Time pickupStartTime) {
        this.pickupStartTime = pickupStartTime;
    }

    public Time getPickupEndTime() {
        return pickupEndTime;
    }

    public void setPickupEndTime(Time pickupEndTime) {
        this.pickupEndTime = pickupEndTime;
    }

    public PickupType getPickupType() {
        return pickupType;
    }

    public void setPickupType(PickupType pickupType) {
        this.pickupType = pickupType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Tote getTote() {
        return tote;
    }

    public void setTote(Tote tote) {
        this.tote = tote;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
}
