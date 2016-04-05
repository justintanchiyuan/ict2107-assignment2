package task7;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class Task7Mapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	@Override
	protected void map(LongWritable key, Text value, 
			Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
		
			String[] parts = value.toString().split(",");
			String IP = parts[13];
			//if(IP != null && !IP.equals("_ip")){
			context.write(new Text(IP), new IntWritable(1));
		}
	}
	
