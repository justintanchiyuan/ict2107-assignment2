package task6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ANSMapper extends Mapper<LongWritable, Text, Text, Text> {

	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		// Split all the columns in the .csv file
		String[] parts = value.toString().split(",");
		// Access the column containing tweet messages
		String tweet = parts[21];
		// Access the column containing the unit id
		String id = parts[0];
		// Write data for shuffling phase
		context.write(new Text(id), new Text(tweet));
	}
}
