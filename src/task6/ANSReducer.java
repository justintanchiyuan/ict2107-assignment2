package task6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class ANSReducer extends Reducer<Text, Text, Text, Text> {
	// Create hashmap to store the unit id to tweet message key pair
	Map<Text, Text> tweetMap = new HashMap<>();

	@Override
	protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {

		for (Text value : values) {
			/**
			 * Reduces data by not mapping redundant twitter messages that are
			 * repeated, messages that shares the same key are repeated, hence
			 * if the key already exist the message already exist as well and
			 * will not be added to the hashmap
			 */
			//
			if (!tweetMap.containsKey(key)) {
				tweetMap.put(new Text(key), new Text(value));
			}

		}

	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		/**
		 *  Count the number of occurrence where a twitter message contains "#sfo" or "delayed"
		 *  regardless of case and increase the counter in the hashmap
		 */
		int delayedCount = 0;
		for (Text key : tweetMap.keySet()) {
			String text = tweetMap.get(key).toString().toLowerCase();

			if (text.contains("#sfo") || text.contains("delayed")) {
				delayedCount++;
			}

		}
		// Write the result indicating number of flights delayed
		context.write(new Text(Integer.toString(delayedCount)), new Text("flights delayed"));
	}

}
