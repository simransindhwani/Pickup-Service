package me.simran.repository.Pickup;

import me.simran.entity.enums.PickupStatus;
import me.simran.entity.models.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, Long> {

    @Query("SELECT pickup.pickupStatus FROM Pickup pickup WHERE pickup.employee.empId=:empId")
    Optional<List<PickupStatus>> findAllEmployeesWithEmpId(@Param("empId") long empId);

}
