package softixx.api.payment.stripe;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is used to parse the payment intent response from Stripe
 *
 */
@Data
@NoArgsConstructor
public class StripePaymentIntentResponse {
	private String id;
    private String object;
    private long amount;
    @JsonProperty("canceled_at")
    private String canceledAt;
    @JsonProperty("cancellation_reason")
    private String cancellationReason;
    @JsonProperty("capture_method")
    private String captureMethod;
    @JsonProperty("client_secret")
    private String clientSecret;
    @JsonProperty("confirmation_method")
    private String confirmationMethod;
    private long created;
    private String currency;
    private String description;
    @JsonProperty("last_payment_error")
    private long lastPaymentError;
    private boolean livemode;
    @JsonProperty("next_action")
    private String nextAction;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("payment_method_types")
    private List<String> paymentMethodTypes;
    @JsonProperty("receipt_email")
    private String receiptEmail;
    @JsonProperty("setup_future_usage")
    private Object setupFutureUsage;
    private Object shipping;
    private Object source;
    private String status;
    
	/*
	 * Example of response
	 * {
		  "id": "pi_1IRiHBJ58gzkz2FuunAZu20g",
		  "object": "payment_intent",
		  "amount": 1000,
		  "canceled_at": null,
		  "cancellation_reason": null,
		  "capture_method": "automatic",
		  "client_secret": "pi_1IRiHBJ58gzkz2FuunAZu20g_secret_ZaDk8ZCiLZVIQ8e3qv3KSqz71",
		  "confirmation_method": "automatic",
		  "created": 1614968485,
		  "currency": "usd",
		  "description": "Created by stripe.com/docs demo",
		  "last_payment_error": null,
		  "livemode": false,
		  "next_action": null,
		  "payment_method": "pm_1IRiHBJ58gzkz2FuxiM3f5jC",
		  "payment_method_types": [
		    "card"
		  ],
		  "receipt_email": null,
		  "setup_future_usage": null,
		  "shipping": null,
		  "source": null,
		  "status": "succeeded"
		}
	 * */
}
