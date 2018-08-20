package part2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Naive Bayes Classifier Comp307 A3 part2
 * @author James Sutton
 *
 */
public class NaiveBayes {
	
	public static List<Instance> training;
	public static List<Instance> test;
	
	public static Classifier classifier;
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){

		training = readTraining("spamLabelled.dat");
		test = readTest("spamUnlabelled.dat");
		
		classifier = new Classifier(training);
		
		classifier.printClassifier();
		
		for(int i = 0; i < test.size(); i++){
			System.out.println();
			System.out.println("Instance "+(i+1)+":");
			test.get(i).setClassification(classifier.classify(test.get(i)));
		}
		
		
//		for(int j = 0; j < test.size(); j++){
//			int x = j+1;
//			System.out.println("Instance "+x+": "+test.get(j).getClassification());
//		}
	}

	/**
	 * read in training data from files
	 * @param args
	 */
	private static ArrayList<Instance> readTraining(String filename) {
		ArrayList<Instance> data = new ArrayList<Instance>();
		try {
            File file = new File(filename);

            Scanner input = new Scanner(file);
            
            int[] features = new int[12];
            int c;
            while (input.hasNextLine() && input.hasNextInt()) {
            	for(int i = 0; i < 12; i++){
            		features[i] = input.nextInt();
            	}
            	c = input.nextInt();
            	data.add(new Instance(features,c));
            }
            input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * read in test data from files
	 * @param args
	 */
	private static ArrayList<Instance> readTest(String filename) {
		ArrayList<Instance> data = new ArrayList<Instance>();
		try {
            File file = new File(filename);

            Scanner input = new Scanner(file);
            
            int[] features = new int[12];
            while (input.hasNextLine() && input.hasNextInt()) {
            	for(int i = 0; i < 12; i++){
            		features[i] = input.nextInt();
            	}
            	data.add(new Instance(features,-1));
            }
            input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
}