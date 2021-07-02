package me.simran.service.ThirdParty;

import me.simran.entity.models.EmployeePerformance;
import me.simran.entity.thirdParty.EmpStats;
import me.simran.repository.thirdParty.ThirdPartyRepository;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService{

    public final ThirdPartyRepository repository;

    public ThirdPartyServiceImpl(ThirdPartyRepository repository) {
        this.repository = repository;
    }


    @Override
    public void saveEmployeePerformance(EmpStats stats) {
        repository.save(stats);
    }
}
