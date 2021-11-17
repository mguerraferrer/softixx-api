package softixx.api.payment.stripe;

import org.springframework.stereotype.Service;

import softixx.api.exception.StripePaymentException;

/**
 * Service that manages the Stripe's operations
 *
 * @since 1.2.0
 * @see StripePayment
 * @author Maikel Guerra Ferrer
 */
@Service
public class StripePaymentService extends StripePayment {
	
	public StripePaymentService() {
		super();
	}

	@Override
	public void stripeConfig(final String apiPublicKey, final String apiSecretKey) {
		super.stripeConfig(apiPublicKey, apiSecretKey);
	}
	
	@Override
	public String paymentIntent(final StripeData dataBean) throws StripePaymentException {
		return super.paymentIntent(dataBean);
	}
	
	@Override
	public StripeChargeResponse charge(StripeData data) {
		return super.charge(data);
	}

	@Override
	public StripePaymentIntentResponse paymentResponse(final String json) throws StripePaymentException {
		return super.paymentResponse(json);
	}

	@Override
	public void showLogs() {
		super.showLogs();
	}
}