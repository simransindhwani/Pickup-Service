package me.simran.service.ThirdParty;

import me.simran.entity.thirdParty.EmployeePerformance;
import me.simran.repository.thirdParty.ThirdPartyRepository;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService{

    public final ThirdPartyRepository repository;

    public ThirdPartyServiceImpl(ThirdPartyRepository repository) {
        this.repository = repository;
    }


    @Override
    public void saveEmployeePerformance(EmployeePerformance performance) {
        repository.save(performance);
    }
}
