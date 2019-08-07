package pl.com.itti

import pl.com.itti.config.SparkConfig
import pl.com.itti.stream.NetFlowStreamSpark

object Main extends App {
  val spark = SparkConfig.runSparkEngine()
  NetFlowStreamSpark.getPreparedDataFromKafka(spark)
  spark.start()
  spark.awaitTermination()
}