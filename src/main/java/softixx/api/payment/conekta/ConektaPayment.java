package softixx.api.payment.conekta;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.conekta.Charge;
import io.conekta.Conekta;
import io.conekta.ErrorList;
import io.conekta.Order;
import io.conekta.PaymentMethod;
import lombok.Builder;
import lombok.Data;
import lombok.val;
import softixx.api.util.UCalculation;
import softixx.api.util.UCrypto;
import softixx.api.util.UPayment;
import softixx.api.util.UValidator;

/**
 * Online payments through Conekta <br>
 * Require {@link Conekta} API
 * 
 * @author Maikel Guerra Ferrer
 * @since 1.2.0
 */
public abstract class ConektaPayment {
	private static final Logger log = LoggerFactory.getLogger(ConektaPayment.class);

	private ConektaConfiguration conektaConfiguration;
	private Boolean showLogs;

	/**
	 * Prints logs information if showLogs is true. By default, showLogs is false.
	 */
	protected void showLogs() {
		this.showLogs = true;
	}

	/**
	 * Conekta configuration data initialization
	 *
	 * @since 1.2.0
	 * @param conektaConfiguration - {@link ConektaConfiguration}
	 * @see ConektaConfiguration
	 */
	protected void conektaConfig(final String publicKey, final String privateKey, final String version, final Boolean liveMode) {
		this.conektaConfiguration = ConektaConfiguration.instance(publicKey, privateKey, version, liveMode);
		this.showLogs = false;
	}

	/**
	 * Payment with Conekta
	 * 
	 * @since 1.2.0
	 * @param dataBean - {@link ConektaConfiguration}
	 * @return A {@link Charge} object with the information of the payment
	 * @see ConektaData
	 */
	protected Charge payment(final ConektaData dataBean) throws Exception {
		Charge orderInfo = null;
		try {

			if (dataBean == null) {
				throw new Exception("Error: conektaDataBean is null!");
			}

			if (UValidator.isNotNull(dataBean.getToken()) && UValidator.isNotNull(dataBean.getHolderName())) {
				//##### Customer
				val phone = (UValidator.isNotNull(dataBean.getHolderPhone())) 
						? dataBean.getHolderPhone()
						: UPayment.PAYMENT_DEFAULT_PHONE;
				val customerInfo = ConektaCustomer.builder()
									.name(dataBean.getHolderName())
									.email(dataBean.getHolderEmail())
									.phone(phone)
									.build();
				

				//##### Product
				val item = ConektaItem
							.builder()
							.name(dataBean.getItemDescription())
							.description(dataBean.getPaymentDescription())
							.unit_price(UCalculation.penniesInt(dataBean.getTotal()))
							.quantity(dataBean.getQuantity())
							.build();
				val items = Arrays.asList(item);

				//##### Order
				val orderBean = ConektaOrder
									.builder()
									.id(UCrypto.salt())
									.currency(dataBean.getCurrency())
									.line_items(items)
									.customer_info(customerInfo)
									.build();

				//##### Payment
				val paymentMethod = ConektaPaymentMethod
										.builder()
										.token_id(dataBean.getToken())
										.build();

				//##### Charge
				val chargeBean = ConektaCharge
									.builder()
									.id(UCrypto.salt())
									.amount(UCalculation.penniesInt(dataBean.getTotal()))
									.currency(dataBean.getCurrency())
									.livemode(dataBean.isLiveMode())
									.payment_method(paymentMethod)
									.build();
				val charges = Arrays.asList(chargeBean);
				orderBean.setCharges(charges);

				//##### Order (JSONObject)
				val orderJSON = ConektaOrder.orderJSON(orderBean);

				//##### initializating Conekta
				val apiKey = conektaConfiguration.getPrivateKey();
				Conekta.setApiKey(apiKey);

				Order completeOrder = Order.create(orderJSON);
				orderInfo = (Charge) completeOrder.charges.get(0);

				if (this.showLogs) {
					log.info("Conekta payment success");
					log.info("Conekta payment orderInfo > {}", orderInfo.toString());

					PaymentMethod payMethod = orderInfo.payment_method;
					log.info("Conekta payment paymentMethod > {}", payMethod.toString());

					val cardBrand = (String) payMethod.getVal("brand");
					log.info("Conekta payment cardBrand > {}", cardBrand.toString());

					val cardLast4 = (String) payMethod.getVal("last4");
					log.info("Conekta payment cardLast4 > {}", cardLast4.toString());

					val paymentStatus = (String) orderInfo.getVal("status");
					log.info("Conekta payment paymentStatus > {}", paymentStatus.toString());
				}

				System.out.println(completeOrder.charges.get(0));
			} else {
				throw new Exception("Error: holderName and token can't be null!");
			}

		} catch (ErrorList e) {
			if (this.showLogs) {
				System.out.println(e.details.get(0).message);
				log.error("Conekta payment error > {}", e.details.get(0).message);
			}
			throw new Exception(e.details.get(0).message);
		} catch (Exception e) {
			if (this.showLogs) {
				e.printStackTrace();
				log.error("Conekta payment error > {}", e.getMessage());
			}
			throw new Exception(e.getMessage());
		} finally {
			Conekta.setApiKey(null);
		}
		return orderInfo;
	}

	/**
	 * Conekta configuration data <br>
	 * 
	 * {@value} <b>publicKey</b> - (String) Conekta public key <br>
	 * {@value} <b>privateKey</b> - (String) Conekta private key <br>
	 * {@value} <b>version</b> - (String) API version <br>
	 * {@value} <b>liveMode</b> - (Boolean) If true, means it is a production payment
	 * @since 1.2.0
	 */
	@Data
	@Builder
	protected static class ConektaConfiguration {
		private String publicKey;
		private String privateKey;
		private String version;
		private boolean liveMode;

		/**
		 * A static instance of ConektaConfiguration
		 * 
		 * @param publicKey - (String) Conekta public key
		 * @param privateKey - (String) Conekta private key
		 * @param version - (String) API version
		 * @param liveMode - (Boolean) If true, means it is a production payment
		 * @return A new instance of ConektaConfiguration
		 */
		public static ConektaConfiguration instance(final String publicKey, final String privateKey,
				final String version, final Boolean liveMode) {
			return ConektaConfiguration
					.builder()
					.publicKey(publicKey)
					.privateKey(privateKey)
					.version(version)
					.liveMode(liveMode)
					.build();
		}
	}
}
