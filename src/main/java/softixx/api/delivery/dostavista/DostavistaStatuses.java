package softixx.api.delivery.dostavista;

import java.util.List;

import softixx.api.util.UMessage;

/**
 * Dostavista delivery and order statuses
 * 
 * @since 1.2.0
 * @author Maikel Guerra Ferrer
 */
public class DostavistaStatuses {
	/**
	 * Delivery statuses 
	 * 
	 * @since 1.2.0
	 * @return List with delivery status
	 */
	protected static List<String> getDeliveryStatus() {
		return List.of("invalid", "draft", "planned", "active", "finished", "canceled", "delayed", "failed",
				"courier_assigned", "courier_departed", "parcel_picked_up", "courier_arrived", "deleted");
	}
	
	/**
	 * Validates if the given status is in delivery's statuses list
	 * 
	 * @param status - (String) An status to validate
	 * @since 1.2.0
	 * @return True if the given status is in delivery's statuses list
	 */
	protected static Boolean isValidDeliveryStatus(final String status) {
		return getDeliveryStatus().stream()
								  .filter(item -> item.equals(status))
								  .findAny()
								  .isPresent();
	}

	/**
	 * Delivery status internationalization
	 * 
	 * @param status - (String) current status
	 * @since 1.2.0
	 * @return Delivery status internationalization
	 */
	protected static String getDeliveryStatus(final String status) {
		return switch (status) {
		case "invalid" -> UMessage.getMessage("dostavista.delivery.status.invalid");
		case "draft" -> UMessage.getMessage("dostavista.delivery.status.draft");
		case "planned" -> UMessage.getMessage("dostavista.delivery.status.planned");
		case "active" -> UMessage.getMessage("dostavista.delivery.status.active");
		case "finished" -> UMessage.getMessage("dostavista.delivery.status.finished");
		case "canceled" -> UMessage.getMessage("dostavista.delivery.status.canceled");
		case "delayed" -> UMessage.getMessage("dostavista.delivery.status.delayed");
		case "failed" -> UMessage.getMessage("dostavista.delivery.status.failed");
		case "courier_assigned" -> UMessage.getMessage("dostavista.delivery.status.courier_assigned");
		case "courier_departed" -> UMessage.getMessage("dostavista.delivery.status.courier_departed");
		case "parcel_picked_up" -> UMessage.getMessage("dostavista.delivery.status.parcel_picked_up");
		case "courier_arrived" -> UMessage.getMessage("dostavista.delivery.status.courier_arrived");
		case "deleted" -> UMessage.getMessage("dostavista.delivery.status.deleted");
		default -> throw new IllegalArgumentException(
				"DostavistaStatuses#getDeliveryStatus unexpected value: " + status);
		};
	}

	/**
	 * Order statuses 
	 * 
	 * @since 1.2.0
	 * @return List with order status
	 */
	protected static List<String> getOrderStatus() {
		return List.of("new", "available", "active", "completed", "reactivated", "draft", "delayed", "canceled");
	}
	
	/**
	 * Validates if the given status is in order's statuses list
	 * 
	 * @param status - (String) An status to validate
	 * @since 1.2.0
	 * @return True if the given status is in order status list
	 */
	protected static Boolean isValidOrderStatus(final String status) {
		return getOrderStatus().stream()
							   .filter(item -> item.equals(status))
							   .findAny()
							   .isPresent();
	}
	
	/**
	 * Order status internationalization
	 * 
	 * @param status - (String) current status
	 * @since 1.2.0
	 * @return Order status internationalization
	 */
	protected static String getOrderStatus(final String status) {
		return switch (status) {
		case "new" -> UMessage.getMessage("dostavista.order.status.new");
		case "available" -> UMessage.getMessage("dostavista.order.status.available");
		case "active" -> UMessage.getMessage("dostavista.order.status.active");
		case "completed" -> UMessage.getMessage("dostavista.order.status.completed");
		case "reactivated" -> UMessage.getMessage("dostavista.order.status.reactivated");
		case "draft" -> UMessage.getMessage("dostavista.order.status.draft");
		case "delayed" -> UMessage.getMessage("dostavista.order.status.delayed");
		case "canceled" -> UMessage.getMessage("dostavista.order.status.canceled");
		default -> throw new IllegalArgumentException("DostavistaStatuses#getOrderStatus unexpected value: " + status);
		};
	}
}