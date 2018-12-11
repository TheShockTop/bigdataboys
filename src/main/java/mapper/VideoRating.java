package mapper;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class VideoRating {
	public static class Map extends Mapper<LongWritable, Text, Text, FloatWritable> {
		private Text videoName = new Text();
		private FloatWritable rating = new FloatWritable();

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();

			if (line.length() > 0){
				String[] stringSplitArray = line.split("\t");
				videoName.set(stringSplitArray[0]);
				if (stringSplitArray[6].matches("\\d+.+")){
					rating.set(Float.parseFloat(stringSplitArray[6]));
				}
			}

			context.write(videoName, rating);
		}
	}

	public static class Reduce extends Reducer<Text, FloatWritable, Text, FloatWritable> {
		public void reduce(Text key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException{
			float sum = 0;
			int i = 0;

			for (FloatWritable value: values) {
				++i;
				sum += value.get();
			}

			sum = sum/i;
			context.write(key, new FloatWritable(sum));
		}
	}
}
