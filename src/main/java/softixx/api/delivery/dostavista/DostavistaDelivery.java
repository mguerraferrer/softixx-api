package softixx.api.delivery.dostavista;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.val;
import softixx.api.delivery.dostavista.DostavistaFindOrder.DostavistaFindOrderResponse;
import softixx.api.util.UValidator;

/**
 * Dostavista delivery operations
 * 
 * @since 1.2.0
 * @author Maikel Guerra Ferrer
 */
public abstract class DostavistaDelivery {
	private static final Logger log = LoggerFactory.getLogger(DostavistaDelivery.class);
	
	private static final String DOSTAVISTA_URL_TEST = "https://robotapitest.dostavista.mx/api/business/1.1"; 
	private static final String DOSTAVISTA_URL_PROD = "https://robot.dostavista.mx/api/business/1.1"; 

	private String baseUrl;
	private String authToken;
	private Boolean showLogs;

	private static RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * Prints logs information if showLogs is true. By default, showLogs is false.
	 */
	protected void showLogs() {
		this.showLogs = true;
	}

	/**
	 * Dostavista configuration data initialization for prod
	 *
	 * @since 1.2.0
	 * @param authToken - (String) Dostavista secret auth token
	 */
	protected void dostavistaConfig(final String authToken) {
		this.authToken = authToken;
		this.baseUrl = DOSTAVISTA_URL_PROD;
	}
	
	/**
	 * Dostavista configuration data initialization for test
	 *
	 * @since 1.2.0
	 * @param authToken - (String) Dostavista secret auth token
	 */
	protected void dostavistaTestConfig(final String authToken) {
		this.authToken = authToken;
		this.baseUrl = DOSTAVISTA_URL_TEST;
	}
	
	protected DostavistaResponse calculateOrder(final DostavistaRequest dostavistaRequest) {
		try {
			
			val url = this.baseUrl + "/calculate-order";
			val requestJson = DostavistaRequest.toJsonString(dostavistaRequest);
			
			val response = dostavistaRequest(url, requestJson);
			return response;
			
		} catch (Exception e) {
			log.error("DostavistaDelivery#calculateOrder error - {}", e.getMessage());
		}
		return null;
	}
	
	protected DostavistaResponse createOrder(final DostavistaRequest dostavistaRequest) {
		try {
			
			val url = this.baseUrl + "/create-order";
			val requestJson = DostavistaRequest.toJsonString(dostavistaRequest);
			
			val response = dostavistaRequest(url, requestJson);
			return response;
			
		} catch (Exception e) {
			log.error("DostavistaDelivery#calculateOrder error - {}", e.getMessage());
		}
		return null;
	}
	
	protected DostavistaResponse cancelOrder(final Integer orderId) {
		try {
			
			val url = this.baseUrl + "/cancel-order";
			val requestJson = "{\"order_id\":"+orderId+"}";
			
			val response = dostavistaRequest(url, requestJson);
			return response;
			
		} catch (Exception e) {
			log.error("DostavistaDelivery#calculateOrder error - {}", e.getMessage());
		}
		return null;
	}
	
	protected DostavistaFindOrderResponse orderList(final DostavistaFindOrder findOrder) {
		try {
			
			var params = "";
			var and = false;
			if(UValidator.isNotNull(findOrder.getOrderId())) {
				params = "?order_id=" + findOrder.getOrderId();
				and = true;
			}
			
			val status = findOrder.getStatus();
			if(UValidator.isNotNull(status)) {
				if(DostavistaStatuses.isValidOrderStatus(status)) {
					params += (and ? "&status=" : "?status=") + status;
					and = true;
				} else {
					throw new IllegalArgumentException(String.format("Invalid status '%' ", status));
				}
			}
			
			if(UValidator.isNotNull(findOrder.getOffset())) {
				params += (and ? "&offset=" : "?offset=") + findOrder.getOffset();
				and = true;
			}
			
			if(UValidator.isNotNull(findOrder.getCount())) {
				params += (and ? "&count=" : "?count=") + findOrder.getCount();
				and = true;
			}
			
			val url = this.baseUrl + "/orders" + params;
			
			if(this.showLogs) {
				log.info("DostavistaDelivery#orderList url {}", url);
			}
			
			val headers = getHeaders();
			val uri = getURI(url);
			
			val request = new HttpEntity<String>(headers);
			val response = restTemplate.exchange(uri, HttpMethod.GET, request, DostavistaFindOrderResponse.class);

			val responseStatusCode = response.getStatusCode();
			val responseBody = response.getBody();
			
			if(this.showLogs) {
				log.info("DostavistaDelivery#orderList responseStatusCode {}", responseStatusCode);
				log.info("DostavistaDelivery#orderList responseBody {}", responseBody);
			}
			
			return responseBody;
			
		} catch (Exception e) {
			log.error("DostavistaDelivery#calculateOrder error - {}", e.getMessage());
		}
		return null;
	}
	
	private DostavistaResponse dostavistaRequest(final String url, final String requestJson) {
		if(this.showLogs) {
			log.info("DostavistaDelivery#dostavistaRequest url {}", url);
			log.info("DostavistaDelivery#dostavistaRequest request {}", requestJson);
		}
		
		val headers = getHeaders();
		val uri = getURI(url);
		
		val request = new HttpEntity<String>(requestJson, headers);
		val response = restTemplate.postForEntity(uri, request, DostavistaResponse.class);

		val responseStatusCode = response.getStatusCode();
		val responseBody = response.getBody();
		
		if(this.showLogs) {
			log.info("DostavistaDelivery#dostavistaRequest responseStatusCode {}", responseStatusCode);
			log.info("DostavistaDelivery#dostavistaRequest responseBody {}", responseBody);
		}
		
		return responseBody;
	}
	
	private HttpHeaders getHeaders() {
		val headers = new HttpHeaders();
		headers.set("X-DV-Auth-Token", this.authToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
	private URI getURI(final String url) {
		try {
			
			return UriComponentsBuilder.fromUri(new URI(url)).build().encode().toUri();
		
		} catch (Exception e) {
			log.error("DostavistaDelivery#getURI error - {}", e.getMessage());
		}
		return null;
	}
}
