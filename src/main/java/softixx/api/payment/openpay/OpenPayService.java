package softixx.api.payment.openpay;

import org.springframework.stereotype.Service;

import softixx.api.payment.openpay.OpenPayCard.OpenPayCardResponse;
import softixx.api.payment.openpay.OpenPayCard.OpenPayCardSecure;
import softixx.api.payment.openpay.OpenPayCharge.OpenPayChargeResponse;
import softixx.api.payment.openpay.OpenPayCustomer.OpenPayCustomerResponse;

/**
 * Service that manages the OpenPay's operations
 *
 * @since 1.2.0
 * @see OpenPayPayment
 * @author Maikel Guerra Ferrer
 */
@Service
public class OpenPayService extends OpenPayPayment {

	@Override
	public void openPayConfig(String url, String apiKey, String merchantId) {
		super.openPayConfig(url, apiKey, merchantId);
	}

	@Override
	public OpenPayCustomerResponse createCustomer(OpenPayCustomer bean) throws Exception {
		return super.createCustomer(bean);
	}

	@Override
	public OpenPayCustomerResponse updateCustomer(OpenPayCustomer bean) throws Exception {
		return super.updateCustomer(bean);
	}

	@Override
	public Boolean deleteCustomer(OpenPayCustomer bean) throws Exception {
		return super.deleteCustomer(bean);
	}

	@Override
	public OpenPayCardResponse createCard(OpenPayCard bean) throws Exception {
		return super.createCard(bean);
	}

	@Override
	public OpenPayCardResponse createCard(OpenPayCardSecure bean) throws Exception {
		return super.createCard(bean);
	}

	@Override
	public Boolean deleteCard(OpenPayCard bean) throws Exception {
		return super.deleteCard(bean);
	}

	@Override
	public OpenPayChargeResponse charge(OpenPayCharge bean) throws Exception {
		return super.charge(bean);
	}
	
	@Override
	public void showLogs() {
		super.showLogs();
	}

}
