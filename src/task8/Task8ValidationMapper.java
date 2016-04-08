package task8;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Task8ValidationMapper extends Mapper<LongWritable, Text, Text, Text> {
	ArrayList<String> tweetList = new ArrayList<String>();
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		
		if(isValid(value.toString())){
			String[] parts = value.toString().split(",");
			//Retrieve airline
			String airline = parts[16];	
			//Retrieve tweet
			String tweet = parts[21];
			
			//Check for duplicated tweet and not header
			if(!checkTweetExist(tweet) && !airline.equals("airline"))
			{
				tweetList.add(tweet);	
				context.write(new Text(airline), new Text(tweet));
			}
		}
	}
	
	private boolean isValid(String line){
		String[] parts = line.split(",");
		if (parts.length==27)
			return true;
		else
			return false;
	}
	
	// Function to check whether input tweet exist in the record list
	private boolean checkTweetExist(String tweet){
		for(int i=0; i<tweetList.size(); i++)
			if(tweetList.get(i).equals(tweet))
				return true;
		
		return false;
	}
}
