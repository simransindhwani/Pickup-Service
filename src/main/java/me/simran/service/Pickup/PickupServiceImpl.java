package me.simran.service.Pickup;

import me.simran.entity.enums.PickupStatus;
import me.simran.entity.enums.PickupType;
import me.simran.entity.models.Pickup;
import me.simran.exception.EmployeeAlreadyAssignedPickupException;
import me.simran.exception.PickupIdNotFoundException;
import me.simran.exception.PickupNotCancelledException;
import me.simran.exception.PickupNotSingleException;
import me.simran.repository.Pickup.PickupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PickupServiceImpl implements  PickupService{

    private final PickupRepository repository;

    public PickupServiceImpl(PickupRepository repository){
        this.repository = repository;
    }

    @Override
    public Pickup saveSinglePickup(Pickup pickup) {
        /* *
         * Check if the size of the List orders in the pickup has length > 1
         * If yes, throw exception
         * else, initiate the pickup
         */
        if(pickup.getOrders().size()>1)
            throw new PickupNotSingleException("The pickup is a Batch pickup with multiple orders of size ["+pickup.getOrders().size()+"]");
        else{
            long empId = pickup.getEmployee().getEmpId();
            Optional<List<PickupStatus>> filteredStatus = repository.findAllEmployeesWithEmpId(empId);
            if(filteredStatus.isPresent()) {
                int i = 0;
                while (i < filteredStatus.get().size()) {
                    if (filteredStatus.get().get(i).equals(PickupStatus.COMPLETED) || filteredStatus.get().get(i).equals(PickupStatus.CANCELLED))
                        i++;
                    else
                        throw new EmployeeAlreadyAssignedPickupException("The employee with empId: [" + empId + "] is in use");
                }
            }
            Pickup pickupSingle =  repository.save(pickup);
            pickupSingle.setPickupType(PickupType.SINGLE);
            pickupSingle.setPickupStatus(PickupStatus.ASSIGNED);
            repository.save(pickupSingle);
            return pickupSingle;
        }
    }

    @Override
    public Pickup saveBatchPickup(Pickup pickup) {
        long empId = pickup.getEmployee().getEmpId();
        Optional<List<PickupStatus>> filteredStatus = repository.findAllEmployeesWithEmpId(empId);
        if(filteredStatus.isPresent()) {
            int i = 0;
            while (i < filteredStatus.get().size()) {
                if (filteredStatus.get().get(i).equals(PickupStatus.COMPLETED) || filteredStatus.get().get(i).equals(PickupStatus.CANCELLED))
                    i++;
                else
                    throw new EmployeeAlreadyAssignedPickupException("The employee with empId: [" + empId + "] is in use");
            }
        }
        Pickup pickupBatch =  repository.save(pickup);
        pickupBatch.setPickupStatus(PickupStatus.ASSIGNED);
        pickupBatch.setPickupType(PickupType.BATCH);
        repository.save(pickupBatch);
        return pickupBatch;
    }

    @Override
    public String cancelPickup(long pickupId) {
        /* *
         * Get the Pickup Object using the Pickup Id
         * If it doesn't exist, throw Exception
         * If it exists, check if the endTime is null, if yes, change the order status to Cancelled
         * If the end time is not null, check if the cancelled time is less than the end time, if yes, throw exception that pickup cannot be cancelled
         * */

        Pickup pickup = repository.findById(pickupId).orElse(null);
        if(pickup==null)
            throw new PickupIdNotFoundException("Pickup Id ["+pickupId+"] was not found!" );
        else{
            if(pickup.getPickupEndTime()==null){
                pickup.setPickupStatus(PickupStatus.CANCELLED);
                repository.save(pickup);
                return "SUCCESS!";
            }
            else
                throw new PickupNotCancelledException("Pickup cannot be cancelled as the Status is ["+pickup.getPickupStatus()+"]");
        }
    }

    @Override
    public PickupStatus pickupStatus(long pickupId) {
        Optional<Pickup> pickup = repository.findById(pickupId);
        if(pickup.isPresent())
            return pickup.get().getPickupStatus();
        else
            throw new PickupIdNotFoundException("Pickup Id ["+pickupId+"] was not found!" );
    }
}
