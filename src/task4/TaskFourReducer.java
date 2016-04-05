package task4;

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

public class TaskFourReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	
	// Initialize Variables
	IntWritable totalIW = new IntWritable();
    private Map<Text, IntWritable> sortRecordMap = new HashMap<>();
    
    
    // Count total of each airlines positive tweets
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values,
            Reducer<Text,IntWritable,Text,IntWritable>.Context context)
    throws IOException,InterruptedException{
       
        int total = 0;
        for(IntWritable value : values){
            total +=value.get();
        }
   
       
        totalIW.set(total);
        
        
        int newest = totalIW.get();    
        sortRecordMap.put(new Text(key),new IntWritable(newest));
   
    }
    
    // Hadoop cleanup method
    // This method will be called once at the end of the program 
    // 	to clean up the results and only print out the top 3 results
    @Override
    protected void cleanup(Context context)throws IOException,InterruptedException{
        Map<Text,IntWritable> sortedMap = sortValue(sortRecordMap);
        int counter = 0;
        for(Text key: sortedMap.keySet()){
            if(counter++ == 3){
                break;
            }
           
            context.write(key, sortedMap.get(key));
        }
    }
   
    private static <K,V extends Comparable<? super V>> Map<K,V> sortValue(Map<K,V> map){
        
    	List<Map.Entry<K,V>> queries = new LinkedList<Map.Entry<K,V>>(map.entrySet());
        Collections.sort(queries, new Comparator<Map.Entry<K,V>>(){
            @Override
            public int compare(Map.Entry<K, V> v1 ,Map.Entry<K,V> v2){
                return (v2.getValue()).compareTo(v1.getValue());
            }
        });
       
        Map<K, V> sortedMap = (Map<K, V>) new LinkedHashMap<K,V>();
       
        for(Map.Entry<K, V> query : queries){
            sortedMap.put(query.getKey(),query.getValue());
        }
       
        return sortedMap;
    }

}
