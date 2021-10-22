package softixx.api.payment.openpay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Builder;
import lombok.Data;
import lombok.val;
import mx.openpay.client.Card;
import mx.openpay.client.Customer;
import mx.openpay.client.core.OpenpayAPI;
import mx.openpay.client.core.requests.transactions.CreateCardChargeParams;
import softixx.api.payment.openpay.OpenPayCard.OpenPayCardPoints;
import softixx.api.payment.openpay.OpenPayCard.OpenPayCardPoints.OpenPayCardPointsResponse;
import softixx.api.payment.openpay.OpenPayCard.OpenPayCardResponse;
import softixx.api.payment.openpay.OpenPayCard.OpenPayCardSecure;
import softixx.api.payment.openpay.OpenPayCharge.OpenPayChargeResponse;
import softixx.api.payment.openpay.OpenPayCustomer.OpenPayCustomerResponse;
import softixx.api.util.UValidator;

/**
 * Online payments through OpenPay <br>
 * Require {@link OpenpayAPI}
 * 
 * @author Maikel Guerra Ferrer
 * @since 1.2.0
 */
public abstract class OpenPayPayment {
	private static final Logger log = LoggerFactory.getLogger(OpenPayPayment.class);
	
	private OpenpayAPI openpay;
	private Boolean showLogs;

	/**
	 * Prints logs information if showLogs is true. By default, showLogs is false.
	 */
	protected void showLogs() {
		this.showLogs = true;
	}
	
	/**
	 * OpenPay configuration data initialization
	 *
	 * @since 1.2.0
	 * @param url - (String) OpenPay url
	 * @param apiKey - (String) OpenPay private key
	 * @param merchantId - (String) OpenPay merchantId
	 */
	protected void openPayConfig(final String url, final String apiKey, final String merchantId) {
		this.openpay = new OpenpayAPI(url, apiKey, merchantId);
		this.showLogs = false;
	}
	
