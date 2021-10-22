package softixx.api.payment.stripe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.ApiConnectionException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.RateLimitException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.val;
import softixx.api.util.UCalculation;
import softixx.api.util.UValidator;

/**
 * Online payments through Stripe <br>
 * Require {@link Stripe} API
 * 
 * @author Maikel Guerra Ferrer
 * @since 1.2.0
 */
public abstract class StripePayment {
	private static final Logger log = LoggerFactory.getLogger(StripePayment.class);
	
	private StripeConfiguration stripeConfiguration;
	private Boolean showLogs;
	private static Gson gson = new Gson();
	private String errorCode;
	private String errorMessage;

	/**
	 * Prints logs information if showLogs is true. By default, showLogs is false.
	 */
	protected void showLogs() {
		this.showLogs = true;
	}
	
	/**
	 * Stripe configuration data initialization
	 *
	 * @since 1.2.0
	 * @param apiPublicKey - (String) Stripe public key
	 * @param apiSecretKey - (String) Stripe secret key
	 * @see ConektaConfiguration
	 */
	protected void stripeConfig(final String apiPublicKey, final String apiSecretKey) {
		this.stripeConfiguration = StripeConfiguration.instance(apiPublicKey, apiSecretKey);
		this.showLogs = false;
	}
	
	/**
	 * Generate a payment intent with Stripe
	 * 
	 * @since 1.2.0
	 * @param data - {@link StripeData} 
	 * @return String with the information of the payment intent
	 * @see StripeData
	 */
	protected String paymentIntent(final StripeData data) throws Exception {
		try {
			
			if(data == null) {
				throw new Exception("Error: stripeDataBean is null!");
			}
			
			//##### Initialization Stripe
			Stripe.apiKey = this.stripeConfiguration.getApiSecretKey();
			
			//##### PaymentIntent paramas
			val createParams = new PaymentIntentCreateParams.Builder()
															.setDescription(data.getDescription())
															.setCurrency(data.getCurrency())
															.setAmount(UCalculation.penniesLong(data.getAmount()))
															.build();
			//##### Create a PaymentIntent with the order amount and currency
			val intent = PaymentIntent.create(createParams);
			val clientSecret = new CreatePaymentResponse(intent.getClientSecret());
			val response = gson.toJson(clientSecret);
			
			if(this.showLogs) {
				log.info("Stripe paymentIntent success");
				log.info("Stripe paymentIntent response > {}", response);
			}
			
			return response;
			
		} catch (Exception e) {
			if (this.showLogs) {
				e.printStackTrace();
				log.error("Stripe paymentIntent error > {}", e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * Generate a charge with Stripe.
	 * If your API key is in test mode, the supplied payment source (e.g., card)
	 * won’t actually be charged, although everything else will occur as if in live
	 * mode. (Stripe assumes that thecharge would have completed successfully).
	 * 
	 * @param data - {@link StripeData}
	 * @return {@link StripeChargeResponse}
	 * @see StripeData
	 * @see StripeChargeResponse
	 */
	protected StripeChargeResponse charge(final StripeData data) {
		StripeChargeResponse response = null;
		
		try {
			
			//##### Initialization Stripe
			Stripe.apiKey = this.stripeConfiguration.getApiSecretKey();
			
			val params = ChargeCreateParams.builder()
							.setAmount(UCalculation.penniesLong(data.getAmount()))
							.setCurrency(data.getCurrency().toLowerCase())
							.setDescription(data.getDescription())
							.setSource(data.getSource())
							.setCustomer(data.getCustomerId())
							.build();
			
			Charge charge = Charge.create(params);
			response = StripeChargeResponse.mapper(charge);
		
		} catch (CardException e) {
			// CardException - Since it's a decline, CardException will be caught
			if (this.showLogs) {
				log.error("Stripe charge CardException > {}", e.getMessage());
			}
			stripeCatchError(e);
		} catch (RateLimitException e) {
			// Too many requests made to the API too quickly
			if (this.showLogs) {
				log.error("Stripe charge RateLimitException > {}", e.getMessage());
			}
			stripeCatchError(e);
		} catch (InvalidRequestException e) {
			// Invalid parameters were supplied to Stripe's API
			if (this.showLogs) {
				log.error("Stripe charge InvalidRequestException > {}", e.getMessage());
			}
			stripeCatchError(e);
		} catch (AuthenticationException e) {
			// Authentication with Stripe's API failed (maybe you changed API keys recently)
			if (this.showLogs) {
				log.error("Stripe charge AuthenticationException > {}", e.getMessage());
			}
			stripeCatchError(e);
		} catch (ApiConnectionException e) {
			// Network communication with Stripe failed
			if (this.showLogs) {
				log.error("Stripe charge ApiConnectionException > {}", e.getMessage());
			}
			stripeCatchError(e);
		} catch (StripeException e) {
			// Display a very generic error to the user, and maybe send yourself an email
			if (this.showLogs) {
				log.error("Stripe charge StripeException > {}", e.getMessage());
			}
			stripeCatchError(e);
		} catch (Exception e) {
			// Something else happened, completely unrelated to Stripe
			if (this.showLogs) {
				errorMessage = e.getMessage();
				log.error("Stripe charge Exception > {}", e.getMessage());
			}
		}
		
		if(UValidator.isNotEmpty(this.errorMessage)) {
			response = StripeChargeResponse
						.builder()
						.failureCode(this.errorCode)
						.failureMessage(this.errorMessage)
						.build();
		}
		return response;
	}
	
	/**
	 * Parses the payment response from Stripe
	 * @param json - A json response from Stripe
	 * @return {@link StripePaymentIntentResponse}
	 * @see StripePaymentIntentResponse
	 * @throws Exception
	 */
	protected StripePaymentIntentResponse paymentResponse(final String json) throws Exception {
		if(json == null) {
			throw new Exception("Error: json data is null!");
		}
		
		try {
			
			val objectMapper = new ObjectMapper();
			val stripeResponse = objectMapper.readValue(json, StripePaymentIntentResponse.class);
			return stripeResponse;
			
		} catch (Exception e) {
			if (this.showLogs) {
				e.printStackTrace();
				log.error("Stripe paymentResponse error > {}", e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		
	}
	
	private void stripeCatchError(StripeException e) {
		if (this.showLogs) {
			log.error("Stripe catch error message > {}", e.getCode());
			log.error("Stripe catch error message > {}", e.getMessage());
		}
		this.errorCode = e.getCode();
		this.errorMessage = e.getMessage();
	}
	
	/**
	 * Stripe configuration data <br>
	 * 
	 * {@value} <b>apiPublicKey</b> - (String) Stripe public key <br>
	 * {@value} <b>apiSecretKey</b> - (String) Stripe secret key
	 * @since 1.2.0
	 */
	@Data
	@Builder
	protected static class StripeConfiguration {
		private String apiPublicKey;
		private String apiSecretKey;

		/**
		 * A static instance of StripeConfiguration
		 *
		 * @param apiPublicKey - (String) Stripe public key
		 * @param apiSecretKey - (String) Stripe secret key
		 * @return A new instance of StripeConfiguration
		 */
		public static StripeConfiguration instance(final String apiPublicKey, final String apiSecretKey) {
			return StripeConfiguration
					.builder()
					.apiPublicKey(apiPublicKey)
					.apiSecretKey(apiSecretKey)
					.build();
		}
	}
	
	@Data
	@AllArgsConstructor
	private static class CreatePaymentResponse {
		private String clientSecret;
	}
}
