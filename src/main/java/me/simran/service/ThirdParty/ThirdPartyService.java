package me.simran.service.ThirdParty;

import me.simran.entity.models.EmployeePerformance;
import me.simran.entity.thirdParty.EmpStats;

public interface ThirdPartyService {

    void saveEmployeePerformance(EmpStats stats);
}
