package softixx.api.payment.openpay;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import mx.openpay.client.Address;
import mx.openpay.client.Customer;
import softixx.api.payment.openpay.OpenPayCustomer.OpenPayCustomerResponse.OpenPayCustomerAddress;

/**
 * Contains all the attributes that you need for create, update or delete a
 * customer on OpenPay <br><br>
 * 
 * <b>Attributes</b> <br>
 * <b>openpayId</b> (String) - Represents the id of the customer <br>
 * <b>externalId</b> (String) - Unique external identifier for the customer
 * assigned by the merchant <b><i>*Optional, length = 100</i></b> <br>
 * <b>name</b> (String) - Client name(s) <b><i>*Required, length = 100</i></b>
 * <br>
 * <b>lastName</b> (String) - Client surname <b><i>*Optional, length =
 * 100</i></b> <br>
 * <b>email</b> (String) - Customer email account <b><i>*Required, length =
 * 100</i></b> <br>
 * <b>phoneNumber</b> (String) - Customer phone number <b><i>*Optional, length =
 * 100</i></b> <br>
 * <b>requiresAccount</b> (Boolean) - Send with value true if it requires the
 * customer to create an account to manage the balance <b><i>*Optional, default
 * false</i></b> <br>
 * <b>address</b> ({@link OpenPayCustomerAddress}) - Customer address. It is
 * commonly used as a shipping or billing address <b><i>*Optional</i></b>
 * 
 * @since 1.2.0
 * @see OpenPayCustomerAddress
 * @author Maikel Guerra Ferrer
 *
 */
@Data
@Builder
public class OpenPayCustomer {
	private String openpayId;
	private String externalId;
	private String name;
	private String lastName;
	private String email;
	private String phoneNumber;
	@Default
	private Boolean requiresAccount = false;
	private OpenPayCustomerAddress address;

	/**
	 * Retrieves the data after create or update a customer on OpenPay <br><br>
	 * 
	 * <b>Attributes</b> <br>
	 * <b>id</b> (String) - Unique identifier of the customer <br>
	 * <b>externalId</b> (String) - Unique external identifier for the customer
	 * assigned by the merchant <br>
	 * <b>name</b> (String) - Customer name(s) <br>
	 * <b>lastName</b> (String) - Customer surname <br>
	 * <b>email</b> (String) - Customer email account <br>
	 * <b>phoneNumber</b> (String) - Customer phone number <br>
	 * <b>requiresAccount</b> (Boolean) - Send with value true if it requires the
	 * customer to create an account to manage the balance <br>
	 * <b>address</b> ({@link OpenPayCustomerAddress}) - Customer address. It is
	 * commonly used as a shipping or billing address <br>
	 * <b>creationDate</b> (String) - Date and time the card was created in ISO 8601
	 * format <br>
	 * <b>status</b> (String) - Client account status. Can be active or deleted. If
	 * the account is in deleted status, no transaction is allowed <br>
	 * <b>balance</b> (BigDecimal) - Account balance to two decimal places <br>
	 * <b>clabe</b> (String) - CLABE account associated with which the customer can
	 * receive funds by making a transfer from any bank in Mexico <br>
	 * 
	 * @since 1.2.0
	 * @see OpenPayCustomerAddress
	 * @author Maikel Guerra Ferrer
	 */
	@Data
	@Builder
	public static class OpenPayCustomerResponse {
		private String id;
		private String externalId;
		private String name;
		private String lastName;
		private String email;
		private String phoneNumber;
		private OpenPayCustomerAddress address;
		private Boolean requiresAccount;
		private String status;
		private BigDecimal balance;
		private Date creationDate;
		private String clabe;

		/**
		 * Converts a {@link Customer} object to {@link OpenPayCustomerResponse} object
		 * 
		 * @param customer - {@link Customer}
		 * @return An OpenPayCustomerResponse object
		 * @see Customer
		 * @see OpenPayCustomerResponse
		 */
		public static OpenPayCustomerResponse toBean(Customer customer) {
			if (customer != null) {
				return OpenPayCustomerResponse.builder().id(customer.getId()).name(customer.getName())
						.email(customer.getEmail()).lastName(customer.getLastName())
						.phoneNumber(customer.getPhoneNumber())
						.address(OpenPayCustomerAddress.toBean(customer.getAddress())).status(customer.getStatus())
						.balance(customer.getBalance()).externalId(customer.getExternalId())
						.requiresAccount(customer.getRequiresAccount()).creationDate(customer.getCreationDate())
						.clabe(customer.getClabe()).build();
			}
			return null;
		}

		/**
		 * Contains all the attributes that you need for create or update a customer address on OpenPay <br><br>
		 * 
		 * <b>Attributes</b> <br>
		 * <b>line1</b> (String) - Cardholder's first address line. Commonly used to indicate the street and exterior and interior number <b><i>*Required</i></b> <br>
		 * <b>line2</b> (String) - Second line of the cardholder's address. Commonly used to indicate condominium, suite or delegation <b><i>*Optional</i></b> <br>
		 * <b>line3</b> (String) - Third line of the cardholder's address. Commonly used to indicate the colony <b><i>*Optional</i></b> <br>
		 * <b>postalCode</b> (String) - Cardholder Zip Code <b><i>*Required</i></b> <br>
		 * <b>state</b> (String) - Cardholder state <b><i>*Required</i></b> <br>
		 * <b>city</b> (String) - Cardholder city <b><i>*Required</i></b> <br>
		 * <b>countryCode</b> (String) - Two-character cardholder country code in ISO_3166-1 format <b><i>*Required</i></b>
		 * @author Maikel Guerra Ferrer
		 */
		@Data
		@Builder
		public static class OpenPayCustomerAddress {
			private String line1;
			private String line2;
			private String line3;
			private String postalCode;
			private String state;
			private String city;
			private String countryCode;

			/**
			 * Converts a {@link Address} object to {@link OpenPayCustomerAddress} object
			 * 
			 * @param address - {@link Address}
			 * @return An OpenPayCustomerAddress object
			 * @see Address
			 * @see OpenPayCustomerAddress
			 */
			protected static OpenPayCustomerAddress toBean(Address address) {
				if (address != null) {
					return OpenPayCustomerAddress.builder().postalCode(address.getPostalCode())
							.line1(address.getLine1()).line2(address.getLine2()).line3(address.getLine3())
							.city(address.getCity()).state(address.getState()).countryCode(address.getCountryCode())
							.build();
				}
				return null;
			}
		}
	}
}
