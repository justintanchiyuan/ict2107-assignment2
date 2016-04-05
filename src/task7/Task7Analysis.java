package task7;
import java.net.URI;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Task7Analysis {
	public static void main(String args[]) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"Task7Analysis");
		
		job.setJarByClass(Task7Analysis.class);
		
		Path inputPath = new Path("hdfs://localhost:9000/user/phamvanvung/airline/input/");
		Path outputPath = new Path("hdfs://localhost:9000/user/phamvanvung/airline/output/");
		outputPath.getFileSystem(conf).delete(outputPath, true);

		Configuration validationConf = new Configuration(false);
		ChainMapper.addMapper(job, Task7ValidationMapper.class, LongWritable.class, Text.class, LongWritable.class, Text.class, validationConf);
		
		Configuration task7mapperConf = new Configuration(false);
		ChainMapper.addMapper(job, Task7Mapper.class, LongWritable.class, Text.class, Text.class, IntWritable.class, task7mapperConf);		

		job.setMapperClass(ChainMapper.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setReducerClass(Task7Reducer.class);
		
		FileInputFormat.addInputPath(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		job.waitForCompletion(true);
		//System.exit((job.waitForCompletion(true))?0:1);
	}
}