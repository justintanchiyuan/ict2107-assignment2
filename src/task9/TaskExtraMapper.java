package task9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TaskExtraMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	Hashtable<String, String> countryCodes = new Hashtable<>();

	@Override
	protected void setup(Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		BufferedReader br = new BufferedReader(new FileReader("ISO-3166-alpha3.tsv"));
		String line = null;
		while (true) {
			line = br.readLine();
			if (line != null) {
				String parts[] = line.split("\t");
				countryCodes.put(parts[0], parts[1]);
			} else {
				break; // finished reading
			}
		}
		br.close();
	}

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		String[] parts = value.toString().split(",");
		String airline = parts[16];
		String negativeComments = parts[15];
		if (airline != null && negativeComments != null) {
			if ((negativeComments.equals("lostluggae") || negativeComments.equals("damagedluggage"))
					&& airline != null) {
				context.write(new Text(airline), new IntWritable(1));
			}
		}
	}
}
