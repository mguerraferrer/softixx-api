package softixx.api.payment.conekta;

import org.springframework.stereotype.Service;

import io.conekta.Charge;

/**
 * Service that manages the Conekta's operations
 *
 * @since 1.2.0
 * @see ConektaPayment
 * @author Maikel Guerra Ferrer
 */
@Service
public class ConektaPaymentService extends ConektaPayment {
	@Override
	public void conektaConfig(final String publicKey, final String privateKey, final String version, final Boolean liveMode) {
		super.conektaConfig(publicKey, privateKey, version, liveMode);
	}
	
	
	@Override
	public Charge payment(final ConektaData dataBean) throws Exception {
		return super.payment(dataBean);
	}
	
	@Override
	public void showLogs() {
		super.showLogs();
	}
}