package fiu.edu.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	private static final String HEX_DIGITS = "0123456789abcdef";
	private static final int BYTE_MASK = 0xFF;
    private static final int HEX_DIGIT_MASK = 0xF;
    private static final int HEX_DIGIT_BITS = 4;
    
    private static final String[] bookNames = {"C Programming Language",
    					"Computer Networking: A Top-Down Approach",
    					"Introduction to Algorithms",
    					"Cracking the Coding Interview",
    					"Does Exist book"};
    
	
    
	public static String computeSha1OfString(String message){
		
		byte[] byteMsg = null;
		MessageDigest md = null;
		
		try {
			byteMsg = message.getBytes("UTF-8");
			md = MessageDigest.getInstance("SHA-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		md.update(byteMsg);
		byte[] res = md.digest();
		return toHexString(res);
	}
	
	
	public static String toHexString(byte[] byteArray){
		StringBuilder sb = new StringBuilder(byteArray.length * 2);
		for(int i = 0; i < byteArray.length; i++){
			int b = byteArray[i] & BYTE_MASK;
			sb.append(HEX_DIGITS.charAt(b >>> HEX_DIGIT_BITS)).append(
					HEX_DIGITS.charAt(b & HEX_DIGIT_MASK));
		}
		
		return sb.toString();
	}
	
	
	public static String genRandomBookName(){
		int idx = (int)(Math.random() * 5);
		return bookNames[idx];
	}
	
	public static void main(String[] args) {
		
		String bookname = genRandomBookName();
		System.out.println(bookname);
		
	}
}
