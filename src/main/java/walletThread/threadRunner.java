package walletThread;

public class threadRunner {
	public static void main(String[] args) {
		Account account = new Account();
		final double AMOUNT = 100;
		final int REPETITIONS = 10;
		final int THREADS = 10;

		for (int i = 1; i <= THREADS; i++) {
			DepositRunnable d = new DepositRunnable(account, AMOUNT, REPETITIONS);
			WithdrawRunnable w = new WithdrawRunnable(account, AMOUNT, REPETITIONS);

			Thread dt = new Thread(d);
			Thread wt = new Thread(w);

			dt.start();
			wt.start();
		}
	}

}
