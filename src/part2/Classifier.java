package part2;

import java.util.Arrays;
import java.util.List;

/**
 * This class implements a Naive Bayes Classifier (Machine Learning)
 * @author James Sutton
 *
 */
public class Classifier {
	
	private double spamProb = 0;
	private double nonSpamProb = 0;
	private double s = 0; //counts number of spam training instances
	private double ns = 0; //counts number of nonspam training instances
	
	private double[][] featureCount = new double[2][24]; //row 0 = nonspam class, row 1 = spam class. Col 0-11 = feature 1-12 false, Col 12-23 = feature 12-1 true

	/**
	 * Implements a Naive Bayes Classifier for the instances
	 * @param data - List of Instances (nonspam or spam), contain 12 features which can either be true or false
	 */
	public Classifier(List<Instance> data) {
		
		//init counts to 1
		for(double[] row: featureCount) {
			Arrays.fill(row, 1);
		}
		
		int f = 0;
		for(Instance i : data){
			countFeatures(i, i.getClassification());
		}
		
		this.spamProb = (s+1)/(s+ns+2);
		this.nonSpamProb = (ns+1)/(s+ns+2);
	}

	/**
	 * feature counter for training instances
	 * @param i - training instance
	 * @param c - classification (nonspam or spam)
	 */
	private void countFeatures(Instance i, int c) {
		if(c == 0) {
			ns++;
		}
		else {
			s++;
		}
		int f;
		int[] feat = i.getFeatures();
		for(int j = 0; j < 12; j++){
			f = feat[j]; //feature value (false or true)
			//false
			if(f == 0){
				featureCount[c][j]++;
			}
			//true
			else{
				featureCount[c][23-j]++;
			}
		}
	}
	
	/**
	 * classifies test instance
	 * @param i - test instance
	 * @return
	 */
	public int classify(Instance i){
		
		int[] features = i.getFeatures();
		double spamScore = spamProb;
		int f = 0;
		for(int j = 0; j < 12; j++){
			f = features[j];
			double featProb = getFeatureProb(1,j,f);
			spamScore *= featProb;
		}
		
		double nonSpamScore = nonSpamProb;
		f = 0;
		for(int j = 0; j < 12; j++){
			f = features[j];
			double featProb = getFeatureProb(0,j,f);
			nonSpamScore *= featProb;
		}
		
		System.out.println("Spam Score = "+spamScore);
		System.out.println("Non-Spam Score = "+nonSpamScore);
		
		if(spamScore > nonSpamScore){
			System.out.println("Class = Spam");
			return 1;
		}
		else{
			System.out.println("Class = Non-Spam");
			return 0;
		}
	}
	
	/**
	 * calculates feature probability
	 * @param spam - class
	 * @param feature - feature number staring from 0 up to 11
	 * @param f - feature value (0=false, 1=true)
	 * @return
	 */
	public double getFeatureProb(int spam, int feature, int f){
		if(f == 0){
			if(spam == 0){
				return featureCount[spam][feature] / (ns+2);
			}
			else{
				return featureCount[spam][feature] / (s+2);
			}
		}
		else{
			if(spam == 0){
				return featureCount[spam][23-feature] / (ns+2);
			}
			else{
				return featureCount[spam][23-feature] / (s+2);
			}
		}
	}
	
	/**
	 * print classifier/feature probabilities
	 */
	public void printClassifier(){
		for(int i = 0; i < 2; i++){
			for(int j = 1; j < 13; j++){
				for(int k = 0; k < 2; k++){
					System.out.println("P(F"+j+" = "+k+" | C = "+i+") = "+getFeatureProb(i,j-1,k));
				}
			}
		}
	}

	public double getSpamProb() {
		return spamProb;
	}

	public void setSpamProb(double spamProb) {
		this.spamProb = spamProb;
	}

	public double getNonSpamProb() {
		return nonSpamProb;
	}

	public void setNonSpamProb(double nonSpamProb) {
		this.nonSpamProb = nonSpamProb;
	}
	

}
