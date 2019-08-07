package pl.com.itti.stream

import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import pl.com.itti.config.KafkaConfig
import pl.com.itti.model.NetFlowProcessed


object NetFlowStreamSpark {

  private final val configuration = ConfigFactory.load("application")

  def getPreparedDataFromKafka(spark: StreamingContext): Unit = {
    createStreamNetFlowRawData(Array( configuration.getString("data.topic.processed")), spark)
      .foreachRDD(RDDS => {
        RDDS.foreach(it =>println(it.value().toString))
      })
  }


  private def createStreamNetFlowRawData(kafkaTopics: Array[String], spark: StreamingContext): InputDStream[ConsumerRecord[String, NetFlowProcessed]] = {
    KafkaUtils.createDirectStream[String, NetFlowProcessed](
      spark,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[
        String,
        NetFlowProcessed](kafkaTopics, KafkaConfig.propertiesMap)
    )
  }

}
