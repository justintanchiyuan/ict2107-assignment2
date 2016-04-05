package task4;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TaskFourMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		
		String[] parts = value.toString().split(",");
		String sentiment = parts[14];
		String airline = parts[16];
		if(sentiment !=null && airline != null){
			if(sentiment.equals("positive")){
				context.write(new Text(airline),new IntWritable(1));
			}
		}
	}

}
