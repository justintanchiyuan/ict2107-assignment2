package task6;

import java.net.URI;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * 
 * How many delayed flights? Tweets can be filtered to only those with certain 
 * keywords (i.e., “delayed”) or hashtags (i.e., “#SFO”) to find a set of relevant messages
 *
 */
public class AirlineNegativeSentiments {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "AirlineNegativeSentiments");
		job.setJarByClass(AirlineNegativeSentiments.class); //Set the main for the jar file of this job
		//Set the input/output format and path
		Path inPath = new Path("hdfs://localhost:9000/user/phamvanvung/airline/input/");
		Path outPath = new Path("hdfs://localhost:9000/user/phamvanvung/airline/output/"+ 
		new Date().getTime());//Output results of executions to different folders  
		
		//Create configuration for each mapper, specifying false prevents the default configuration from loading
		Configuration validationConf = new Configuration(false);
		Configuration ansConf = new Configuration(false);
		
		//Add the ANSValidationMapper and ANSMapper class for chain mapper 
		ChainMapper.addMapper(job, ANSValidationMapper.class, LongWritable.class, Text.class, LongWritable.class, Text.class, validationConf);
		ChainMapper.addMapper(job, ANSMapper.class, LongWritable.class, Text.class, Text.class, Text.class, ansConf);		
		
		job.setMapperClass(ChainMapper.class); //Set the mapper class		
		job.setReducerClass(ANSReducer.class); //Set the reducer class
		
		job.setOutputKeyClass(Text.class); //Set the output key format
		job.setOutputValueClass(Text.class); //Set the output data format
		
		job.setNumReduceTasks(1); //Set only one instance of reduce task
		
		FileInputFormat.addInputPath(job, inPath); //Ensure input files can be processed by mappers
		FileOutputFormat.setOutputPath(job, outPath); //Ensure output files can be processed reducers
		
		System.exit(job.waitForCompletion(true)?0:1); //Exit once job execution is completed
	}

}
