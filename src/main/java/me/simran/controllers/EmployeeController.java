package me.simran.controllers;

import io.swagger.annotations.*;
import me.simran.entity.models.Pickup;
import me.simran.service.Employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"Employee Controller"})
@SwaggerDefinition(tags={
        @Tag(name="Employee Controller", description="API Description for Employee Controller to Fill in the Start Time, End Time and Tote Weight Carried in each pickup")
})
public class EmployeeController {

    private EmployeeService service;

    public EmployeeController(EmployeeService service){
        this.service = service;
    }

    @PutMapping("insertStartTime/{pickupId}")
    @ApiOperation(value="Insert Start Time",
            notes="Inserts the Start Time into the Order and Changes the Status to InProgress")
    public ResponseEntity<String> insertStartTime(@ApiParam(value="Id of the Pickup Order", required=true)@PathVariable long pickupId, @RequestBody Pickup pickup){
        service.updateStartTime(pickupId, pickup);
        return new ResponseEntity<>("Pickup Start Time has been added and Status has been changed!", HttpStatus.OK);
    }

    @PutMapping("insertEndTime/{pickupId}")
    @ApiOperation(value="Insert End Time",
            notes="Inserts the End Time into the Order and Changes the Status to Completed")
    public ResponseEntity<String> insertEndTime(@ApiParam(value="Id of the Pickup Order")@PathVariable long pickupId, @RequestBody Pickup pickup){
        service.updateEndTime(pickupId, pickup);
        return new ResponseEntity<>("Pickup End Time has been added and Status has been changed!", HttpStatus.OK);
    }

    @PutMapping("insertToteWeight/{pickupId}")
    @ApiOperation(value="Insert Tote Weight",
            notes="Inserts the Tote Weight Carried by the Employee")
    public ResponseEntity<String> insertToteWeight(@ApiParam(value="Id of the Pickup Order")@PathVariable long pickupId, @RequestBody Pickup pickup){
        service.updateToteWeight(pickupId, pickup);
        return new ResponseEntity<>("Tote Weight has been modified", HttpStatus.OK);
    }
}
