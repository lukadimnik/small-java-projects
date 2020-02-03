package JavaBankApplication;

// ************************************************************************
// Account.java	  Template created on15.9.2016
// - The class for Account objects
// ************************************************************************
public class Account {
	// Fields
	private String accountNumber;
	private double balance;

	// Constructor
	public Account(String accountNumber) {
		this.accountNumber = accountNumber;
		this.balance = 0;
	}

	// Methods
	public String getAccountNumber() {
		return accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double amount) {
		this.balance = amount;
	}

	public boolean withdrawMoney(double amount) {

		if (this.balance < amount) {
			return false;
		} else {
			this.balance = balance - amount;
			return true;
		}

	}
}
// End