package me.simran.service.Pickup;

import me.simran.entity.enums.PickupStatus;
import me.simran.entity.models.Pickup;

public interface PickupService {

    Pickup saveSinglePickup(Pickup pickup);

    Pickup saveBatchPickup(Pickup pickup);

    String cancelPickup(long pickupId);

    PickupStatus pickupStatus(long pickupId);
}
