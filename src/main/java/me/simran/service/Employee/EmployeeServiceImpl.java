package me.simran.service.Employee;

import me.simran.entity.enums.PickupStatus;
import me.simran.entity.enums.PickupType;
import me.simran.entity.models.Employee;
import me.simran.entity.models.EmployeePerformance;
import me.simran.entity.models.Pickup;
import me.simran.exception.PickupIdNotFoundException;
import me.simran.repository.Employee.EmployeeRepository;
import me.simran.service.kafka.producer.ProducerServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    Random random = new Random();

    long id = random.nextLong();
    private final EmployeeRepository repository;
    private final ProducerServiceImpl producerService;

    public EmployeeServiceImpl(EmployeeRepository repository,ProducerServiceImpl producerService){
        this.repository = repository;
        this.producerService = producerService;
    }


    @Override
    public void updateStartTime(long pickupId, Pickup pickupNew) {
        Pickup pickup = repository.findById(pickupId).orElse(null);

        if(pickup!=null) {
            pickupNew.setPickupType(pickup.getPickupType());
            pickupNew.setPickupStatus(PickupStatus.IN_PROGRESS);
            pickupNew.setEmployee(pickup.getEmployee());
            repository.save(pickupNew);
        }
        else
            throw new PickupIdNotFoundException("Pickup Id ["+pickupId+"] was not found!" );
    }

    @Override
    public void updateEndTime(long pickupId, Pickup pickupNew) {

        Pickup pickup = repository.findById(pickupId).orElse(null);
        pickupNew.setPickupStatus(PickupStatus.COMPLETED);
        pickupNew.setPickupStartTime(pickup.getPickupStartTime());
        pickupNew.setPickupType(pickup.getPickupType());
        pickupNew.setEmployee(pickup.getEmployee());

        if(pickup!=null) {
            repository.save(pickupNew);

            Employee employee = pickupNew.getEmployee();

            /** Converting the time to millis */
            long milliStartTime = pickupNew.getPickupStartTime().getTime();
            long milliEndTime = pickupNew.getPickupEndTime().getTime();
            long difference = milliEndTime - milliStartTime;

            if(pickupNew.getPickupType().equals(PickupType.SINGLE)){

                /**Incrementing the Count Value by 1*/
                int countSingle = employee.getSinglePickupCount();
                employee.setSinglePickupCount(countSingle+1);

                /**Assigning the difference in ms to differenceInSingleTimeForEachPickup and storing back pickup**/
                employee.setDifferenceSinglePickupTime(difference);
                pickupNew.setEmployee(employee);
                repository.save(pickupNew);

                /**Get the employeePerformance row for the Certain employee**/
                EmployeePerformance performanceRetrieved = repository.findEmployeeWithEmpId(employee.getEmpId()).orElse(null);

                /**If it is present, extract the values and update it with new values and store it back*/
                if(performanceRetrieved!=null){

                long newTotalCount = performanceRetrieved.getAverageSinglePickupTime() * performanceRetrieved.getSinglePickupTotalCount() + difference;

                int newSingleCount = performanceRetrieved.getSinglePickupTotalCount()+1;

                long newAverageSingle = newTotalCount/newSingleCount;

                performanceRetrieved.setSinglePickupTotalCount(newSingleCount);

                performanceRetrieved.setAverageSinglePickupTime(newAverageSingle);

                performanceRetrieved.setBatchPickupCount(performanceRetrieved.getBatchPickupCount());

                performanceRetrieved.setAverageBatchPickupTime(performanceRetrieved.getAverageBatchPickupTime());

                repository.updatePerformanceRetrieved(performanceRetrieved.getId(), performanceRetrieved.getAverageBatchPickupTime(), performanceRetrieved.getAverageSinglePickupTime(), performanceRetrieved.getBatchPickupCount(), performanceRetrieved.getEmpId(), performanceRetrieved.getSinglePickupTotalCount());

                producerService.sendPerformanceStats(performanceRetrieved);
                }

                /**Creating a new Entry for Single Pickup Count**/
                else{
                    id++;
                    repository.insertIntoPerformance(id,0, difference, 0, employee.getEmpId(), 1);
                    EmployeePerformance newPerformance = new EmployeePerformance();
                    newPerformance.setEmpId(id);
                    newPerformance.setAverageBatchPickupTime(0);
                    newPerformance.setAverageSinglePickupTime(difference);
                    newPerformance.setBatchPickupCount(0);
                    newPerformance.setEmpId(employee.getEmpId());
                    newPerformance.setSinglePickupTotalCount(1);

                    producerService.sendPerformanceStats(newPerformance);
                }

            }

            else if(pickupNew.getPickupType().equals(PickupType.BATCH)){
                /**Incrementing the Count Value by 1*/
                int countBatch = employee.getSinglePickupCount();
                employee.setBatchPickupCount(countBatch+1);

                /**Assigning the difference in ms to differenceInBatchTimeForEachPickup and storing back pickup**/
                employee.setDifferenceBatchPickupTime(difference);
                pickupNew.setEmployee(employee);
                repository.save(pickupNew);

                /**Get the employeePerformance row for the Certain employee**/
                EmployeePerformance performanceRetrieved = repository.findEmployeeWithEmpId(employee.getEmpId()).orElse(null);;

                /**If it is present, extract the values and update it with new values and store it back*/
                if(performanceRetrieved!=null){

                    long newTotalCount = performanceRetrieved.getAverageBatchPickupTime() * performanceRetrieved.getBatchPickupCount() + difference;

                    performanceRetrieved.setBatchPickupCount(performanceRetrieved.getBatchPickupCount()+1);

                    performanceRetrieved.setAverageBatchPickupTime(newTotalCount/performanceRetrieved.getBatchPickupCount());

                    performanceRetrieved.setSinglePickupTotalCount(performanceRetrieved.getSinglePickupTotalCount());

                    performanceRetrieved.setAverageSinglePickupTime(performanceRetrieved.getAverageSinglePickupTime());

                    repository.updatePerformanceRetrieved(performanceRetrieved.getId(), performanceRetrieved.getAverageBatchPickupTime(), performanceRetrieved.getAverageSinglePickupTime(), performanceRetrieved.getBatchPickupCount(), performanceRetrieved.getEmpId(), performanceRetrieved.getSinglePickupTotalCount());

                    producerService.sendPerformanceStats(performanceRetrieved);
                }

                /**Creating a new Entry for Batch Pickup Count**/
                else{
                    id++;
                    repository.insertIntoPerformance(id,difference, 0, 1, employee.getEmpId(), 0);
                    EmployeePerformance newPerformance = new EmployeePerformance();
                    newPerformance.setEmpId(id);
                    newPerformance.setAverageBatchPickupTime(0);
                    newPerformance.setAverageSinglePickupTime(difference);
                    newPerformance.setBatchPickupCount(0);
                    newPerformance.setEmpId(employee.getEmpId());
                    newPerformance.setSinglePickupTotalCount(1);

                    producerService.sendPerformanceStats(newPerformance);
                }
                producerService.sendPerformanceStats(performanceRetrieved);
            }
        }

        else
            throw new PickupIdNotFoundException("Pickup Id ["+pickupId+"] was not found!" );
    }

    @Override
    public void updateToteWeight(long pickupId, Pickup pickupNew) {
        Pickup pickup = repository.findById(pickupId).orElse(null);

        pickupNew.setPickupType(pickup.getPickupType());
        pickupNew.setPickupStatus(pickup.getPickupStatus());
        pickupNew.setPickupStartTime(pickup.getPickupStartTime());
        pickupNew.setPickupEndTime(pickup.getPickupEndTime());

        if(pickup!=null) {
            repository.save(pickupNew);
        }
        else
            throw new PickupIdNotFoundException("Pickup Id ["+pickupId+"] was not found!" );
    }
}
