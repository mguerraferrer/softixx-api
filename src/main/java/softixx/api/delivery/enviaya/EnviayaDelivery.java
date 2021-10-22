package softixx.api.delivery.enviaya;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.val;
import softixx.api.delivery.enviaya.EnviayaCatalogue.CatalogueType;
import softixx.api.delivery.enviaya.EnviayaPickup.EnviayaPickupResponse;
import softixx.api.delivery.enviaya.EnviayaRate.EnviayaRateResponse;
import softixx.api.delivery.enviaya.EnviayaShipment.EnviayaShipmentBooking;
import softixx.api.delivery.enviaya.EnviayaShipment.EnviayaShipmentData;
import softixx.api.delivery.enviaya.EnviayaTracking.EnviayaTrackingResponse;
import softixx.api.util.UDateTime;
import softixx.api.util.UValidator;
import softixx.api.util.UDateTime.Formatter;

/**
 * EnviaYa delivery operations
 * 
 * @since 1.2.0
 * @author Maikel Guerra Ferrer
 */
public abstract class EnviayaDelivery {
	private static final Logger log = LoggerFactory.getLogger(EnviayaDelivery.class);

	// POST
	private static final String RATES_URL = "https://enviaya.com.mx/api/v1/rates";
	// POST
	private static final String SHIPMENTS_BOOKING_URL = "https://enviaya.com.mx/api/v1/shipments";
	// GET
	private static final String SHIPMENTS_LOOK_UP_URL = "https://enviaya.com.mx/api/v1/shipments/{enviayaId}?api_key={apiKey}";
	// POST
	private static final String SHIPMENTS_CANCELLATION_URL = "https://enviaya.com.mx/api/v1/request_cancellation";
	// POST
	private static final String SHIPMENTS_TRACKING_URL = "https://enviaya.com.mx/api/v1/trackings";
	// POST
	private static final String PICKUPS_BOOKING_URL = "https://enviaya.com.mx/api/v1/pickups";
	// GET
	private static final String PICKUPS_LOOK_UP_URL = "https://enviaya.com.mx/api/v1/pickups/{pickupId}?api_key={apiKey}";
	// GET
	private static final String CATALOGUE_CARRIERS_URL = "https://enviaya.com.mx/api/v1/get_carriers?api_key={apiKey}";
	// GET
	private static final String CATALOGUE_SERVICES_URL = "https://enviaya.com.mx/api/v1/get_services?api_key={apiKey}";
	// GET
	private static final String CATALOGUE_SHIPMENT_STATUSES_URL = "https://enviaya.com.mx/api/v1/get_shipment_statuses?api_key={apiKey}";
	// GET
	private static final String CATALOGUE_PICKUP_STATUSES_URL = "https://enviaya.com.mx/api/v1/get_pickup_statuses?api_key={apiKey}";

	private String apiKey;
	private Boolean showLogs;

	private static RestTemplate restTemplate = new RestTemplate();

	/**
	 * Prints logs information if showLogs is true. By default, showLogs is false.
	 */
	protected void showLogs() {
		this.showLogs = true;
	}

	/**
	 * EnviaYa configuration data initialization
	 *
	 * @since 1.2.0
	 * @param apiKey - (String) EnviaYa API key
	 */
	protected void enviayaConfig(final String apiKey) {
		this.apiKey = apiKey;
	}
	
