package task8;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Task8Reducer extends Reducer<Text, Text, Text, Text> {
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
		int[] emotionCount = new int[7];
		
		for (Text value: values) {
			String emotion = value.toString();
			if(emotion.equals("Super Happy"))
				emotionCount[0] ++;
			else if(emotion.equals("Very Happy"))
				emotionCount[1] ++;
			else if(emotion.equals("Quite Happy"))
				emotionCount[2] ++;
			else if(emotion.equals("Neutral"))
				emotionCount[3] ++;
			else if(emotion.equals("Quite Angry"))
				emotionCount[4] ++;
			else if(emotion.equals("Very Angry"))
				emotionCount[5] ++;
			else if(emotion.equals("Super Angry"))
				emotionCount[6] ++;
		}

		context.write(new Text("Airline:" + key), new Text(
				"\nSuper Happy:" + emotionCount[0] + "\n" +
				"Very Happy:" + emotionCount[1] + "\n" +
				"Quite Happy:" + emotionCount[2] + "\n" +
				"Neutral:" + emotionCount[3] + "\n" +
				"Quite Angry:" + emotionCount[4] + "\n" +
				"Very Angry:" + emotionCount[5] + "\n" +
				"Super Angry:" + emotionCount[6] + "\n"));
	}
}