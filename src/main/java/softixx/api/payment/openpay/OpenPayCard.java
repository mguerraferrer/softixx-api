package softixx.api.payment.openpay;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
import mx.openpay.client.Card;
import mx.openpay.client.PointsBalance;
import softixx.api.payment.openpay.OpenPayCustomer.OpenPayCustomerResponse.OpenPayCustomerAddress;

/**
 * Contains all the attributes that you need for create, update or delete a card on OpenPay. This is less secure than {@link OpenPayCardSecure} <br><br>
 * 
 * {@value <b>cardId</b> (String)} - Represents the id of the card generated by OpenPay. <b><i>*Fill this value only for updates or deletions</i></b> <br>
 * {@value <b>openpayId</b> (String)} - Represents the id of the customer that will be linked to the card <b><i>*Required</i></b> <br>
 * {@value <b>holderName</b> (String)} - Card Holder name <b><i>*Required</i></b> <br>
 * {@value <b>cardNumber</b> (String)} - Card number <b><i>*Required</i></b> <br>
 * {@value <b>cvv2</b> (String)} - Security code <b><i>*Required</i></b> <br>
 * {@value <b>expirationMonth</b> (Integer)} - Card expiration month <b><i>*Required</i></b> <br>
 * {@value <b>expirationYear</b> (Integer)} - Card expiration year <b><i>*Required</i></b> <br>
 * @since 1.2.0
 * @see OpenPayCardSecure
 * @author Maikel Guerra Ferrer
 *
 */
@Data
@Builder
public class OpenPayCard {
	private String cardId;
	private String openpayId;
	private String holderName;
	private String cardNumber;
	private String cvv2;
	private Integer expirationMonth;
	private Integer expirationYear;
	
	/**
	 * Contains all the attributes that you need for create a card on OpenPay. This is more secure than {@link OpenPayCard} <br><br>
	 * {@value <b>openpayId</b> (String)} - Represents the id of the customer that will be linked to the card <b><i>*Required</i></b> <br>
	 * {@value <b>tokenId</b> (String)} - Token previously generated by Openpay <b><i>*Required</i></b> <br>
	 * {@value <b>deviceSessionId</b> (String)} - Another token previously generated by Openpay <b><i>*Required</i></b>
	 * @since 1.2.0
	 * @see OpenPayCard
	 * @author Maikel Guerra Ferrer
	 *
	 */
	@Data
	@Builder
	public static class OpenPayCardSecure {
		private String openpayId;
		private String tokenId;
		private String deviceSessionId;
	}
	
	/**
	 * Retrieves the data after create or update a card on OpenPay <br><br>
	 * 
	 * {@value <b>id</b> (String)} - Unique identifier of the card <br>
	 * {@value <b>cardNumber</b> (String)} - Card number, can be 16 or 19 digits <br>
	 * {@value <b>creationDate</b> (String)} - Date and time the card was created in ISO 8601 format <br>
	 * {@value <b>holderName</b> (String)} - Cardholder name <br>
	 * {@value <b>expirationMonth</b> (String)} - Expiration month as it appears on the card <br>
	 * {@value <b>expirationYear</b> (String)} - Expiration year as it appears on the card <br>
	 * {@value <b>address ({@link OpenPayCustomerAddress})</b>} - Cardholder billing address <br>
	 * {@value <b>allowsCharges</b> (Boolean)} - It allows to know if the card can be charged <br>
	 * {@value <b>allowsPayouts</b> (Boolean)} - It allows you to know if you can send payments to the card <br>
	 * {@value <b>brand</b> (String)} - Card brand: visa, mastercard, carnet or american express <br>
	 * {@value <b>type</b> (String)} - Card type: debit, credit, cash, etc <br>
	 * {@value <b>bankName</b> (String)} - Issuing bank name <br>
	 * {@value <b>bankCode</b> (String)} - Issuing bank code <br>
	 * {@value <b>pointsCard</b> (Boolean)} - Indicates if the card supports payment with points <br>
	 * {@value <b>pointsType</b> (String)} - Indicates the type of points on the card
	 * @since 1.2.0
	 * @see OpenPayCustomerAddress
	 * @author Maikel Guerra Ferrer
	 *
	 */
	@Data
	@Builder
	public static class OpenPayCardResponse {
		private String id;
		private String cardNumber;
	    private Date creationDate;
	    private String holderName;
	    private String expirationMonth;
	    private String expirationYear;
	    private OpenPayCustomerAddress address;
	    private Boolean allowsCharges;
	    private Boolean allowsPayouts;
	    private String brand;
	    private String type;
	    private String bankName;
	    private String bankCode;
	    private Boolean pointsCard;
	    private String pointsType;
	    /**
	     * Converts a {@link Card} object to {@link OpenPayCardResponse} object
	     * @param card - {@link Card}
	     * @return An OpenPayCardResponse object
	     * @see Card
	     * @see OpenPayCardResponse
	     */
	    protected static OpenPayCardResponse toBean(Card card) {
	    	if (card != null) {
		    	return OpenPayCardResponse
		    			.builder()
		    			.id(card.getId())
		    			.creationDate(card.getCreationDate())
		    			.bankName(card.getBankName())
		    			.bankCode(card.getBankCode())
		    			.allowsPayouts(card.getAllowsPayouts())
		    			.holderName(card.getHolderName())
		    			.expirationMonth(card.getExpirationMonth())
		    			.expirationYear(card.getExpirationYear())
		    			.address(OpenPayCustomerAddress.toBean(card.getAddress()))
		    			.cardNumber(card.getCardNumber())
		    			.brand(card.getBrand())
		    			.allowsCharges(card.getAllowsCharges())
		    			.type(card.getType())
		    			.pointsCard(card.isPointsCard())
		    			.pointsType(card.getPointsType())
		    			.build();
	    	}
	    	return null;
	    }
	}
	
	/**
	 * Use this class to verify card points in OpenPay <br><br>
	 * 
	 * @since 1.2.0
	 * @author Maikel Guerra Ferrer
	 */
	@Data
	@Builder
	public static class OpenPayCardPoints {
		public static final String NONE = "NONE";
		public static final String MIXED = "MIXED";
		public static final String ONLY_POINTS = "ONLY_POINTS";
		
		private String customerId;
		private String cardId;
		
		/**
		 * Retrieves the data after check card points in OpenPay <br><br>
		 * 
		 * {@value <b>pointsType</b> (String)} - Type of points accepted by the card (Santander, Scotiabank or Bancomer) <br>
		 * {@value <b>remainingPoints</b> (BigInteger)} - Amount of points remaining <br>
		 * {@value <b>remainingMxn</b> (BigDecimal)} - Remaining points balance in pesos <br>
		 * @since 1.2.0
		 * @author Maikel Guerra Ferrer
		 */
		@Data
		@Builder
		public static class OpenPayCardPointsResponse {
			private String pointsType;
			private BigInteger remainingPoints;
			private BigDecimal remainingMxn;
			
			/**
		     * Converts a {@link PointsBalance} object to {@link OpenPayCardPointsResponse} object
		     * @param points - {@link PointsBalance}
		     * @return An OpenPayCardPointsResponse object
		     * @see PointsBalance
		     * @see OpenPayCardPointsResponse
		     */
			protected static OpenPayCardPointsResponse toBean(PointsBalance points) {
				if (points != null) {
					return OpenPayCardPointsResponse
							.builder()
							.pointsType(points.getPointsType().name())
							.remainingPoints(points.getRemainingPoints())
							.remainingMxn(points.getRemainingMxn())
							.build();
				}
				return null;
			}
		}
	}
}
