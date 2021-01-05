import findspark
findspark.init()
import pyspark as ps
from confluent_kafka.schema_registry import SchemaRegistryClient
from confluent_kafka.schema_registry.avro import AvroDeserializer
from confluent_kafka.serialization import SerializationContext
from pyspark.sql.functions import udf, array
from pyspark.sql.types import DoubleType, StringType, ArrayType
from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider


windowSize = 5
slideSize = 3
windowDuration = '{} seconds'.format(windowSize)
slideDuration = '{} seconds'.format(slideSize)


def getSparkInstance():
    """
    @return: Return Spark session
    """
    spark = ps.sql.SparkSession.builder \
        .master("local[4]") \
        .appName("individual") \
        .getOrCreate()
    return spark


spark = getSparkInstance()


def process_row(serialized_data):
    schema = '''
    {
    "namespace": "org.mddarr.rides.event.dto",
     "type": "record",
     "name": "AvroRideCoordinate",
     "fields": [
         {"name": "timestamp", "type": "long"},
         {"name": "latitude", "type": "double"},
         {"name": "longitude", "type": "double"}
     ]
    }
    '''
    schemaRegistryClient = SchemaRegistryClient({"url": "http://localhost:8081"})
    avroDeserializer = AvroDeserializer(schema, schemaRegistryClient)
    serializationContext = SerializationContext("coordinates", schema)
    deserialized_row = avroDeserializer(serialized_data, serializationContext)
    print("THE DESERIALIZED ROW LOOKS LIKE " + str(deserialized_row))

    # return deserialized_row['value']
    return [deserialized_row['latitude'], deserialized_row['longitude']]

streamingDF = spark \
    .readStream \
    .format("kafka") \
    .option("kafka.bootstrap.servers", "localhost:9092") \
    .option("subscribe", "coordinates") \
    .option('includeTimestamp', 'true') \
    .load()

deserialize_row_udf = udf(lambda x: process_row(x), ArrayType(DoubleType()))
#
deserialized_value_dataframe = streamingDF.withColumn('deserialized_value', deserialize_row_udf("value"))
deserialized_value_dataframe = deserialized_value_dataframe.select(['key','timestamp','deserialized_value'])
deserialized_value_dataframe = deserialized_value_dataframe.withColumnRenamed('deserialized_value', 'value')


def insert_coordinate_data_cassandra(row):

    print("THE ROW LOOKS LIKE " + str(row))

    key = row['key'].decode('utf-8')
    print("THE KEY LOOKS LIKE " + key)
    # print("THE KEY LOOKS LIKE " + )

    # insert_time_series_data_point = """INSERT INTO coordinates(rideid, time, latitude, longitude) VALUES(%s,%s,%s, %s);"""
    # dbsession = initialize_cassanrdra_session()
    #
    # try:
    #     dbsession.set_keyspace('ks1')
    #     dbsession.execute(insert_time_series_data_point, [row['rideid'], row['time'], row['latitude'], row['longitude']])
    # except Exception as e:
    #     print(e)


def initialize_cassanrdra_session():
    auth_provider = PlainTextAuthProvider(username='cassandra', password='cassandra')
    try:
        cluster = Cluster(["127.0.0.1"], auth_provider=auth_provider)
        session = cluster.connect()
        return session
    except Exception as e:
        print(e)
        return None


ds = deserialized_value_dataframe \
    .writeStream \
    .format("console") \
    .foreach(insert_coordinate_data_cassandra) \
    .trigger(processingTime="5 seconds") \
    .start()

# ds = deserialized_value_dataframe \
#     .selectExpr("value", "CAST(key AS STRING)", "timestamp") \
#     .writeStream \
#     .foreach(process_row) \
#     .trigger(processingTime="5 seconds") \
#     .start()

spark.streams.awaitAnyTermination()
