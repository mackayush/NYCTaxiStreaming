
Steps for code

First built session 
read both the streams from kafka

define schema for both streams and parse incoming data w.r.t to schema provided

Training data avaiable at: https://training.ververica.com/setup/taxiData.html

Downloaded location of the data :/Users/ayushpandey/Stack/data

gunzip -c  nycTaxiRides.gz  | gsplit -l 10000 --filter="/Users/ayushpandey/Stack/kafka_2.12-2.3.0/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic taxirides; sleep 0.2" > /dev/null 



##### **Running jar in local mode**

`./spark-submit  --class BaseMain --master local --driver-memory 4g  --num-executors 2 --executor-memory 4g --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.0  /Users/ayushpandey/Stack/Streamtaxi/target/scala-2.11/streamtaxi_2.11-0.1.jar`
