package fiu.edu.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fiu.edu.db.DBUtil;
import fiu.edu.util.Util;

public class BookOrder {

	private String bookName;
	private int number;

	public static String END_RPOTOCOL;
	public static Object lock = new Object();
	private static String BOOK_NUM_SQL = "select bookname, price, quantities from bookstore where bookname = ?";
	private static String BOOK_NUM_UPDATE_SQL = "update bookstore set quantities = quantities - ? where bookname = ?";

	static {
		BookOrder.END_RPOTOCOL = "@" + Util.computeSha1OfString("zhouwubai")
				+ "@";
	}

	public BookOrder(String bookName, int number) {
		// TODO Auto-generated constructor stub
		this.bookName = bookName;
		this.number = number;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getNum() {
		return number;
	}

	public void setNum(int num) {
		this.number = num;
	}

	public static BookOrder parseBookOrder(String orderMsg) {

		JsonElement jsonElem = new JsonParser().parse(orderMsg);
		JsonObject jsonObj = jsonElem.getAsJsonObject();

		String bookName = jsonObj.get("bookName").getAsString();
		int number = jsonObj.get("number").getAsInt();

		return new BookOrder(bookName, number);
	}

	// lock
	public static String sellBookByOrder(BookOrder bookOrder) {
		synchronized (lock) {
			Connection conn = null;
			JsonObject result = new JsonObject();
			PreparedStatement pstm = null;

			try {
				conn = DBUtil.getConnection();
				pstm = conn.prepareStatement(BOOK_NUM_SQL);
				pstm.setString(1, bookOrder.getBookName());
//				pstm.setInt(2, bookOrder.getNum());
				// System.out.println(pstm.toString());

				ResultSet numRs = pstm.executeQuery();
				if (numRs.next()) {

					int quantities = numRs.getInt(3);
					if (quantities >= bookOrder.getNum()) {
						float price = numRs.getFloat(2);
						pstm.close();
						pstm = conn.prepareStatement(BOOK_NUM_UPDATE_SQL);
						pstm.setInt(1, bookOrder.getNum());
						pstm.setString(2, bookOrder.getBookName());

						pstm.executeUpdate();

						result.addProperty("result", "true");
						result.addProperty("msg",
								"you just ordered " + bookOrder.getNum() + " "
										+ bookOrder.getBookName()
										+ " successfully." + "Whole cost is: "
										+ price * bookOrder.getNum());

						return result.toString();
					} else {
						result.addProperty("result", "false");
						result.addProperty("msg",
								"The book (" + bookOrder.getBookName() + ") you order is not available now.");
						return result.toString();
					}

				} else {
					result.addProperty("result", "false");
					result.addProperty("msg",
							"The book you order does not exist.");
					return result.toString();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				result.addProperty("result", "false");
				result.addProperty("msg",
						"Exception happens when changing database.");
				return result.toString();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}

	public static void main(String[] args) {

		String result = BookOrder.sellBookByOrder(new BookOrder(
				"Cracking the Coding Interview", 5));
		System.out.println(result);

	}

}
