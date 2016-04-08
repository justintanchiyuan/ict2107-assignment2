package task6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ANSMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	//ArrayList of unique tweets
	List tweetList = new ArrayList();

	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// Split all the columns in the .csv file
		String[] parts = value.toString().split(",");
		// Access the column containing tweet messages
		String tweet = parts[21];
		// Access the column containing the unit id
		String id = parts[0];
		// Convert content of tweet to lower case for easy comparison
		String text = tweet.toString().toLowerCase();

		/**
		 * Reduces duplicate by not mapping redundant twitter messages that are
		 * repeated, messages that shares the same key are repeated in the .csv files
		 * , hence if the key already exist the message already exist as well and 
		 * will not be added to the hashmap of unique tweets
		 */
		//
		if (!tweetList.contains(tweet)) {
			tweetList.add(tweet);
			// We check if the tweet message contains "#sfo" or "#delayed" here
			if (text.contains("#sfo") || text.contains("delayed")) {
				// Write unique tweet to context
				context.write(new Text(tweet), new IntWritable(1));
			}
		}

	}
}