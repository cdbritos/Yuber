package my.application;

import java.math.BigDecimal;

import myjpa.CustomerAcct;

public class OutBean {
	
	String status;
	CustomerAcct customerAccts[] = new CustomerAcct[8];
	String customerName;
	String creditAmount;
	BigDecimal balance;
	double customerNumber;

	

	public double getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(double customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CustomerAcct[] getCustomerAccts() {
		return customerAccts;
	}

	public void setCustomerAccts(CustomerAcct[] customerAccts) {
		this.customerAccts = customerAccts;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
