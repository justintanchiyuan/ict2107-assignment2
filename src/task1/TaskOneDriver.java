package task1;


import java.util.Date;

//import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import task1.TaskOneMapper;
import task1.TaskOneReducer;
import task1.TaskOneValidationMapper;
import task1.TaskOneDriver;

public class TaskOneDriver  {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "TaskOneDriver");
		job.setJarByClass(TaskOneDriver.class);
		Path inPath = new Path("hdfs://localhost:9000/user/phamvanvung/airline/input/");
		Path outPath = new Path("hdfs://localhost:9000/user/phamvanvung/airline/output/");
		outPath.getFileSystem(conf).delete(outPath, true);
		
		Configuration validationConf = new Configuration(false);
		ChainMapper.addMapper(job, TaskOneValidationMapper.class, LongWritable.class, Text.class, LongWritable.class, Text.class, validationConf);
		
		Configuration ansConf = new Configuration(false);
		ChainMapper.addMapper(job, TaskOneMapper.class, LongWritable.class, Text.class, Text.class, IntWritable.class, ansConf);		
		
		job.setMapperClass(ChainMapper.class);
		
		job.setCombinerClass(TaskOneReducer.class);
		job.setReducerClass(TaskOneReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, inPath);
		FileOutputFormat.setOutputPath(job, outPath);
		job.waitForCompletion(true);
		//System.exit(job.waitForCompletion(true)?0:1);
	}

}
