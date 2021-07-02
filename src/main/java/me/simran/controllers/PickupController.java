package me.simran.controllers;

import io.swagger.annotations.*;
import me.simran.entity.enums.PickupStatus;
import me.simran.entity.models.Pickup;
import me.simran.service.Pickup.PickupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags={"Pickup Controller"})
@SwaggerDefinition(tags={
        @Tag(name="Pickup Controller", description="API Description for Order Pickup")
})
public class PickupController {

    private PickupService service;

    public PickupController(PickupService service){
        this.service = service;
    }

    @PostMapping("createSinglePickup")
    @ApiOperation(value="Creates a Single Order",
            notes="Returns the Single Order Created")
    public ResponseEntity<Pickup> createSinglePickup(@RequestBody Pickup pickup){
        return new ResponseEntity<>(service.saveSinglePickup(pickup), HttpStatus.CREATED);
    }

    @PostMapping("createBatchPickup")
    @ApiOperation(value="Creates a Batch Order",
            notes="Returns the Batch Order Created")
    public ResponseEntity<Pickup> createBatchPickup(@RequestBody Pickup pickup){
        return new ResponseEntity<>(service.saveBatchPickup(pickup), HttpStatus.CREATED);
    }

    @PostMapping("cancelPickup/{pickupId}")
    @ApiOperation(value="Cancels the Created Order",
            notes="Returns the Cancel order, if cancelled.")
    public ResponseEntity<String> cancelPickup(@ApiParam(value="Id of the Pickup Order") @PathVariable("pickupId") long pickupId){
        return new ResponseEntity<>(service.cancelPickup(pickupId), HttpStatus.OK);
    }

    @GetMapping("getPickupStatus/{id}")
    @ApiOperation(value="Creates a Batch Order",
            notes="Returns the Pickup Status of the Order")
    public ResponseEntity<PickupStatus> returnPickupStatus(@ApiParam(value="Id of the Pickup Order")@PathVariable("id") long pickupId){
        return new ResponseEntity<>(service.pickupStatus(pickupId), HttpStatus.OK);
    }
}
