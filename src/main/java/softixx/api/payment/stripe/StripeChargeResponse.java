package softixx.api.payment.stripe;

import java.util.Map;

import com.stripe.model.Charge;

import lombok.Builder;
import lombok.Data;

/**
 * Retrieves the data after payment response from Stripe
 *
 */
@Data
@Builder
public class StripeChargeResponse {
	private Long amount;
	private Long amountCaptured;
	private Long amountRefunded;
	private String application;
	private String applicationFee;
	private String applicationFeeAmount;
	private String authorizationCode;
	private String balanceTransaction;
	private String calculatedStatementDescriptor;
	private boolean captured;
	private Long created;
	private String currency;
	private String customer;
	private String description;
	private String destination;
	private String dispute;
	private boolean disputed;
	private String failureCode;
	private String failureMessage;
	private String id;
	private String invoice;
	private boolean livemode;
	private Map<String, String> metadata;
	private String object;
    private String onBehalfOf;
    private String order;
    private boolean paid;
    private String paymentIntent;
    private String paymentMethod;
    private String receiptEmail;
    private String receiptNumber;
    private String receiptUrl;
    private boolean refunded;
    private String review;
    private String sourceTransfer;
    private String statementDescriptor;
    private String statementDescriptorSuffix;
    private String status;
    private String transfer;
    private String transferGroup;
    
    /**
     * Maps a {@link Charge} object to {@link StripeChargeResponse} object
     * 
     * @param charge - {@link Charge}
     * @return {@link StripeChargeResponse}
     */
    protected static StripeChargeResponse mapper(final Charge charge) {
    	try {
			
    		if(charge != null) {
    			return StripeChargeResponse
    					.builder()
    					.amount(charge.getAmount())
    					.amountCaptured(charge.getAmountCaptured())
    					.amountRefunded(charge.getAmountRefunded())
    					.application(charge.getApplication())
    					.applicationFee(charge.getApplicationFee())
    					.applicationFeeAmount(charge.getApplicationFee())
    					.authorizationCode(charge.getAuthorizationCode())
    					.balanceTransaction(charge.getBalanceTransaction())
    					.calculatedStatementDescriptor(charge.getCalculatedStatementDescriptor())
    					.captured(charge.getCaptured())
    					.created(charge.getCreated())
    					.currency(charge.getCurrency())
    					.customer(charge.getCustomer())
    					.description(charge.getDescription())
    					.destination(charge.getDestination())
    					.dispute(charge.getDispute())
    					.disputed(charge.getDisputed())
    					.failureCode(charge.getFailureCode())
    					.failureMessage(charge.getFailureMessage())
    					.id(charge.getId())
    					.invoice(charge.getInvoice())
    					.livemode(charge.getLivemode())
    					.metadata(charge.getMetadata())
    					.object(charge.getObject())
    					.onBehalfOf(charge.getOnBehalfOf())
    					.order(charge.getOrder())
    					.paid(charge.getPaid())
    					.paymentIntent(charge.getPaymentIntent())
    					.paymentMethod(charge.getPaymentMethod())
    					.receiptEmail(charge.getReceiptEmail())
    					.receiptNumber(charge.getReceiptNumber())
    					.receiptUrl(charge.getReceiptUrl())
    					.refunded(charge.getRefunded())
    					.review(charge.getReview())
    					.sourceTransfer(charge.getSourceTransfer())
    					.statementDescriptor(charge.getStatementDescriptor())
    					.statementDescriptorSuffix(charge.getStatementDescriptorSuffix())
    					.status(charge.getStatus())
    					.transfer(charge.getTransfer())
    					.transferGroup(charge.getTransferGroup())
    					.build();
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
}
