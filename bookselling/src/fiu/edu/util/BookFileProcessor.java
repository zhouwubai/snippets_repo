package fiu.edu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import fiu.edu.db.DBUtil;
import fiu.edu.server.Book;

public class BookFileProcessor {
	
	public static String INSERT_BOOK_QUERY = "insert into bookstore (ISBN,bookname,authors,edition,price,quantities)" +
										" VALUES (?,?,?,?,?,?)";
	public static String UPDATE_BOOK_SQL = "update bookstore set quantities = ? where bookname = ? ";

	public static List<Book> parseBookFile(String path){
		
		BufferedReader br = null;
		List<Book> books = new ArrayList<Book>();
		
		try {
			br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			while(line != null){
				
				if(line.split(":")[0].trim().equals("Book Name")){//parse to one book
					String bookName = line.substring(line.indexOf(":") + 1).trim();
					line = br.readLine();
					String authors = line.substring(line.indexOf(":") + 1).trim();
					line = br.readLine();
					String ISBN = line.substring(line.indexOf(":") + 1).trim();
					line = br.readLine();
					int edition = Integer.parseInt(line.substring(line.indexOf(":")+1).trim());
					line = br.readLine();
					Float price = Float.parseFloat(line.substring(line.indexOf(":")+1).trim());
					line = br.readLine();
					int quantities = Integer.parseInt(line.substring(line.indexOf(":")+1).trim());
					
					Book book = new Book(bookName, authors, ISBN, edition, price, quantities);
					books.add(book);                                                                                                                             
				}else{
					line = br.readLine();
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return books;
	}
	
	
	
	public static boolean importBooks2db(List<Book> books){
		
		Connection conn = null;
		
		try {
			
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstm = conn.prepareStatement(INSERT_BOOK_QUERY);
			Book tmpBook = null;
			
			for(int i = 0; i < books.size(); i++){
				
				tmpBook = books.get(i);
				pstm.setString(1, tmpBook.getISBN());
				pstm.setString(2, tmpBook.getBookName());
				pstm.setString(3, tmpBook.getAuthors());
				pstm.setInt(4, tmpBook.getEdition());
				pstm.setFloat(5, tmpBook.getPrice());
				pstm.setInt(6, tmpBook.getQuantities());
				
				pstm.addBatch();
				
				if((i + 1) % 500 == 0){
					pstm.executeBatch();
					conn.commit();
				}
			}
			
			pstm.executeBatch();
			conn.commit();
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
	}
	
	
	
	public static boolean restoreBooksDB(List<Book> books){
		
		Connection conn = null;
		
		try {
			
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstm = conn.prepareStatement(UPDATE_BOOK_SQL);
			Book tmpBook = null;
			
			for(int i = 0; i < books.size(); i++){
				
				tmpBook = books.get(i);
				pstm.setInt(1, tmpBook.getQuantities());
				pstm.setString(2, tmpBook.getBookName());
				
				pstm.addBatch();
				
				if((i + 1) % 500 == 0){
					pstm.executeBatch();
					conn.commit();
				}
			}
			
			pstm.executeBatch();
			conn.commit();
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		
		String base = System.getProperty("user.dir");
		String bookFile = base + File.separator + "conf/booklist.txt";
		System.out.println(bookFile);
		List<Book> books = BookFileProcessor.parseBookFile(bookFile);
		System.out.println(books.size());
//		System.out.println(BookFileProcessor.importBooks2db(books));
		
		System.out.println(BookFileProcessor.restoreBooksDB(books));
	}
}
