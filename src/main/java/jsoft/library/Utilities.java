package jsoft.library;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.servlet.*;
import net.htmlparser.jericho.*;

public class Utilities {

	public static byte getByteParam(ServletRequest request, String name) {
		byte value = -1;

		String str_value = request.getParameter(name);
		if (str_value != null && !str_value.equalsIgnoreCase("")) {
			value = Byte.parseByte(str_value);
		}

		return value;
	}

	public static short getShortParam(ServletRequest request, String name) {
		short value = -1;

		String str_value = request.getParameter(name);
		if (str_value != null && !str_value.equalsIgnoreCase("")) {
			value = Short.parseShort(str_value);
		}

		return value;
	}

	public static int getIntParam(ServletRequest request, String name) {
		int value = -1;

		String str_value = request.getParameter(name);
		if (str_value != null && !str_value.equalsIgnoreCase("")) {
			value = Integer.parseInt(str_value);
		}

		return value;
	}

	public static String getMd5(String input) {
		try {

			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String encode(String str_unicode) {
		return CharacterReference.encode(str_unicode);
	}

	public static String decode(String str_html) {
		return CharacterReference.decode(str_html);
	}

	public static boolean saveFile(InputStream file, String filePath) {
		try {
			File newFile = new File(filePath);
			boolean fileCreated = true;
			
			if(!newFile.exists()) {
				fileCreated = newFile.createNewFile();				
			}
			
			if(fileCreated) {
				FileOutputStream outputStream = new FileOutputStream(newFile);
				int read = 0;
				byte[] bytes = new byte[1024];
				
				while((read = file.read(bytes)) != -1 ) {
					outputStream.write(bytes, 0, read);
				}
				
				outputStream.flush();
				outputStream.close();
				return true; // tra ve true neu khi thanh cong
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static int convertStringToInt(String string) {		
		int result = 0;
		
        try {
            // Check if product_price is not null and not empty
            if (string != null && !string.isEmpty()) {
                // Convert the string to an integer
            	result = Integer.parseInt(string);
//                System.out.println("Converted product price: " + result);
            } else {
//                System.out.println("Product price is null or empty.");
            }
        } catch (NumberFormatException e) {
            // Handle the exception if the string is not a valid integer
//            System.out.println("Invalid product price format: " + result);
        }
		return result;
	}
	
	public static short convertStringToShort(String string) {		
		short result = 0;
		
        try {
            // Check if product_price is not null and not empty
            if (string != null && !string.isEmpty()) {
                // Convert the string to an integer
            	result = Short.parseShort(string);
                System.out.println("Converted product price: " + result);
            } else {
                System.out.println("Product price is null or empty.");
            }
        } catch (NumberFormatException e) {
            // Handle the exception if the string is not a valid integer
            System.out.println("Invalid product price format: " + result);
        }
		return result;
	}
}
