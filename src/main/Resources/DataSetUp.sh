( curl -s https://training.ververica.com/trainingData/nycTaxiRides.gz \
  | zcat \
  | split -l 10000 --filter="kafka/bin/kafka-console-producer.sh \
    --broker-list localhost:9092 --topic taxirides; sleep 0.2"\
  > /dev/null ) &
( curl -s https://training.ververica.com/trainingData/nycTaxiFares.gz \
  | zcat \
  | split -l 10000 --filter="kafka/bin/kafka-console-producer.sh \
    --broker-list localhost:9092 --topic taxifares; sleep 0.2" \
  > /dev/null ) &