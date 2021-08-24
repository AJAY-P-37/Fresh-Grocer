package utilities;

public class RandomUtil {

	public static int getRandomNumberBetween(int startInt, int endInt) {

		int randomNumber = (int) (Math.random() * (endInt - startInt + 1) + (startInt));
		System.out.println("The random number is " + randomNumber);
		return randomNumber;
	}

	public static String getRandomStringOfLength(int length) {

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
}
