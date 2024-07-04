package com.ramadan.api.helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class Utils {

	private static final String CHARACTERS = "0123456789";
    private static final int CODE_LENGTH = 6;

    public static  String getHashedUuid( LocalDateTime dateCreation,  Long id) {
        UUID uuid = UUID.randomUUID();
        String hashString = uuid.toString() + dateCreation.toString() + id.toString();
        byte[] hashBytes = sha256(hashString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes);
    }
    
    public static  String getHashedToken( LocalDateTime dateCreation,  String id) {
        UUID uuid = UUID.randomUUID();
        String hashString = uuid.toString() + dateCreation.toString() + id;
        byte[] hashBytes = sha256(hashString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes);
    }

    private static byte[] sha256(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte  [] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    
    public static Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;
	}

    public static List<Order> getListOrderBySort(String[] sort){
    	
    	List<Order> orders = new ArrayList<Order>();

		if (sort[0].contains(",")) {
			for (String sortOrder : sort) {
				String[] _sort = sortOrder.split(",");
				orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
			}
		} else {
			orders.add(new Order(getSortDirection(sort[1]), sort[0]));
		}
		
		return orders;
    }
    /////ILYASS COSTUMER
    public static List<Order> getListOrderBySorts(String[] sort) {
        List<Order> orders = new ArrayList<>();

        if (sort.length == 0) {
            return orders;
        }

        for (String sortOrder : sort) {
            String[] _sort = sortOrder.split(",");
            if (_sort.length == 2) {
                orders.add(new Order(getSortDirections(_sort[1]), _sort[0]));
            } else {
                orders.add(new Order(getSortDirections("asc"), _sort[0]));
            }
        }

        return orders;
    }

    private static Sort.Direction getSortDirections(String direction) {
        return direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
/////
    
    public static String generateOtp() {
    	
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
       
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
}
    
    public static String generateHash() {
    	
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
       
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
}

    public static String addCharacterBetween(String input, char character , int groupSize) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < input.length(); i++) {
            result.append(input.charAt(i));
            
            if ((i + 1) % groupSize == 0 && i < input.length() - 1) {
                result.append(character);
            }
        }
        return result.toString();
    }
    
	  public static String generateCode() {
	    	
	        SecureRandom random = new SecureRandom();
	        StringBuilder code = new StringBuilder();
	        for (int i = 0; i < CODE_LENGTH; i++) {
	            int randomIndex = random.nextInt(CHARACTERS.length());
	            char randomChar = CHARACTERS.charAt(randomIndex);
	            code.append(randomChar);
	        }
	
	        return code.toString();
	}
}
