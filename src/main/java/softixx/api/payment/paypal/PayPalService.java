package softixx.api.payment.paypal;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.val;

@Component
public class PayPalService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private PayPalSettings payPalSettings;
	
	private static final String GET_ORDER_API = "/v2/checkout/orders/";
	
	public void init(PayPalSettings payPalSettings) {
		this.payPalSettings = payPalSettings;
	}
	
	public boolean validateOrder(String orderId) throws PayPalApiException {
		val orderResponse = getOrderDetails(orderId);
		if (orderResponse != null) {
			return orderResponse.validate(orderId);
		}
		return false;
	}

	private PayPalOrderResponse getOrderDetails(String orderId) throws PayPalApiException {
		val response = makeRequest(orderId);
		
		val statusCode = response.getStatusCode();
		if (!statusCode.equals(HttpStatus.OK)) {
			throwExceptionForNonOkResponse(statusCode);
		}
		
		return response.getBody();
	}

	private ResponseEntity<PayPalOrderResponse> makeRequest(String orderId) {		
		val baseURL = payPalSettings.url();
		val requestURL = baseURL + GET_ORDER_API + orderId;
		val clientId = payPalSettings.clientId();
		val clientSecret = payPalSettings.clientSecret();
		
		val headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Accept-Language", "en_US");
		headers.setBasicAuth(clientId, clientSecret);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
		return restTemplate.exchange(requestURL, HttpMethod.GET, request, PayPalOrderResponse.class);
	}

	private void throwExceptionForNonOkResponse(HttpStatus statusCode) throws PayPalApiException {
		String message = null;
		switch (statusCode) {
			case NOT_FOUND -> message = "Order ID not found";
			case BAD_REQUEST -> message = "Bad Request to PayPal Checkout API";
			case INTERNAL_SERVER_ERROR -> message = "PayPal server error";
			default -> message = "PayPal returned non-OK status code";
		}
		throw new PayPalApiException(message);
	}
	
}