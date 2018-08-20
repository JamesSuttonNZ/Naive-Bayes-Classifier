package part2;

/**
 * An instance of an email which is either spam or nonspam, with 12 features which are either true or false
 * @author James Sutton
 *
 */
public class Instance {

	private int[] features = new int[12];
	private int classification;

	public Instance(int[] f, int c){
		for(int i = 0; i < 12; i++){
			features[i] = f[i];
		}
		classification = c;
		
	}

	public int[] getFeatures() {
		return features;
	}

	public void setFeatures(int[] features) {
		this.features = features;
	}

	public int getClassification() {
		return classification;
	}

	public void setClassification(int classification) {
		this.classification = classification;
	}	
	
}
