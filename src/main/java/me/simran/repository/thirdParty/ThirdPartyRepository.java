package me.simran.repository.thirdParty;

import me.simran.entity.models.EmployeePerformance;
import me.simran.entity.thirdParty.EmpStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends JpaRepository<EmpStats, Long> {
}
