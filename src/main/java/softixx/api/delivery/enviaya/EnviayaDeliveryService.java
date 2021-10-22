package softixx.api.delivery.enviaya;

import java.util.List;

import org.springframework.stereotype.Service;

import softixx.api.delivery.enviaya.EnviayaCatalogue.CatalogueType;
import softixx.api.delivery.enviaya.EnviayaPickup.EnviayaPickupResponse;
import softixx.api.delivery.enviaya.EnviayaShipment.EnviayaShipmentBooking;
import softixx.api.delivery.enviaya.EnviayaShipment.EnviayaShipmentData;
import softixx.api.delivery.enviaya.EnviayaTracking.EnviayaTrackingResponse;

/**
 * Service that manages the EnviaYa delivery operations
 * @since 1.2.0
 * @author Maikel Guerra Ferrer
 */
@Service
public class EnviayaDeliveryService extends EnviayaDelivery {

	@Override
	public void enviayaConfig(final String apiKey) {
		super.enviayaConfig(apiKey);
	}

	@Override
	public List<EnviayaRate> rates(final EnviayaRating enviayaRating) {
		return super.rates(enviayaRating);
	}

	@Override
	public EnviayaShipment shipmentBooking(final EnviayaShipmentBooking shipmentBooking) {
		return super.shipmentBooking(shipmentBooking);
	}

	@Override
	public EnviayaShipment shipmentLookUp(final EnviayaShipmentData data) {
		return super.shipmentLookUp(data);
	}

	@Override
	public EnviayaShipment shipmentCancellation(final EnviayaShipmentData data) {
		return super.shipmentCancellation(data);
	}

	@Override
	public EnviayaTrackingResponse shipmentTracking(final EnviayaTracking tracking) {
		return super.shipmentTracking(tracking);
	}
	
	@Override
	public EnviayaPickupResponse pickupsBooking(EnviayaPickup enviayaPickup) {
		return super.pickupsBooking(enviayaPickup);
	}

	@Override
	public EnviayaPickupResponse pickupsLookUp(EnviayaPickup enviayaPickup) {
		return super.pickupsLookUp(enviayaPickup);
	}

	@Override
	public EnviayaCatalogue getCatalogue(final CatalogueType type) {
		return super.getCatalogue(type);
	}

	@Override
	public void showLogs() {
		super.showLogs();
	}

}
