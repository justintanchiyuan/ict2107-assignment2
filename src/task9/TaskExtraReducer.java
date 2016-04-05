package task9;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class TaskExtraReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	IntWritable totalIW = new IntWritable();
	private Map<Text, IntWritable> sortmap = new HashMap<>();

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		int count = 0;
		for (IntWritable value : values) {
			count += value.get();
		}

		totalIW.set(count);
		// context.write(key, totalIW);

		int update = totalIW.get();
		sortmap.put(new Text(key), new IntWritable(update));
	}

	// cleanup will run after the reducer task is completed
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {

		Map<Text, IntWritable> sortedmap = sortByValues(sortmap);
		int counter = 0;
		for (Text key : sortedmap.keySet()) {

			// to keep track of the for loop, exit when it reaches 6
			if (counter++ == 3) {
				break;
			}

			// write the first 3 key and store it into context
			context.write(key, sortedmap.get(key));
		}
	}

	private static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(Map<K, V> map) {
		// store the hashmap into a collection list, so it can be sorted
		List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		// sort the collection list
		Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		// save it back to a hashmap object
		Map<K, V> sortedMap = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : entries) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
