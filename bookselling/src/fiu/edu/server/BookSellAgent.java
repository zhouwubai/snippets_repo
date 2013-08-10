package fiu.edu.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class BookSellAgent implements Runnable {
	
	private Socket sessionSocket;
	private DataInputStream is;
	private PrintStream os ;
	
	public BookSellAgent(Socket sessionSocket) {
		// TODO Auto-generated constructor stub
		this.sessionSocket = sessionSocket;
		this.is = null;
		this.os = null;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String line;
		try {
			
			is = new DataInputStream(sessionSocket.getInputStream());
			os = new PrintStream(sessionSocket.getOutputStream());
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			
			while (true) {
				line = br.readLine();
				if (line.indexOf(BookOrder.END_RPOTOCOL) != -1) {
					break;
				}
				sb.append(line);
			}
			
			BookOrder bookOrder = BookOrder.parseBookOrder(sb.toString());
//			System.out.println(bookOrder);
			
			String result = BookOrder.sellBookByOrder(bookOrder);
			os.println(result + "\n"+BookOrder.END_RPOTOCOL + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(is != null)
					is.close();
				if (os != null)
					os.close();
				if( sessionSocket != null)
					sessionSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
