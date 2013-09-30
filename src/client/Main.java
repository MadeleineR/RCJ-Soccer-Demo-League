package client;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new Client();
                new Thread(client).start();
	}

}