	/**
	 * Enviaya rates
	 * 
	 * @param enviayaRating - {@link EnviayaRating}
	 * @see EnviayaRate
	 * @see EnviayaRating
	 * @since 1.2.0
	 * @return List<{@link EnviayaRate}>
	 */
	protected List<EnviayaRate> rates(final EnviayaRating enviayaRating) {
		try {

			if(UValidator.isNull(enviayaRating.getApiKey())) {
				enviayaRating.setApiKey(this.apiKey);
			}
			
			val requestJson = EnviayaRating.toJsonString(enviayaRating);
			if(this.showLogs) {
				log.info("EnviayaDelivery#rates request {}", requestJson);
			}

			val headers = getHeaders();
			val uri = getURI(RATES_URL);

			val request = new HttpEntity<String>(requestJson, headers);
			val response = restTemplate.postForEntity(uri, request, EnviayaRateResponse.class);

			val responseStatusCode = response.getStatusCode();
			val responseBody = response.getBody();
			
			if(this.showLogs) {
				log.info("EnviayaDelivery#rates responseStatusCode {}", responseStatusCode);
				log.info("EnviayaDelivery#rates responseBody {}", responseBody);
			}

			List<EnviayaRate> sourceRates = new ArrayList<>();
			sourceRates.addAll(responseBody.getUps());
			sourceRates.addAll(responseBody.getRedpack());
			sourceRates.addAll(responseBody.getEstafeta());
			sourceRates.addAll(responseBody.getFedex());
			sourceRates.addAll(responseBody.getPaquetexpress());
			sourceRates.addAll(responseBody.getMinutos());
			sourceRates.addAll(responseBody.getTresguerras());
			sourceRates.addAll(responseBody.getFour72());
			sourceRates.addAll(responseBody.getIvoy());
			sourceRates.addAll(responseBody.getAmpm());
			sourceRates.addAll(responseBody.getSendex());
			
			sourceRates.forEach(item -> {
				val dateStr = UDateTime.formatDate(UDateTime.parseDate(item.getEstimatedDelivery(), Formatter.DATE_DB_FORMAT), Formatter.DATE_SIMPLE_FORMAT);
				item.setEstimatedDelivery(dateStr);
			});
			
			val comparator = Comparator
								.comparing(EnviayaRate::getEstimatedDelivery)
                    			.thenComparingDouble(EnviayaRate::getTotalAmount);
			
			val rates = sourceRates.stream()
								   .sorted(comparator)
								   .collect(Collectors.toList());
			return rates;
			
		} catch (Exception e) {
			log.error("EnviayaDelivery#rates error {}", e.getMessage());
		}
		return new ArrayList<>();
	}
	
