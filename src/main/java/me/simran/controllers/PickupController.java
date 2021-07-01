package me.simran.controllers;

import me.simran.entity.enums.PickupStatus;
import me.simran.entity.models.Pickup;
import me.simran.service.Pickup.PickupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PickupController {

    private PickupService service;

    public PickupController(PickupService service){
        this.service = service;
    }

    @PostMapping("createSinglePickup")
    public ResponseEntity<Pickup> createSinglePickup(@RequestBody Pickup pickup){
        return new ResponseEntity<>(service.saveSinglePickup(pickup), HttpStatus.CREATED);
    }

    @PostMapping("createBatchPickup")
    public ResponseEntity<Pickup> createBatchPickup(@RequestBody Pickup pickup){
        return new ResponseEntity<>(service.saveBatchPickup(pickup), HttpStatus.CREATED);
    }

    @PostMapping("cancelPickup/{pickupId}")
    public ResponseEntity<String> cancelPickup(@PathVariable("pickupId") long pickupId){
        return new ResponseEntity<>(service.cancelPickup(pickupId), HttpStatus.OK);
    }

    @GetMapping("getPickupStatus/{id}")
    public ResponseEntity<PickupStatus> returnPickupStatus(@PathVariable("id") long pickupId){
        return new ResponseEntity<>(service.pickupStatus(pickupId), HttpStatus.OK);
    }
}
