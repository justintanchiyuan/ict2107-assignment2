package task10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TaskExtra2Reducer extends Reducer<Text, Text, Text, DoubleWritable> {
	Map<Text, Text> trustLevelList = new HashMap<>();
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Reducer<Text, Text, Text, DoubleWritable>.Context context) throws IOException, InterruptedException {
		String trusts = "";
		for (Text value : values) {
			trusts += value + ",";
		}
		// Put the key and its concatenated point values into the hashmap
		trustLevelList.put(new Text(key), new Text(trusts));
		
		//System.out.println(trustLevelList.toString());
		//context.write(key, new DoubleWritable(0));
	}
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {

		for (Text k : trustLevelList.keySet()) {
			double avgTrust = 0;
			double computeAvgTrust=0;
			String trusting[] = trustLevelList.get(k).toString().split(",");
			int size = trusting.length;
			for (int i = 0; i < size-1; i++) {
				//doubleTrusts.add(Double.parseDouble(trusts[i]));
				avgTrust += Double.parseDouble(trusting[i]);
				
			}
			computeAvgTrust = avgTrust / size;
			context.write(k, new DoubleWritable(computeAvgTrust));
			System.out.println(k + " " +computeAvgTrust +"");
		}
		
	}
}