	/**
	 * Makes a shipment booking
	 * 
	 * @param shipmentBooking - {@link EnviayaShipmentBooking}
	 * @see EnviayaShipment
	 * @see EnviayaShipmentBooking
	 * @since 1.2.0
	 * @return {@link EnviayaShipment}
	 */
	protected EnviayaShipment shipmentBooking(final EnviayaShipmentBooking shipmentBooking) {
		try {
			
			if(UValidator.isNull(shipmentBooking.getApiKey())) {
				shipmentBooking.setApiKey(this.apiKey);
			}
			
			val requestJson = EnviayaShipmentBooking.toJsonString(shipmentBooking);
			if(this.showLogs) {
				log.info("EnviayaDelivery#shipmentBooking request {}", requestJson);
			}

			val headers = getHeaders();
			val uri = getURI(SHIPMENTS_BOOKING_URL);

			val request = new HttpEntity<String>(requestJson, headers);
			val response = restTemplate.postForEntity(uri, request, EnviayaShipment.class);

			val responseStatusCode = response.getStatusCode();
			val responseBody = response.getBody();
			
			if(this.showLogs) {
				log.info("EnviayaDelivery#shipmentBooking responseStatusCode {}", responseStatusCode);
				log.info("EnviayaDelivery#shipmentBooking responseBody {}", responseBody);
			}
			
			return responseBody;
			
		} catch (Exception e) {
			log.error("EnviayaDelivery#shipmentBooking error {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Shipment look up
	 * 
	 * @param data - {@link EnviayaShipmentData}
	 * @see EnviayaShipment
	 * @see EnviayaShipmentData
	 * @since 1.2.0
	 * @return {@link EnviayaShipment}
	 */
	protected EnviayaShipment shipmentLookUp(final EnviayaShipmentData data) {
		try {
			
			if(UValidator.isNull(data.getApiKey())) {
				data.setApiKey(this.apiKey);
			}
			
			val requestJson = EnviayaShipmentData.toJsonString(data);
			val url = sanitizeUrl(SHIPMENTS_LOOK_UP_URL, "enviayaId", data.getEnviayaId());
			
			if(this.showLogs) {
				log.info("EnviayaDelivery#shipmentLookUp url {}", url);
				log.info("EnviayaDelivery#shipmentLookUp request {}", requestJson);
			}
			
			val headers = getHeaders();
			val uri = getURI(url);

			val request = new HttpEntity<String>(requestJson, headers);
			val response = restTemplate.postForEntity(uri, request, EnviayaShipment.class);

			val responseStatusCode = response.getStatusCode();
			val responseBody = response.getBody();
			
			if(this.showLogs) {
				log.info("EnviayaDelivery#shipmentLookUp responseStatusCode {}", responseStatusCode);
				log.info("EnviayaDelivery#shipmentLookUp responseBody {}", responseBody);
			}
			
			return responseBody;
			
		} catch (Exception e) {
			log.error("EnviayaDelivery#shipmentLookUp error {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Shipment cancellation
	 * 
	 * @param data {@link EnviayaShipmentData}
	 * @see EnviayaShipment
	 * @see EnviayaShipmentData
	 * @since 1.2.0
	 * @return {@link EnviayaShipment}
	 */
	protected EnviayaShipment shipmentCancellation(final EnviayaShipmentData data) {
		try {
			
			if(UValidator.isNull(data.getApiKey())) {
				data.setApiKey(this.apiKey);
			}
			
			val requestJson = EnviayaShipmentData.toJsonString(data);
			if(this.showLogs) {
				log.info("EnviayaDelivery#shipmentCancellation request {}", requestJson);
			}

			val headers = getHeaders();
			val uri = getURI(SHIPMENTS_CANCELLATION_URL);

			val request = new HttpEntity<String>(requestJson, headers);
			val response = restTemplate.postForEntity(uri, request, EnviayaShipment.class);

			val responseStatusCode = response.getStatusCode();
			val responseBody = response.getBody();
			
			if(this.showLogs) {
				log.info("EnviayaDelivery#shipmentCancellation responseStatusCode {}", responseStatusCode);
				log.info("EnviayaDelivery#shipmentCancellation responseBody {}", responseBody);
			}
			
			return responseBody;
			
		} catch (Exception e) {
			log.error("EnviayaDelivery#shipmentCancellation error {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Shipment tracking
	 * 
	 * @param tracking - {@link EnviayaTracking}
	 * @see EnviayaTrackingResponse
	 * @see EnviayaTracking
	 * @since 1.2.0
	 * @return {@link EnviayaTrackingResponse}
	 */
	protected EnviayaTrackingResponse shipmentTracking(final EnviayaTracking tracking) {
		try {
			
			if(UValidator.isNull(tracking.getApiKey())) {
				tracking.setApiKey(this.apiKey);
			}
			
			val requestJson = EnviayaTracking.toJsonString(tracking);
			if(this.showLogs) {
				log.info("EnviayaDelivery#shipmentTracking request {}", requestJson);
			}

			val headers = getHeaders();
			val uri = getURI(SHIPMENTS_TRACKING_URL);

			val request = new HttpEntity<String>(requestJson, headers);
			val response = restTemplate.postForEntity(uri, request, EnviayaTrackingResponse.class);

			val responseStatusCode = response.getStatusCode();
			val responseBody = response.getBody();
			
			if(this.showLogs) {
				log.info("EnviayaDelivery#shipmentTracking responseStatusCode {}", responseStatusCode);
				log.info("EnviayaDelivery#shipmentTracking responseBody {}", responseBody);
			}
			
			return responseBody;
			
		} catch (Exception e) {
			log.error("EnviayaDelivery#shipmentTracking error {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Pickups booking
	 * 
	 * @param enviayaPickup - {@link EnviayaPickup}
	 * @see EnviayaPickupResponse
	 * @see EnviayaPickup
	 * @since 1.2.0
	 * @return {@link EnviayaPickupResponse}
	 */
	protected EnviayaPickupResponse pickupsBooking(final EnviayaPickup enviayaPickup) {
		try {

			if(UValidator.isNull(enviayaPickup.getApiKey())) {
				enviayaPickup.setApiKey(this.apiKey);
			}
			
			val requestJson = EnviayaPickup.toJsonString(enviayaPickup);
			if(this.showLogs) {
				log.info("EnviayaDelivery#pickupsBooking request {}", requestJson);
			}

			val headers = getHeaders();
			val uri = getURI(PICKUPS_BOOKING_URL);

			val request = new HttpEntity<String>(requestJson, headers);
			val response = restTemplate.postForEntity(uri, request, EnviayaPickupResponse.class);

			val responseStatusCode = response.getStatusCode();
			val responseBody = response.getBody();
			
			if(this.showLogs) {
				log.info("EnviayaDelivery#pickupsBooking responseStatusCode {}", responseStatusCode);
				log.info("EnviayaDelivery#pickupsBooking responseBody {}", responseBody);
			}

			return responseBody;
			
		} catch (Exception e) {
			log.error("EnviayaDelivery#pickupsBooking error {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Pickups look up
	 * 
	 * @param enviayaPickup - {@link EnviayaPickup}
	 * @see EnviayaPickupResponse
	 * @see EnviayaPickup
	 * @since 1.2.0
	 * @return {@link EnviayaPickupResponse}
	 */
	protected EnviayaPickupResponse pickupsLookUp(final EnviayaPickup enviayaPickup) {
		try {
			
			if(UValidator.isNull(enviayaPickup.getApiKey())) {
				enviayaPickup.setApiKey(this.apiKey);
			}
			
			val requestJson = EnviayaPickup.toJsonString(enviayaPickup);
			val url = sanitizeUrl(PICKUPS_LOOK_UP_URL, "pickupId", enviayaPickup.getId().toString());
			
			if(this.showLogs) {
				log.info("EnviayaDelivery#pickupsLookUp url {}", url);
				log.info("EnviayaDelivery#pickupsLookUp request {}", requestJson);
			}

			val headers = getHeaders();
			val uri = getURI(url);

			val request = new HttpEntity<String>(requestJson, headers);
			val response = restTemplate.postForEntity(uri, request, EnviayaPickupResponse.class);

			val responseStatusCode = response.getStatusCode();
			val responseBody = response.getBody();
			
			if(this.showLogs) {
				log.info("EnviayaDelivery#pickupsLookUp responseStatusCode {}", responseStatusCode);
				log.info("EnviayaDelivery#pickupsLookUp responseBody {}", responseBody);
			}
			
			return responseBody;
			
		} catch (Exception e) {
			log.error("EnviayaDelivery#pickupsLookUp error {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Returns a {@link EnviayaCatalogue}
	 * 
	 * @param type - {@link CatalogueType}
	 * @see EnviayaCatalogue
	 * @see CatalogueType
	 * @since 1.2.0
	 * @return {@link EnviayaCatalogue}
	 */
	protected EnviayaCatalogue getCatalogue(final CatalogueType type) {
		try {
			
			val url = switch (type) {
				case CARRIERS -> sanitizeUrl(CATALOGUE_CARRIERS_URL);
				case SHIPMENT_STATUSES -> sanitizeUrl(CATALOGUE_SHIPMENT_STATUSES_URL);
				case PICKUP_STATUSES -> sanitizeUrl(CATALOGUE_PICKUP_STATUSES_URL);
				case SERVICES -> sanitizeUrl(CATALOGUE_SERVICES_URL);
				default -> throw new IllegalArgumentException("getCatalogue unexpected value: " + type);
			};
			
			if(this.showLogs) {
				log.info("EnviayaDelivery#getCatalogue url {}", url);
			}
			
			val headers = getHeaders();
			val uri = getURI(url);

			val request = new HttpEntity<String>(headers);
			val response = restTemplate.postForEntity(uri, request, EnviayaCatalogue.class);

			val responseStatusCode = response.getStatusCode();
			val responseBody = response.getBody();
			
			if(this.showLogs) {
				log.info("EnviayaDelivery#getCatalogue responseStatusCode {}", responseStatusCode);
				log.info("EnviayaDelivery#getCatalogue responseBody {}", responseBody);
			}
			
		} catch (Exception e) {
			log.error("EnviayaDelivery#getCatalogue error {}", e.getMessage());
		}
		return null;
	}
	
	private HttpHeaders getHeaders() {
		val headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
	private URI getURI(final String url) {
		try {
			
			return UriComponentsBuilder.fromUri(new URI(url)).build().encode().toUri();
		
		} catch (Exception e) {
			log.error("EnviayaDelivery#getURI error {}", e.getMessage());
		}
		return null;
	}
	
	private String sanitizeUrl(final String url) {
		val sanitizedUrl = url.replace("{apiKey}", this.apiKey); 
		return sanitizedUrl;
	}
	
	private String sanitizeUrl(final String url, final String target, final String value) {
		val rep = "{" + target + "}";
		val sanitizedUrl = url.replace("{apiKey}", this.apiKey).replace(rep, value); 
		return sanitizedUrl;
	}
}