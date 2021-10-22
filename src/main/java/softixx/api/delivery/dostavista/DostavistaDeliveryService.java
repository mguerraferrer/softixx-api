package softixx.api.delivery.dostavista;

import java.util.List;

import org.springframework.stereotype.Service;

import softixx.api.delivery.dostavista.DostavistaFindOrder.DostavistaFindOrderResponse;

/**
 * Service that manages the Dostavista delivery operations <br><br>
 * 
 * Use {@link DostavistaDeliveryService#dostavistaConfig(String)} for production <br>
 * Use {@link DostavistaDeliveryService#dostavistaTestConfig(String)} for tests
 * @since 1.2.0
 * @author Maikel Guerra Ferrer
 */
@Service
public class DostavistaDeliveryService extends DostavistaDelivery {

	@Override
	public void dostavistaConfig(final String authToken) {
		super.dostavistaConfig(authToken);
	}

	@Override
	public void dostavistaTestConfig(final String authToken) {
		super.dostavistaTestConfig(authToken);
	}

	@Override
	public DostavistaResponse calculateOrder(final DostavistaRequest dostavistaRequest) {
		return super.calculateOrder(dostavistaRequest);
	}
	
	@Override
	public DostavistaResponse createOrder(final DostavistaRequest dostavistaRequest) {
		return super.createOrder(dostavistaRequest);
	}

	@Override
	public DostavistaFindOrderResponse orderList(final DostavistaFindOrder findOrder) {
		return super.orderList(findOrder);
	}

	@Override
	public DostavistaResponse cancelOrder(final Integer orderId) {
		return super.cancelOrder(orderId);
	}

	@Override
	public void showLogs() {
		super.showLogs();
	}
	
	/**
	 * Order statuses 
	 * 
	 * @since 1.2.0
	 * @return List with order status
	 */
	public List<String> orderStatus() {
		return DostavistaStatuses.getOrderStatus();
	}
	
	/**
	 * Delivery statuses 
	 * 
	 * @since 1.2.0
	 * @return List with delivery status
	 */
	public List<String> deliveryStatus() {
		return DostavistaStatuses.getDeliveryStatus();
	}

}