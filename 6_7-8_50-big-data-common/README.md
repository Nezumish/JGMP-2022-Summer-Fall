#Topics 6&7: Big Data
##Application Info
Trying some Big Data stuff

There are 3 application modules: 
1. `6_7-8_50-big-data-hadoop` with Hadoop application and examples screens
2. `6_7-8_50-big-data-kafka` with Kafka application
3. `6_7-8_50-big-data-spark` with Spark applications

##1 and 2 tasks
I tried different ways of Hadoop installation: Linux, Windows and Docker. The last one was the most convinient for me.\
To create a cluster I've replicated data_node containers up to 4 and linked them to the name_node one.

The following commands are to get into the name_node container and launch the Pi example:

      docker exec -it namenode /bin/bash
      cd /opt/hadoop-3.2.1
      /opt/hadoop-3.2.1/share/hadoop/mapreduce# hadoop jar hadoop-mapreduce-examples-3.2.1.jar pi 1 10

The screenshots of work are in `6_7-8_50-big-data-hadoop` resources folders task1 and task2 accordingly

##3 task
The 3rd task is in `6_7-8_50-big-data-hadoop` module. Use the commands to get into the name_node Docker container 
and launch the prebuilded WordCount job (don't forget to copy the jar here too):

      exec -it namenode /bin/bash
      cd /opt/hadoop-3.2.1
      hadoop jar 6_7-8_50-big-data-2.7.1.jar com.rntgroup.big.data.task3.WordCountApplication 
      --data <input-text-path>
      --filter <filter-words-list>
      --output <output-result-path>

The screenshots of work are in the same module resources.\
I couldn't use MRUnit because there are older versions are available than I need.\
Some nice articles described the Hadoop testing process as launching it in standalone mode with regular tests.

##4 task
This time it was better on my local machine which couldn't bear another nodes cluster:

      <spark-path> spark-submit 
         --class com.rntgroup.big.data.task4.SparkWordCountApplication 
         --master local <project-path>\6_7-8_50-big-data-spark\target\6_7-8_50-big-data-spark-1.0-SNAPSHOT-jackofall.jar 
         --spring.profiles.active=<one of: rdd, df, sql>
         --data <input-text-path>
         --filter <filter-words-list>
         --output <output-result-path>

Yep, I needed to show up and try Spark with Spring. There are three profiles for each WordCount solution option: 
RDD ("rdd" profile), DataFrame/DataSet ("df" profile), SparkSQL ("sql" profile).

**I used the same hamlet.txt file, but it seems to be corrupted, because even trusted tokenizers couldn't remove those broken whitespaces.
So, whitespace - is the most popular word in this poem :>** 

##5 task
I've made it more simple because using Spring seems inconvenient for this task. There is a command to launch it:

      <spark-path> spark-submit 
         --class com.rntgroup.big.data.task5.SparkSmsApplication 
         --master local <path-to-project>\JGMP-2022-Summer-Fall\6_7-8_50-big-data-spark\target\6_7-8_50-big-data-spark-1.0-SNAPSHOT-jackofall.jar 
         <path-to-attached-file-in-the-task>\smsData.txt

**ATTENTION**\
ALL SPARK TASKS COMPLETED WITH SOME OWN EXCEPTIONS, BUT IT WORKS AS EXPECTED SO I IGNORED IT

##6 task
Docker my Docker my Docker <3
Ahem.
I've used this my most favorite tool to create a simple Kafka eco:

Start Zookeeper:

      docker run -d --name=zookeeper 
            -e ZOOKEEPER_CLIENT_PORT=2181 
            -e ZOOKEEPER_TICK_TIME=2000 
            -p 2181:2181 
            confluentinc/cp-zookeeper

And Kafka:

      docker run -d --name=kafka 
            -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 
            -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 
            -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 
            -p 9092:9092 
            confluentinc/cp-kafka

Enter the Kafka container and execute the following commands to create a topic manually:

      docker exec -it kafka bash
      /bin/kafka-topics 
         --create --topic secret-messages 
         --replication-factor 1 
         --partitions 3 
         --bootstrap-server kafka:9092

There are simple Consumer and Producer in `6_7-8_50-big-data-kafka` module implemented via Spring Kafka Support framework.\
The task required 3 partitions to be created, so the topic can be processed only by 3 consumers simultaneously.\


##7 task
I used Docker again to install the required tool:

      docker pull yandex/clickhouse-server
      docker run -d -p 8123:8123 
         -p 9000:9000 
         -e CLICKHOUSE_DB=my_database 
         -e CLICKHOUSE_USER=username 
         -e CLICKHOUSE_DEFAULT_ACCESS_MANAGEMENT=1 
         -e CLICKHOUSE_PASSWORD=password 
         --name some-clickhouse-server 
         --ulimit nofile=262144:262144 
         yandex/clickhouse-server

Then I connected to it via DBeaver and uploaded the suggested data file from task.\
The queries:

1. Number of events per day:


      select date, count()\
      from ads_data\
      group by date\
      order by date asc\


2. Count of each ad displayed:

The condition is not really clear for me. \
The first interpretation: count all unique viewed ads:


      select uniqExactIf(ad_id, event = 'view')
      from ads_data

The second interpretation: get times each app displayed:


      select ad_id, countIf(event = 'view') 
      from ads_data
      group by ad_id

3. Number of clicks:


      select countIf(event='click')
      from ads_data

4. Number of unique ads and unique campaigns:


      select campaign_union_id, uniqExact(ad_id)
      from ads_data
      group by campaign_union_id