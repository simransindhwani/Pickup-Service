package me.simran.service.Employee;

import me.simran.entity.models.Pickup;

public interface EmployeeService {

    void updateStartTime(long pickupId, Pickup pickup);

    void updateEndTime(long pickupId, Pickup pickup);

    void updateToteWeight(long pickupId, Pickup pickup);

}
