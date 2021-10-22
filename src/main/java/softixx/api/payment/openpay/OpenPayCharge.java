package softixx.api.payment.openpay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import mx.openpay.client.BankAccount;
import mx.openpay.client.Charge;
import mx.openpay.client.ExchangeRate;
import mx.openpay.client.GatewayResponse;
import mx.openpay.client.SimpleRefund;
import mx.openpay.client.TransactionFee;
import mx.openpay.client.enums.UseCardPointsType;
import softixx.api.payment.openpay.OpenPayCard.OpenPayCardResponse;
import softixx.api.util.UPayment;
import softixx.api.util.URandom;

/**
 * Contains all the attributes that you need for create a charge on OpenPay <br><br>
 * 
 * <b>Attributes</b> <br>
 * <b>tokenId</b> (String) - Id of the saved card or the id of the created token from which the funds will be withdrawn <b><i>*Required, length = 45</i></b> <br>
 * <b>deviceSessionId</b> (String) - Device identifier generated with the anti-fraud tool <b><i>*Required, length = 255</i></b> <br>
 * <b>customer</b> ({@link OpenPayCustomer}) - Information of the customer to whom the charge is made <b><i>*Required</i></b> <br>
 * <b>description</b> (String) - A description associated with the payment <b><i>*Required, length = 250</i></b> <br>
 * <b>amount</b> (BigDecimal) - Amount of the charge. Must be a quantity greater than zero, with up to two decimal digits <b><i>*Required</i></b> <br>
 * <b>orderId</b> (String) - Unique payment identifier. Must be unique among all transactions <b><i>*Optional</i></b> <br>
 * <b>autogenerateOrderId</b> (Boolean) - If true and orderId is null, an order ID is generated automatically <b><i>*Optional default false</i></b> <br>
 * <b>currency</b> (Boolean) - Type of charge currency. At the moment, only 2 types of currencies are supported: Mexican pesos (MXN) and US dollars (USD). <b><i>*Optional default MXN</i></b> <br>
 * @see OpenPayCustomer
 * @author Maikel Guerra Ferrer
 */
@Data
@Builder
public class OpenPayCharge {
	private String tokenId;
	private String deviceSessionId;
	private OpenPayCustomer customer;
	private String description;
	private BigDecimal amount;
	private String orderId;
	@Default
	private Boolean autogenerateOrderId = false;
	@Default
	private String currency = UPayment.PAYMENT_CURRENCY_MXN;
	@Default
	private Boolean useCardPoints = false;
	private UseCardPointsType useCardPointsType;
	@Default
	private Boolean sendEmail = false;
	@Default
	private Boolean createCustomer = false;
	@Default
	private Boolean createCard = false;
	
	public static String orderId() {
		return "oid-" + URandom.randomBigInteger();
	}
	
	@Data
	@Builder
	public static class OpenPayChargeResponse {
		private String id;
		private String customerId;
		private String orderId;
		private BigDecimal amount;
		private String currency;
		private Date creationDate;
	    private Date operationDate;
	    private Date dueDate;
	    private String status;
	    private String description;
	    private String transactionType;
	    private String operationType;
	    private String method;
	    private String errorMessage;
	    private Integer errorCode;
	    private String authorization;
	    private OpenPayCardResponse card;
	    private OpenPayBankAccount bankAccount;
	    private OpenPayTransactionFee fee;
	    private OpenPayExchangeRate exchangeRate;
	    private Map<String, String> metadata;
	    private List<OpenPaySimpleRefund> refunds;
	    private OpenPayGatewayResponse gateway;
	    @Default
	    private	boolean customerCreated = false;
	    @Default
	    private	boolean cardCreated = false;
	    
	    public static OpenPayChargeResponse toBean(Charge charge) {
	    	if(charge != null) {
	    		return OpenPayChargeResponse
	    				.builder()
	    				.id(charge.getId())
	    				.customerId(charge.getCustomerId())
	    				.orderId(charge.getOrderId())
	    				.amount(charge.getAmount())
	    				.creationDate(charge.getCreationDate())
	    				.operationDate(charge.getOperationDate())
	    				.status(charge.getStatus())
	    				.description(charge.getDescription())
	    				.transactionType(charge.getTransactionType())
	    				.operationType(charge.getOperationType())
	    				.method(charge.getMethod())
	    				.errorMessage(charge.getErrorMessage())
	    				.errorCode(charge.getErrorCode())
	    				.card(OpenPayCardResponse.toBean(charge.getCard()))
	    				.authorization(charge.getAuthorization())
	    				.bankAccount(OpenPayBankAccount.toBean(charge.getBankAccount()))
	    				.dueDate(charge.getDueDate())
	    				.fee(OpenPayTransactionFee.toBean(charge.getFee()))
	    				.exchangeRate(OpenPayExchangeRate.toBean(charge.getExchangeRate()))
	    				.metadata(charge.getMetadata())
	    				.currency(charge.getCurrency())
	    				.refunds(OpenPaySimpleRefund.to(charge.getRefunds()))
	    				.gateway(OpenPayGatewayResponse.to(charge.getGateway()))
	    				.build();
	    	}
	    	return null;
	    }
	    
