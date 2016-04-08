package task6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ANSReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	int delayCount =0;
	List tweetList = new ArrayList();
	

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
	
		/**
		 * We want to check filter out duplicate tweets again here, in the mapper,
		 * we have only filtered out duplicate tweets of EACH .csv. Now we will 
		 * filter out duplicates of all 10 .csv
		 */
		for (IntWritable value : values) {
			if (!tweetList.contains(key.toString())) {
				tweetList.add(key.toString());
				delayCount++;
				}
			}
			
		}

	

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		
		// Write the result indicating number of flights delayed
		context.write(new Text("Number of flights delayed:"), new IntWritable(delayCount));
	}

}