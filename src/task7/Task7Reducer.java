package task7;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class Task7Reducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	IntWritable totalIW = new IntWritable();
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context)
				throws IOException, InterruptedException{
		
		int total = 0;
		for(IntWritable value:values){
			total = total + value.get();
		}
		totalIW.set(total);
		context.write(key,totalIW);
	}
			
}
