package mapper;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.IOException;

public class VideoRating {
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, FloatWritable> {
		private Text videoName = new Text();
		private FloatWritable rating = new FloatWritable();

		//public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		public void map(LongWritable key, Text value, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
			String line = value.toString();

			if (line.length() > 0){
				String[] stringSplitArray = line.split(" ");
				videoName.set(stringSplitArray[0]);
				rating.set(Float.parseFloat(stringSplitArray[1]));

				/*
				if (stringSplitArray[6].matches("\\d+.+")){
					rating.set(Float.parseFloat(stringSplitArray[6]));
				}
				*/
			}
			output.collect(videoName, rating);
		}
	}

	//public static class Reduce extends Reducer<Text, FloatWritable, Text, FloatWritable> {
	public static class Reduce extends MapReduceBase implements Reducer<Text, FloatWritable, Text, FloatWritable> {
		//public void reduce(Text key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException{
		public void reduce(Text key, Iterator<FloatWritable> values, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
			float sum = 0;
			int i = 0;

			/*for (FloatWritable value: values) {
				++i;
				sum += value.get();
			}*/

			while (values.hasNext()){
				++i;
				sum += values.next().get();
			}

			sum = sum/i;
			//context.write(key, new FloatWritable(sum));
			output.collect(key, new FloatWritable(sum));
		}
	}

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(VideoRating.class);
		conf.setJobName("videorating");
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(FloatWritable.class);
		conf.setMapperClass(Map.class);
		conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(Reduce.class);
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		JobClient.runJob(conf);
	}
}