	/**
	 * Creates a new customer on OpenPay
	 * 
	 * @param bean - {@link OpenPayCustomer}
	 * @return {@link OpenPayCustomerResponse} with the response information
	 * @see OpenPayCustomer
	 * @see OpenPayCustomerResponse
	 * @throws Exception
	 */
	protected OpenPayCustomerResponse createCustomer(final OpenPayCustomer bean) throws Exception {
		try {
			
			if(UValidator.isNotNull(bean)) {
				val customer = this.openpayCustomer(bean);
				val response = this.openpay.customers().create(customer);
				return this.openpayCustomerResponse(response);
			}
			
		} catch (Exception e) {
			if (this.showLogs) {
				e.printStackTrace();
				log.error("OpenPay createCustomer error > {}", e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Updates a customer on OpenPay
	 * 
	 * @param bean - {@link OpenPayCustomer}
	 * @return {@link OpenPayCustomerResponse} with the response information
	 * @see OpenPayCustomer
	 * @see OpenPayCustomerResponse
	 * @throws Exception
	 */
	protected OpenPayCustomerResponse updateCustomer(final OpenPayCustomer bean) throws Exception {
		try {
			
			if(bean != null) {
				val customer = this.openpayCustomer(bean);
				val response = this.openpay.customers().update(customer);
				return this.openpayCustomerResponse(response);
			}
			
		} catch (Exception e) {
			if (this.showLogs) {
				e.printStackTrace();
				log.error("OpenPay updateCustomer error > {}", e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Deletes a customer on OpenPay
	 * 
	 * @param bean - {@link OpenPayCustomer}
	 * @return True if customer was deleted
	 * @see OpenPayCustomer
	 * @throws Exception
	 */
	protected Boolean deleteCustomer(final OpenPayCustomer bean) throws Exception {
		try {
			
			if(bean != null) {
				this.openpay.customers().delete(bean.getOpenpayId());
				return true;
			}
			
		} catch (Exception e) {
			if (this.showLogs) {
				e.printStackTrace();
				log.error("OpenPay deleteCustomer error > {}", e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Creates a new card on OpenPay
	 * 
	 * @param bean - {@link OpenPayCard}
	 * @return {@link OpenPayCardResponse} with the response information
	 * @see OpenPayCard
	 * @see OpenPayCardResponse
	 * @throws Exception
	 */
	protected OpenPayCardResponse createCard(final OpenPayCard bean) throws Exception {
		try {
			
			if(bean != null) {
				val card = new Card();
				card.holderName(bean.getHolderName());
				card.cardNumber(bean.getCardNumber());
				card.cvv2(bean.getCvv2());
				card.expirationMonth(bean.getExpirationMonth());
				card.expirationYear(bean.getExpirationYear());
				
				val response = this.openpay.cards().create(bean.getOpenpayId(), card);
				return this.openpayCardResponse(response);
			}
			
		} catch (Exception e) {
			if (this.showLogs) {
				e.printStackTrace();
				log.error("OpenPay createCard error > {}", e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Creates a new card on OpenPay
	 * 
	 * @param bean - {@link OpenPayCardSecure}
	 * @return {@link OpenPayCardResponse} with the response information
	 * @see OpenPayCardSecure
	 * @see OpenPayCardResponse
	 * @throws Exception
	 */
	protected OpenPayCardResponse createCard(final OpenPayCardSecure bean) throws Exception {
		try {
			
			if(bean != null) {
				val card = new Card();
				card.tokenId(bean.getTokenId());
				card.setDeviceSessionId(bean.getDeviceSessionId());
				
				val response = this.openpay.cards().create(bean.getOpenpayId(), card);
				return this.openpayCardResponse(response);
			}
			
		} catch (Exception e) {
			if (this.showLogs) {
				e.printStackTrace();
				log.error("OpenPay createCard error > {}", e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Deletes a card on OpenPay
	 * 
	 * @param bean - {@link OpenPayCard}
	 * @return True if card was deleted
	 * @see OpenPayCard
	 * @throws Exception
	 */
	protected Boolean deleteCard(final OpenPayCard bean) throws Exception {
		try {
			
			if(bean != null) {
				this.openpay.cards().delete(bean.getOpenpayId(), bean.getCardId());
				return true;
			}
			
		} catch (Exception e) {
			if (this.showLogs) {
				e.printStackTrace();
				log.error("OpenPay deleteCard error > {}", e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Returns the balance of points on the card. Only applicable to Santander, Scotiabank and Bancomer points
	 * 
	 * @param cardPoints - {@link OpenPayCardPoints}
	 * @return {@link OpenPayCardPointsResponse}
	 * @see OpenPayCardPoints
	 * @see OpenPayCardPointsResponse
	 * @throws Exception
	 */
	protected OpenPayCardPointsResponse checkCardPoints(final OpenPayCardPoints cardPoints) throws Exception {
		try {
			
			if(cardPoints != null) {
				val response = this.openpay.cards().points(cardPoints.getCustomerId(), cardPoints.getCardId());
				return OpenPayCardPointsResponse.toBean(response);
			}
			
		} catch (Exception e) {
			if (this.showLogs) {
				e.printStackTrace();
				log.error("OpenPay checkCardPoints error > {}", e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Creates a charge on OpenPay
	 * 
	 * @param bean - {@link OpenPayCharge}
	 * @return {@link OpenPayChargeResponse} with te response information
	 * @see OpenPayCharge
	 * @see OpenPayChargeResponse
	 * @throws Exception
	 */
	protected OpenPayChargeResponse charge(final OpenPayCharge bean) throws Exception {
		try {
			
			OpenPayChargeResponse response = null;
			if(bean != null) {
				val chargeParams = new CreateCardChargeParams();
				chargeParams.cardId(bean.getTokenId());
				chargeParams.amount(bean.getAmount());
				chargeParams.currency(bean.getCurrency().toUpperCase());
				chargeParams.description(bean.getDescription());
				chargeParams.deviceSessionId(bean.getDeviceSessionId());
				chargeParams.customer(this.openpayCustomer(bean.getCustomer()));
				chargeParams.sendEmail(bean.getSendEmail());
				
				var orderId = bean.getOrderId();
				if(UValidator.isNotEmpty(orderId)) {
					if(bean.getAutogenerateOrderId()) {
						orderId = OpenPayCharge.orderId();
					}
					chargeParams.orderId(bean.getOrderId());
				}
				
				//var cardPointsType
				//chargeParams.useCardPoints(null)
				
				val charge = this.openpay.charges().createCharge(chargeParams);
				response = OpenPayChargeResponse.toBean(charge);
				
				if(charge.getStatus().equalsIgnoreCase("completed") && charge.getErrorMessage() == null) {
					if(bean.getCreateCustomer()) {
						try {
							
							val customer = this.createCustomer(bean.getCustomer());
							if(customer != null) {
								response.setCustomerCreated(true);
								if(bean.getCreateCard()) {
									val cardData = OpenPayCardSecure
													.builder()
													.openpayId(customer.getId())
													.deviceSessionId(bean.getDeviceSessionId())
													.tokenId(bean.getTokenId())
													.build();
									val card = this.createCard(cardData);
									if(card != null) {
										response.setCardCreated(true);
									}
								}
							}
							
						} catch (Exception e) {
							if (this.showLogs) {
								e.printStackTrace();
								log.error("OpenPay charge [create customer or card after charge] error > {}", e.getMessage());
							}
						}
					}
				}
				return response;
			}
			
		} catch (Exception e) {
			if (this.showLogs) {
				e.printStackTrace();
				log.error("OpenPay deleteCard error > {}", e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return null;
	}
	
	private Customer openpayCustomer(final OpenPayCustomer bean) {
		if(bean != null) {
			val customer = new Customer();
			customer.externalId(bean.getExternalId());
			customer.name(bean.getName());
			customer.lastName(bean.getLastName());
			customer.email(bean.getEmail());
			customer.phoneNumber(bean.getPhoneNumber());
			customer.requiresAccount(bean.getRequiresAccount());
			return customer;
		}
		return null;
	}
	
	private OpenPayCustomerResponse openpayCustomerResponse(final Customer customer) throws Exception {
		if(customer != null) {
			return OpenPayCustomerResponse.toBean(customer);
		}
		return null;
	}
	
	private OpenPayCardResponse openpayCardResponse(final Card card) throws Exception {
		if(card != null) {
			return OpenPayCardResponse.toBean(card);
		}
		return null;
	}
	
	/**
	 * OpenPay configuration data <br>
	 * 
	 * {@value} <b>publicKey</b> - (String) OpenPay public key <br>
	 * {@value} <b>privateKey</b> - (String) OpenPay private key <br>
	 * {@value} <b>version</b> - (String) API version <br>
	 * {@value} <b>liveMode</b> - (Boolean) If true, means it is a production payment
	 * @since 1.2.0
	 */
	@Data
	@Builder
	protected static class OpenPayConfiguration {
		private String url;
		private String apiKey;
		private String merchantId;

		/**
		 * A static instance of OpenPayConfiguration
		 * 
		 * @param url - (String) OpenPay public key
		 * @param apiKey - (String) OpenPay private key
		 * @param merchantId - (String) OpenPay merchantId
		 * @return A new instance of OpenPayConfiguration
		 */
		public static OpenPayConfiguration instance(final String url, final String apiKey, final String merchantId) {
			return OpenPayConfiguration
					.builder()
					.url(url)
					.apiKey(apiKey)
					.merchantId(merchantId)
					.build();
		}
	}
}