	    @Data
	    @Builder
	    public static class OpenPayBankAccount {
	        private String id;
	        private String bankName;
	        private String holderName;
	        private String clabe;
	        private String alias;
	        private String bankCode;
	        private Date creationDate;
	        
	        public static OpenPayBankAccount toBean(BankAccount bankAccount) {
	        	if(bankAccount != null) {
	        		return OpenPayBankAccount
	        				.builder()
	        				.id(bankAccount.getId())
	        				.bankName(bankAccount.getBankName())
	        				.holderName(bankAccount.getHolderName())
	        				.clabe(bankAccount.getClabe())
	        				.alias(bankAccount.getAlias())
	        				.bankCode(bankAccount.getBankCode())
	        				.creationDate(bankAccount.getCreationDate())
	        				.build();
	        	}
	        	return null;
	        }
	    }
	    
	    @Data
	    @Builder
	    public static class OpenPayTransactionFee {
	    	private BigDecimal amount;
	    	private BigDecimal tax;
	    	
	    	public static OpenPayTransactionFee toBean(TransactionFee fee) {
	    		if(fee != null) {
		    		return OpenPayTransactionFee
		    				.builder()
		    				.amount(fee.getAmount())
		    				.tax(fee.getTax())
		    				.build();
	    		}
	    		return null;
	    	}
	    }
	    
	    @Data
	    @Builder
	    public static class OpenPayExchangeRate {
	    	private String fromCurrency;
	    	private String toCurrency;
	    	private Date date;
	    	private BigDecimal value;
	    	private BigDecimal rate;
	    	
	    	public static OpenPayExchangeRate toBean(ExchangeRate exchangeRate) {
	    		if(exchangeRate != null) {
	    			return OpenPayExchangeRate
	    					.builder()
	    					.fromCurrency(exchangeRate.getFromCurrency())
	    					.toCurrency(exchangeRate.getToCurrency())
	    					.date(exchangeRate.getDate())
	    					.value(exchangeRate.getValue())
	    					.rate(exchangeRate.getRate())
	    					.build();
	    		}
	    		return null;
	    	}
	    }
	    
	    @Data
	    @Builder
	    public static class OpenPaySimpleRefund {
	    	private String id;
	    	private Date operationDate;
	        private String authorization;
	        private BigDecimal amount;
	        private String status;
	        private Boolean conciliated;
	        private String description;
	        private OpenPayTransactionFee fee;
	        
	        public static OpenPaySimpleRefund toBean(SimpleRefund refund) {
	        	if(refund != null) {
		        	return OpenPaySimpleRefund
		        			.builder()
		        			.id(refund.getId())
		        			.operationDate(refund.getOperationDate())
		        			.authorization(refund.getAuthorization())
		        			.amount(refund.getAmount())
		        			.status(refund.getStatus())
		        			.conciliated(refund.getConciliated())
		        			.description(refund.getDescription())
		        			.fee(OpenPayTransactionFee.toBean(refund.getFee()))
		        			.build();
	        	}
	        	return null;
	        }
	        
	        public static List<OpenPaySimpleRefund> to(List<SimpleRefund> refunds) {
	        	if(refunds != null && !refunds.isEmpty()) {
	        		return refunds.stream()
	        					  .map(item -> toBean(item))
	        					  .filter(item -> item != null)
	        					  .collect(Collectors.toList());
	        	}
	        	return new ArrayList<>();
	        }
	    }
	    
	    @Data
	    @Builder
	    public static class OpenPayGatewayResponse {
	    	 private String affiliation;
	    	 
	    	 public static OpenPayGatewayResponse to(GatewayResponse gateway) {
	    		 if(gateway != null) {
	    			 return OpenPayGatewayResponse
	    					 .builder()
	    					 .affiliation(gateway.getAffiliation())
	    					 .build();
	    		 }
	    		 return null;
	    	 }
	    }
	}
}
