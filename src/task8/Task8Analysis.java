package task8;
import java.net.URI;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Task8Analysis {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Task8Analysis");
		job.setJarByClass(Task8Analysis.class);
		
		Path inputPath = new Path("hdfs://localhost:9000/user/phamvanvung/airline/input/Airline-Full-Non-Ag-DFE-Sentiment.csv");
		Path outputPath = new Path("hdfs://localhost:9000/user/phamvanvung/airline/output/");
		outputPath.getFileSystem(conf).delete(outputPath, true);
		job.addCacheFile(new URI("hdfs://localhost:9000/user/phamvanvung/airline/input/SentiWordNet_3.0.0_20130122.txt"));
		
		Configuration task8selectConf = new Configuration(false);
		ChainMapper.addMapper(job, Task8Select.class, LongWritable.class, Text.class, Text.class, Text.class, task8selectConf);
		
		Configuration task8mapperConf = new Configuration(false);
		ChainMapper.addMapper(job, Task8Mapper.class, Text.class, Text.class, Text.class, Text.class, task8mapperConf);		
		
		job.setMapperClass(ChainMapper.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setReducerClass(Task8Reducer.class);
		
		FileInputFormat.addInputPath(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		job.waitForCompletion(true);
		//System.exit((job.waitForCompletion(true))?0:1);
	}
}