package fiu.edu.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BookStoreServer {

	public static void main(String args[]) {

		int port = 9996;
		
		ServerSocket booksellerServer = null;
		Socket clientSocket = null;

		try {
			booksellerServer = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println(e);
		}

		while (true) {
			try {
				clientSocket = booksellerServer.accept();

				Thread agent = new Thread(new BookSellAgent(clientSocket));
				agent.run();
				
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
	}

}
