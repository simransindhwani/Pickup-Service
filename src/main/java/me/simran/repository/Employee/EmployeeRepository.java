package me.simran.repository.Employee;

import me.simran.entity.models.EmployeePerformance;
import me.simran.entity.models.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Pickup, Long> {

    @Query("SELECT performance FROM EmployeePerformance performance WHERE performance.empId=:empId")
    Optional<EmployeePerformance> findEmployeeWithEmpId(@Param("empId") long empId);

    @Modifying
    @Transactional
    @Query(value= "REPLACE INTO employee_performance(id, average_batch_pickup_time, average_single_pickup_time, batch_pickup_total_count, emp_id, single_pickup_total_count ) values(?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
    void updatePerformanceRetrieved(long id, long average_batch_pickup_time,long average_single_pickup_time,long batch_pickup_total_count,long emp_id, long single_pickup_total_count);

    @Modifying
    @Transactional
    @Query(value= "INSERT INTO employee_performance(id, average_batch_pickup_time, average_single_pickup_time, batch_pickup_total_count, emp_id, single_pickup_total_count ) values(?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
    void insertIntoPerformance(long id, long average_batch_pickup_time,long average_single_pickup_time,long batch_pickup_total_count,long emp_id, long single_pickup_total_count);
}
