from time import sleep
from json import dumps
from kafka import KafkaProducer

def sendData(topic, data):
  producer = KafkaProducer(bootstrap_servers=['78.128.251.219:9092'],
                         value_serializer=lambda x: 
                         dumps(x).encode('utf-8'))
  producer.send(topic, value=data)
