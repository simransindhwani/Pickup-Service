package me.simran.entity.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="employee_performance")
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeePerformance {

    @Id
    private long id;

    @Column(name="empId")
    private long empId;

    @Column(name="single_pickup_total_count")
    private Integer singlePickupTotalCount = 0;

    @Column(name="batch_pickup_total_count")
    private Integer batchPickupCount = 0;

    @Column(name="average_single_pickup_time")
    private long AverageSinglePickupTime;

    @Column(name="average_batch_pickup_time")
    private long AverageBatchPickupTime;

    public EmployeePerformance(){
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

    public Integer getSinglePickupTotalCount() {
        return singlePickupTotalCount;
    }

    public void setSinglePickupTotalCount(Integer singlePickupTotalCount) {
        this.singlePickupTotalCount = singlePickupTotalCount;
    }

    public Integer getBatchPickupCount() {
        return batchPickupCount;
    }

    public void setBatchPickupCount(Integer batchPickupCount) {
        this.batchPickupCount = batchPickupCount;
    }

    public long getAverageSinglePickupTime() {
        return AverageSinglePickupTime;
    }

    public void setAverageSinglePickupTime(long averageSinglePickupTime) {
        AverageSinglePickupTime = averageSinglePickupTime;
    }

    public long getAverageBatchPickupTime() {
        return AverageBatchPickupTime;
    }

    public void setAverageBatchPickupTime(long averageBatchPickupTime) {
        AverageBatchPickupTime = averageBatchPickupTime;
    }
}
