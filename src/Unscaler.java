import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Unscaler {

	public static void main(String[] args) {

		final int numFiles = 5;
		int testID = 1;
		
		for (int j = 0; j < numFiles; j++) {

			File file1 = new File("files/learning-rate/test" + testID);

			ArrayList<String> myArray = new ArrayList<String>();
			ArrayList<String> desiredOutcome = new ArrayList<String>();
			ArrayList<String> actualOutcome = new ArrayList<String>();

			int numPat;

			// Adding all lines from file to ArrayList
			try (Scanner input = new Scanner(file1)) {
				while (input.hasNextLine()) {
					myArray.add(input.nextLine());

				}
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		
			// Remove lines 1-10 (Headers and formatting stuff)
			for (int i = 0; i < 10; i++) {
				myArray.remove(0);
			}
			
			// Populating actual outcomes
			for (int i = 0; i < myArray.size(); i++) {
				if ((i + 1) % 7 == 0) {
					actualOutcome.add(myArray.get(i));
				}
			}

			myArray.add(0, "extra line");

			// Populating desired outcomes
			for (int i = 0; i < myArray.size(); i++) {
				if ((i + 1) % 7 == 0) {
					desiredOutcome.add(myArray.get(i));
				}
			}
			
			// Converting String arrays to double.
			ArrayList<Double> desiredOutcomeDouble = new ArrayList<Double>();
			ArrayList<Double> actualOutcomeDouble = new ArrayList<Double>();
			
			desiredOutcomeDouble = getDoubleArray(desiredOutcome);
			actualOutcomeDouble = getDoubleArray(actualOutcome);
		
			// Unscaling the values in each array
			for (int i = 0; i < desiredOutcomeDouble.size(); i++) {
				double newValue = ((desiredOutcomeDouble.get(i) * 88) + 2);
				desiredOutcomeDouble.set(i, newValue);
			}

			for (int i = 0; i < actualOutcomeDouble.size(); i++) {
				double newValue = ((actualOutcomeDouble.get(i) * 88) + 2);
				actualOutcomeDouble.set(i, newValue);
			}
			

			// Checking size of arrays match
			if (desiredOutcomeDouble.size() != actualOutcomeDouble.size()) {
				System.out.println("Error - mismatched arrays.");
				System.exit(0);
			}

			numPat = desiredOutcomeDouble.size();
			double TSS = 0;
			for (int i = 0; i < actualOutcome.size(); i++) {

				TSS += ((desiredOutcomeDouble.get(i) - actualOutcomeDouble.get(i))
						* ((desiredOutcomeDouble.get(i) - actualOutcomeDouble.get(i))));
			}

			double MSE = (TSS / numPat);
			double roundMSE = Math.round(MSE * 100.0) / 100.0;
			
			System.out
					.println("Result " + (j + 1) + "\nPatterns: " + numPat + "\nTSS: " + TSS + "\nMSE: " + roundMSE + "\n");

			testID++;
		}
	}

	// Converts ArrayList<String> into ArrayList<Double>
	private static ArrayList<Double> getDoubleArray(ArrayList<String> stringArray) {
		ArrayList<Double> result = new ArrayList<Double>();
		for (String stringValue : stringArray) {
			try {
				result.add(Double.parseDouble(stringValue.trim()));
			} catch (NumberFormatException nfe) {
				System.out.println("Error: " + nfe);
			}
		}
		return result;
	}
}
