package task4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import task4.TaskFourDriver;
import task4.TaskFourMapper;
import task4.TaskFourReducer;
import task4.TaskFourValidationMapper;

public class TaskFourDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "TaskFourDriver");
		job.setJarByClass(TaskFourDriver.class);
		// Path inPath1 = new
		// Path("hdfs://localhost:9000/user/phamvanvung/taskFour/input/Airline-Full-Non-Ag-DFE-Sentiment_20150225.csv");
		// Path inPath2 = new
		// Path("hdfs://localhost:9000/user/phamvanvung/taskFour/input/Airline-Full-Non-Ag-DFE-Sentiment_20150226.csv");
		// Path inPath3 = new
		// Path("hdfs://localhost:9000/user/phamvanvung/taskFour/input/Airline-Full-Non-Ag-DFE-Sentiment_20150227.csv");
		// Path inPath4 = new
		// Path("hdfs://localhost:9000/user/phamvanvung/taskFour/input/Airline-Full-Non-Ag-DFE-Sentiment_20150228.csv");
		// Path inPath5 = new
		// Path("hdfs://localhost:9000/user/phamvanvung/taskFour/input/Airline-Full-Non-Ag-DFE-Sentiment_20150301.csv");
		// Path inPath6 = new
		// Path("hdfs://localhost:9000/user/phamvanvung/taskFour/input/Airline-Full-Non-Ag-DFE-Sentiment_20150302.csv");
		// Path inPath7 = new
		// Path("hdfs://localhost:9000/user/phamvanvung/taskFour/input/Airline-Full-Non-Ag-DFE-Sentiment_20150303.csv");
		// Path inPath8 = new
		// Path("hdfs://localhost:9000/user/phamvanvung/taskFour/input/Airline-Full-Non-Ag-DFE-Sentiment_20150304.csv");
		// Path inPath9 = new
		// Path("hdfs://localhost:9000/user/phamvanvung/taskFour/input/Airline-Full-Non-Ag-DFE-Sentiment_20150305.csv");
		// Path inPathA = new
		// Path("hdfs://localhost:9000/user/phamvanvung/taskFour/input/Airline-Full-Non-Ag-DFE-Sentiment_20150306.csv");
		Path inPathB = new Path("hdfs://localhost:9000/user/phamvanvung/airline/input/");
		Path outPath = new Path("hdfs://localhost:9000/user/phamvanvung/airline/output/");
		outPath.getFileSystem(conf).delete(outPath, true);

		Configuration validationConf = new Configuration(false);
		ChainMapper.addMapper(job, TaskFourValidationMapper.class, LongWritable.class, Text.class, LongWritable.class,
				Text.class, validationConf);

		Configuration ansConf = new Configuration(false);
		ChainMapper.addMapper(job, TaskFourMapper.class, LongWritable.class, Text.class, Text.class, IntWritable.class,
				ansConf);

		job.setMapperClass(ChainMapper.class);

		job.setCombinerClass(TaskFourReducer.class);
		job.setReducerClass(TaskFourReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// FileInputFormat.addInputPath(job, inPath1);
		// FileInputFormat.addInputPath(job, inPath2);
		// FileInputFormat.addInputPath(job, inPath3);
		// FileInputFormat.addInputPath(job, inPath4);
		// FileInputFormat.addInputPath(job, inPath5);
		// FileInputFormat.addInputPath(job, inPath6);
		// FileInputFormat.addInputPath(job, inPath7);
		// FileInputFormat.addInputPath(job, inPath8);
		// FileInputFormat.addInputPath(job, inPath9);
		// FileInputFormat.addInputPath(job, inPathA);
		FileInputFormat.addInputPath(job, inPathB);
		FileOutputFormat.setOutputPath(job, outPath);
		job.waitForCompletion(true);
		// System.exit(job.waitForCompletion(true)?0:1);
	}

}
