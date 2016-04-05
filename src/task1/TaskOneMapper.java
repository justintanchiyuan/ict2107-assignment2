package task1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TaskOneMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		
		String[] parts = value.toString().split(",");
		String sentiment = parts[14];
		String sentimentReason = parts[15];
		
		// checks for !null sentiments and reasons
		// counts each negative reasons
		if (sentiment != null && sentimentReason != null) {
			if (sentiment.equals("negative")) {
				context.write(new Text(sentimentReason), new IntWritable(1));
			}
		}
	}

}
