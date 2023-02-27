package walletThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	private double balance;
	private Lock balanceChangeLock;
	private Condition sufficientFundsCondition;

	public Account()
	{
		balance = 0;
		balanceChangeLock = new ReentrantLock();
		sufficientFundsCondition = balanceChangeLock.newCondition();
	}

	public void deposit(double amount) {
		balanceChangeLock.lock();
		try {
			System.out.print("Depositing " + amount);
			double newBalance = balance + amount;
			System.out.println(", new balance is " + newBalance);
			balance = newBalance;
			sufficientFundsCondition.signalAll();
		} finally {
			balanceChangeLock.unlock();
		}
	}

	public void withdraw(double amount) throws InterruptedException {
		balanceChangeLock.lock();
		try {
			while (balance < amount) {
				sufficientFundsCondition.await();
			}
			System.out.print("Withdrawing " + amount);
			double newBalance = balance - amount;
			System.out.println(", new balance is " + newBalance);
			balance = newBalance;
		} finally {
			balanceChangeLock.unlock();
		}
	}

	public double getBalance() {
		return balance;
	}

}
