
import org.apache.spark.sql.SparkSession.builder
import org.apache.spark.sql.types.{FloatType, LongType, ShortType, StringType, StructType, TimestampType}
import utils.ParseKafkaMessage

object BaseMain extends App {

  val spark = builder
    .appName("Taxi-Trip-Analysis")
    .master("local")
    .getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")

  val sdfRides = spark.readStream
  .format("kafka")
  .option("kafka.bootstrap.servers", "localhost:9092")
  .option("subscribe", "taxirides")
  .option("startingOffsets", "latest")
  .load().selectExpr("CAST(value AS STRING)")

  val sdfFares = spark.readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("subscribe", "taxifares")
    .option("startingOffsets", "latest")
    .load()
    .selectExpr("CAST(value AS STRING)")

  val taxiFaresSchema = new StructType().add("rideId", LongType, false)
    .add("taxiId",LongType)
    .add("driverId", LongType)
    .add("startTime",TimestampType)
    .add("paymentType",StringType)
    .add("tip", FloatType)
    .add("tolls",FloatType)
    .add("totalFare",FloatType)

  val taxiRidesSchema = new StructType().add("rideId", LongType, false)
    .add("isStart",StringType)
    .add("endTime", TimestampType)
    .add("startTime",TimestampType)
    .add("startLon",FloatType)
    .add("startLat", FloatType)
    .add("endLon",FloatType)
    .add("endLat",FloatType)
    .add("passengerCnt", ShortType)
    .add("taxiId",LongType)
    .add("driverId",LongType)

  val sdfRidesMapped = ParseKafkaMessage.parseDataFromKafkaMessage(sdfRides, taxiRidesSchema)
  val sdfFaresMapped = ParseKafkaMessage.parseDataFromKafkaMessage(sdfFares, taxiFaresSchema)
  val query = sdfRidesMapped.groupBy("driverId").count()

  query.writeStream
  .outputMode("complete")
  .format("console")
  .option("truncate", false)
  .start()
  .awaitTermination()

}
