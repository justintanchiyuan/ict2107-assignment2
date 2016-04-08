package task8;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Task8Mapper extends Mapper<Text, Text, Text, Text> {
	private Map<String, Double> dictionary;

	@Override
	protected void setup(Mapper<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
		// Read the text file and stored into a dictionary.
		
		dictionary = new HashMap<String, Double>();

		HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();

		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new FileReader("SentiWordNet_3.0.0_20130122.txt"));
			String line;
			while ((line = buffer.readLine()) != null) {
				// If it's a comment, skip this line.
				if (!line.trim().startsWith("#")) {
					String[] data = line.split("\t");
					String wordTypeMarker = data[0];
					if (data.length != 6) { // if line is invalid
						throw new IllegalArgumentException("Incorrect format in file");
					}

					// score = PosS - NegS
					Double score = Double.parseDouble(data[2]) - Double.parseDouble(data[3]);

					// Get all synset terms
					String[] terms = data[4].split(" ");

					for (String termSplit : terms) {
						// Get rank
						String[] termAndRank = termSplit.split("#");
						String synTerm = termAndRank[0] + "#" + wordTypeMarker;

						int termRank = Integer.parseInt(termAndRank[1]);
						// What we get here is a map of the type:
						// term -> {score of synset#1, score of synset#2...}

						// Add map to term if doesn't exist
						if (!tempDictionary.containsKey(synTerm)) {
							tempDictionary.put(synTerm, new HashMap<Integer, Double>());
						}
						// Add synset link to synterm
						tempDictionary.get(synTerm).put(termRank, score);
					}
				}
			}

			// Go through all terms
			for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary .entrySet()){
				String word = entry.getKey();
				Map<Integer, Double> synSetScoreMap = entry.getValue();

				// Calculate weighted average and rank accordingly 
				double score = 0.0;
				double sum = 0.0;
				for (Map.Entry<Integer, Double> setScore : synSetScoreMap .entrySet()) {
					score += setScore.getValue() / (double) setScore.getKey();
					sum += 1.0 / (double) setScore.getKey();
				}
				score = score / sum;

				dictionary.put(word, score);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (buffer != null) {
				buffer.close();
			}
		}
	}

	@Override
	protected void map(Text key, Text value, Mapper<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		// Get emotion after analyzing
		String emotion = analyseTweet(value.toString());
		context.write(key, new Text(emotion));
	}
	
	public String analyseTweet(String sentence) {
		//Split and remove item that are not word in sentence  
		String[] sentenceList = sentence.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
		double d = 0.0d;
		for (String word : sentenceList) {
			// total up the words
			d += extract(word.toLowerCase());
		}
		return rating(d);
	}
	
	//Retrieve word sentimental score
	public Double extract(String word)
	{
	   Double total = new Double(0);
	    if(dictionary.get(word+"#n") != null)
	        total = dictionary.get(word+"#n") + total;
	    if(dictionary.get(word+"#a") != null)
	        total = dictionary.get(word+"#a") + total;
	    if(dictionary.get(word+"#r") != null)
	        total = dictionary.get(word+"#r") + total;
	    if(dictionary.get(word+"#v") != null)
	        total = dictionary.get(word+"#v") + total;
	    return total;
	}
	
	public String rating(Double score)
	{             
	    if(score>=0.75)
	    	return "Super Happy";
	    else if(score > 0.25 && score<=0.5)
	    	return "Very Happy";
	    else if(score > 0 && score>=0.25)
	    	return "Quite Happy";
	    else if(score < 0 && score>=-0.25)
	    	return "Quite Angry";
	    else if(score < -0.25 && score>=-0.5)
	    	return "Angry";
	    else if(score<=-0.75)
	    	return "Super Angry";
	    return "Neutral";
	}
}
