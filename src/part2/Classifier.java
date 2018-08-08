package part2;

import java.util.List;

public class Classifier {
	
	private double spamProb = 0;
	private double nonSpamProb = 0;
	private double s = 0; 
	private double ns = 0;
	
	private double[][] featureCount = new double[2][24]; //row 0 = nonspam class, row 1 = spam class. Col 0-11 = feature 1- 12 false, Col 12-23 = feature 1-12 true (A little confusing I know sorry)

	public Classifier(List<Instance> data) {
		
		//init counts to 1
		for (int row = 0; row < 2; row ++){
		    for (int col = 0; col < 24; col++){
		        featureCount[row][col] = 1;
		    }
		}
		
		int f = 0;
		for(Instance i : data){
			if(i.getClassification() == 0){
				ns++;
				int[] feat = i.getFeatures();
				for(int j = 0; j < 12; j++){
					f = feat[j];
					
					if(f == 0){
						
						featureCount[0][j]++;
					}
					else{
						featureCount[0][23-j]++;
					}
				}
				
			}
			else if(i.getClassification() == 1){
				s++;
				int[] feat = i.getFeatures();
				for(int j = 0; j < 12; j++){
					f = feat[j];
					if(f == 0){
						featureCount[1][j]++;
					}
					else{
						featureCount[1][23-j]++;
					}
				}
				
			}
		}
		
		this.spamProb = (s+1)/(s+ns+2);
		this.nonSpamProb = (ns+1)/(s+ns+2);
		
		
	}
	
	/**
	 * classifies test instance
	 * @param i
	 * @return
	 */
	public int classify(Instance i){
		
		int[] features = i.getFeatures();
		double spamScore = spamProb;
		int b = 0;
		for(int j = 0; j < 12; j++){
			b = features[j];
			double featProb = getFeatureProb(1,j,b);
			spamScore *= featProb;
		}
		
		double nonSpamScore = nonSpamProb;
		b = 0;
		for(int k = 0; k < 12; k++){
			b = features[k];
			double featProb = getFeatureProb(0,k,b);
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
	 * @param bin - binary value of feature
	 * @return
	 */
	public double getFeatureProb(int spam, int feature, int bin){
		if(bin == 0){
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
