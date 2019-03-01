package com.purplepuli.logging;

public class LogId {

	Character[] value;
	Character[] elements = {'0','1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	int length;
	
	public LogId(int length) {
		super();
		this.length = length;
		value = new Character[length];
		
		initilizeValue();
	}
	
	private void initilizeValue() {
		for (int i = 0; i < value.length; i++) {
			value[i] = elements[0];
		}
	}

	public String getNextId() {
		String retVal = convertToString();
		
		long longValue = convertToLong();
		
		convertToCharArray(++longValue);
		return retVal;
	}
	private String convertToString() {
		
		StringBuffer sb = new StringBuffer();
				
		for (Character character : value) {
			sb.append(character);
		}
		return sb.toString();
	}

	private void convertToCharArray(long number) {
		int numberOfElements = elements.length;
		long divider = numberOfElements;
		long numberToConvert = number;
		
		initilizeValue();
		int i = 1;
		do {
			int character = (int)(numberToConvert % divider);
			value[value.length - i] = elements[character];
			numberToConvert /= divider;
			i++;
		} while (numberToConvert > 0);
	}

	private long convertToLong() {
		long retVal = 0;
		int numberOfElements = elements.length;
		for (int i = 0; i < value.length; i++) {
			//System.err.println("char: " + value[value.length - i - 1]);
			Character actualChar = value[value.length - i - 1];
			int position = getPosition(actualChar);
			retVal += position * Math.pow(numberOfElements, i);
			
		}
		
		return retVal;
	}
	
	private int getPosition(Character actualChar) {
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] == actualChar) {
				return i;
			}
		}
		return -1;
	}

	public String test(long number)  {
		long longValue = convertToLong();
		convertToCharArray(longValue + number);
		return convertToString();
	}
	
	
}
