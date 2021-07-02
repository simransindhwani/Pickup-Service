package me.simran.repository.thirdParty;

import me.simran.entity.thirdParty.EmployeePerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends JpaRepository<EmployeePerformance, Long> {
}
