package utilities;

import java.time.LocalDate;
import java.util.Random;

public class RandomUtil {

	public static int getRandomNumberBetween(int startInt, int endInt) {

		int randomNumber = (int) (Math.random() * (endInt - startInt + 1) + (startInt));
		System.out.println("The random number is " + randomNumber);
		return randomNumber;
	}

	public static String getRandomNumberOfLength(int length) {

		// chose a Character random from this String
		String numericString = "0123456789";

		// create StringBuffer size of NumericString
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {

			// generate a random number between
			// 0 to NumericString variable length
			int index = (int) (numericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(numericString.charAt(index));
		}

		return sb.toString();
	}

	public static String getRandomStringOfLength(int length) {

		// chose a Character random from this String
		String AlphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaString
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {

			// generate a random number between
			// 0 to AlphaString variable length
			int index = (int) (AlphaString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaString.charAt(index));
		}

		return sb.toString();

	}

	public static String getRandomAlphaNumericStringOfLength(int length) {

		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
				+ "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();

	}

	public static String getRandomUpperCaseLettersOfLength(int length) {

		// chose a Character random from this String
		String AlphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// create StringBuffer size of AlphaString
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {

			// generate a random number between
			// 0 to AlphaString variable length
			int index = (int) (AlphaString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaString.charAt(index));
		}

		return sb.toString();

	}

	public static String getRandomLowerCaseLettersOfLength(int length) {

		// chose a Character random from this String
		String AlphaString = "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaString
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {

			// generate a random number between
			// 0 to AlphaString variable length
			int index = (int) (AlphaString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaString.charAt(index));
		}

		return sb.toString();

	}

	public static String[][] getRandomStringArrayOfLength(int rowLength,
			int columnLength, int stringLength) {

		String[][] randomArray = new String[rowLength][columnLength];

		for (int i = 0; i < rowLength; i++) {
			for (int j = 0; j < columnLength; j++) {
				randomArray[i][j] = getRandomStringOfLength(stringLength);
			}
		}
		return randomArray;
	}

	public static LocalDate getRandomDate(int minAge, int maxAge) {

		// Subtracting Current date with Minimum Age to get the Maximum Year
		LocalDate maxYear = LocalDate.now().minusYears(minAge);

		// Getting random year from between the minimum and maximum age to be
		// the Minimum Year
		int minYear = getRandomNumberBetween(0, maxAge - minAge);

		// Subtracting the maximum year with minimum year to get the Valid Year
		// as the Random Date in the Year
		LocalDate randomDate = maxYear.minusYears(minYear);

		return randomDate;

	}
}
