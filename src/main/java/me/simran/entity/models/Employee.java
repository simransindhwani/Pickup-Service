package me.simran.entity.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="employees")
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="empId")
    private long empId;

    @Column(name="single_pickup_count")
    private Integer singlePickupCount =  0;

    @Column(name="batch_pickup_count")
    private Integer batchPickupCount = 0;

    @Column(name="difference_single_pickup_time")
    private long differenceSinglePickupTime;

    @Column(name="difference_batch_pickup_time")
    private long differenceBatchPickupTime;

    public Employee(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public Integer getSinglePickupCount() {
        return singlePickupCount;
    }

    public void setSinglePickupCount(Integer singlePickupCount) {
        this.singlePickupCount = singlePickupCount;
    }

    public Integer getBatchPickupCount() {
        return batchPickupCount;
    }

    public void setBatchPickupCount(Integer batchPickupCount) {
        this.batchPickupCount = batchPickupCount;
    }

    public long getDifferenceSinglePickupTime() {
        return differenceSinglePickupTime;
    }

    public void setDifferenceSinglePickupTime(long differenceSinglePickupTime) {
        this.differenceSinglePickupTime = differenceSinglePickupTime;
    }

    public long getDifferenceBatchPickupTime() {
        return differenceBatchPickupTime;
    }

    public void setDifferenceBatchPickupTime(long differenceBatchPickupTime) {
        this.differenceBatchPickupTime = differenceBatchPickupTime;
    }
}
