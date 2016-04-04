package task5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ANSReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
	// Create a hashmap to store the airlines as key and their trusting point
	// values that are separated by commas
	// as value
	Map<Text, Text> airlineTrustMap = new HashMap<>();

	@Override
	protected void reduce(Text key, Iterable<DoubleWritable> values,
			Reducer<Text, DoubleWritable, Text, DoubleWritable>.Context context)
					throws IOException, InterruptedException {
		// Concatenate all the trusting point values for the current key into
		// "trusts"
		String trusts = "";
		for (DoubleWritable value : values) {
			trusts += value.get() + ",";
		}
		// Put the key and its concatenated point values into the hashmap
		airlineTrustMap.put(new Text(key), new Text(trusts));

	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {

		for (Text key : airlineTrustMap.keySet()) {
			// Retrieve each of the concatenated trusting point values into a
			// String array
			String trusts[] = airlineTrustMap.get(key).toString().split(",");
			
			// Convert the String array into a double array list
			int size = trusts.length;
			ArrayList<Double> doubleTrusts = new ArrayList<Double>();
			for (int i = 0; i < size; i++) {
				doubleTrusts.add(Double.parseDouble(trusts[i]));
			}
			
			// Sort this array list
			Collections.sort(doubleTrusts);

			// Calculate median using arithmetic formula
			double median;
			if (doubleTrusts.size() % 2 == 0)
				median = ((double) doubleTrusts.get(doubleTrusts.size() / 2)
						+ (double) doubleTrusts.get(doubleTrusts.size() / 2 - 1)) / 2;
			else
				median = (double) doubleTrusts.get(doubleTrusts.size() / 2);
			// Write the median result for this particular key (airline)
			context.write(new Text(key), new DoubleWritable(median));
		}
	}
}
