package task5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ANSMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, DoubleWritable>.Context context)
			throws IOException, InterruptedException {
		//Split all the columns in the .csv file
		String[] parts = value.toString().split(",");
		//Access the column containing airline name
		String airline = parts[16];
		double trust = 0;
		try {
			//Access the column containing the trusting point value and parse to double to filter out the table header
			//in the .csv file
			trust = Double.parseDouble(parts[8]);
		} catch (NumberFormatException e) {
			System.out.println("String to double parsing fail, probably table header: " + parts[8]);
		}
		
		//Check if data accessed is valid before shuffling phase
		if (airline != null && trust != 0) {
			context.write(new Text(airline), new DoubleWritable(trust));
		}
	}
}
