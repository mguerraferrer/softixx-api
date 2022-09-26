package softixx.api.payment.paypal;

public abstract class PayPalConfig {
	
	protected PayPalSettings payPalSettings;
	
	protected void init(PayPalSettings payPalSettings) {
		this.payPalSettings = payPalSettings;
	}
	
}