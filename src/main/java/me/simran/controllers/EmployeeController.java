package me.simran.controllers;

import me.simran.entity.models.Pickup;
import me.simran.service.Employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private EmployeeService service;

    public EmployeeController(EmployeeService service){
        this.service = service;
    }

    @PutMapping("insertStartTime/{pickupId}")
    public ResponseEntity<String> insertStartTime(@PathVariable long pickupId, @RequestBody Pickup pickup){
        service.updateStartTime(pickupId, pickup);
        return new ResponseEntity<>("Pickup Start Time has been added and Status has been changed!", HttpStatus.OK);
    }

    @PutMapping("insertEndTime/{pickupId}")
    public ResponseEntity<String> insertEndTime(@PathVariable long pickupId, @RequestBody Pickup pickup){
        service.updateEndTime(pickupId, pickup);
        return new ResponseEntity<>("Pickup End Time has been added and Status has been changed!", HttpStatus.OK);
    }

    @PutMapping("insertToteWeight/{pickupId}")
    public ResponseEntity<String> insertToteWeight(@PathVariable long pickupId, @RequestBody Pickup pickup){
        service.updateToteWeight(pickupId, pickup);
        return new ResponseEntity<>("Tote Weight has been modified", HttpStatus.OK);
    }
}
