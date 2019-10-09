import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Unscaler {

	public static void main(String[] args) {
		
		File file1 = new File("files/learning-rate/train/train5");
		
		ArrayList<String> myArray = new ArrayList<String>();
		ArrayList<String> desiredOutcome = new ArrayList<String>();
		ArrayList<String> actualOutcome = new ArrayList<String>();
		
		int numPat;
		
		//Adding all lines from file to ArrayList
		try (Scanner input = new Scanner(file1)) {
			while(input.hasNextLine()) {
				myArray.add(input.nextLine());
			
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		//Remove lines 1-10 (Headers and formatting stuff)
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
		
		//Converting String arrays to double. 
		ArrayList<Double> desiredOutcomeDouble = new ArrayList<Double>();
		ArrayList<Double> actualOutcomeDouble = new ArrayList<Double>();
		
		if (desiredOutcomeDouble.size() != actualOutcomeDouble.size()) {
			System.out.println("Error - mismatched arrays.");
			System.exit(0);
		}
		
		numPat = desiredOutcome.size();
		
		desiredOutcomeDouble = getDoubleArray(desiredOutcome);
		actualOutcomeDouble = getDoubleArray(actualOutcome);
		
		
		double TSS = 0;
		for (int i = 0; i < actualOutcome.size(); i++) {
			
			TSS += ((desiredOutcomeDouble.get(i) - actualOutcomeDouble.get(i)) * ((desiredOutcomeDouble.get(i) - actualOutcomeDouble.get(i))));
		}
		
		double MSE = (TSS / numPat);
		System.out.println("Patterns: " + numPat + "\nTSS: " + TSS + "\nMSE: " + MSE);
	}
	
	//Converts ArrayList<Strin> into ArrayList<Double>
	private static ArrayList<Double> getDoubleArray(ArrayList<String> stringArray) {
        ArrayList<Double> result = new ArrayList<Double>();
        for(String stringValue : stringArray) {
            try {
                result.add(Double.parseDouble(stringValue.trim()));
            } catch(NumberFormatException nfe) {
               System.out.println("Could not parse " + nfe);
            } 
        }       
        return result;
    }
} 
