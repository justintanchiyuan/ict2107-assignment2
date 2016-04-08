package task10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TaskExtra2Mapper extends Mapper<LongWritable, Text, Text, Text> {
		Hashtable<String, String> countryCodes = new Hashtable<>();

		@Override
		protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// We will put the ISO-3166-alpha3.tsv to Distributed Cache in the driver class
			// so we can access to it here locally by its name
			BufferedReader br = new BufferedReader(new FileReader("ISO-3166-alpha3.tsv"));
			String line = null;
			while (true) {
				line = br.readLine();
				if (line != null) {
					String parts[] = line.split("\t");
					countryCodes.put(parts[0], parts[1]);
				} else {
					break;// finished reading
				}
			}
			br.close();
		}

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] parts = value.toString().split(",");
			String countryCode = parts[10];
			String airline = parts[16];
			String trust = parts[8];
			String newkey = "";
			if (countryCode != null && airline != null) {
				String countryName = countryCodes.get(countryCode);
				newkey = airline +" "+countryName;
					context.write(new Text(newkey), new Text(trust));
			}
		}
}