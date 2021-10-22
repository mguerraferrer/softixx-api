package softixx.api.payment.conekta;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import softixx.api.util.UPayment;

/**
 * Contains all the attributes that you need for a payment with Conekta <br><br>
 * 
 * Attributes <br>
 * {@value} <b>token</b> (String) - Token provides by Conekta API <br>
 * {@value} <b>liveMode</b> (Boolean) - If true, it indicates that it is a
 * production payment <br>
 * {@value} <b>holderName</b> (String) - Holder name (as it appears on the card)
 * <br>
 * {@value} <b>holderEmail</b> (String) - Holder email <br>
 * {@value} <b>holderPhone</b> (String) - Holder phone <br>
 * {@value} <b>itemDescription</b> (String) - Item name or description <br>
 * {@value} <b>paymentDescription</b> (String) - Payment description <br>
 * {@value} <b>quantity</b> (Integer) - Order quantity <br>
 * {@value} <b>currency</b> (String) - Currency (Has to be either MXN or USD -
 * {@link UPayment}) <br>
 * {@value} <b>total</b> (Double) - Order total
 * 
 * @see UPayment
 *
 */
@Data
@Builder
public class ConektaData {
	private String token;
	@Default
	private boolean liveMode = false;
	// ##### Customer
	private String holderName;
	private String holderEmail;
	private String holderPhone;
	// ##### Item
	private String itemDescription;
	private String paymentDescription;
	private Integer quantity;
	// ##### Payment
	private String currency;
	private Double total;
}
