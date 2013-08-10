package fiu.edu.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import fiu.edu.server.BookOrder;
import fiu.edu.util.Util;

public class BookStoreClient implements Runnable{

	
	private String server;
	private int port;
	private String bookName;
	
	
	public BookStoreClient(String server, int port, String bookName) {
		// TODO Auto-generated constructor stub
		this.server = server;
		this.port = port;
		this.bookName = bookName;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		orderBook(server, port, bookName);
	}
	
	
	public boolean orderBook(String server, int port, String bookName){
		
		Socket clientSocket = null;
		DataOutputStream os = null;
		DataInputStream is = null;

		try {
			clientSocket = new Socket(server, port);
			os = new DataOutputStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host:" + server);
			return false;
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + server);
			return false;
		}

		if (clientSocket != null && os != null && is != null) {
			try {

				//default number set to 1
				String endLine = "\n" + BookOrder.END_RPOTOCOL + "\n";
				os.writeBytes(getJsonMsg(bookName, 1) + endLine);

				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String responseLine;
				while ((responseLine = br.readLine()) != null) {
					if (responseLine.indexOf(BookOrder.END_RPOTOCOL) != -1) {
						break;
					}
					System.out.println("Server: " + responseLine);
				}
				
			} catch (UnknownHostException e) {
				System.err.println("Trying to connect to unknown host: " + e);
				return false;
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
				return false;
			}finally{
				try {
					if (os != null)
							os.close();
					if( is != null)
						is.close();
					if(clientSocket != null)
						clientSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return true;
		
	}
	
	
	
	
	public static String getJsonMsg(String bookName, int number){
		
		JsonObject orderMsg = new JsonObject();
		orderMsg.addProperty("bookName", bookName);
		orderMsg.addProperty("number", number);
		
		return orderMsg.toString();
	}
	
	
	
	public static void main(String[] args) {
		
		List<Thread> clients = new ArrayList<Thread>();
		int clientNum = 20;
		
		String server = "127.0.0.1";
		int port = 9996;
		
		//RandomID
		for(int i = 0; i < clientNum; i++){
			Thread client = new Thread(new BookStoreClient(server, port, Util.genRandomBookName()));
			clients.add(client);
			client.run();
		}
		try {
			for(Thread client : clients)
				client.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
