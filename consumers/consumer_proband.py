from kafka import KafkaConsumer
from json import loads
import json
import requests as req

API_ENDPOINT_OBJECTS = "http://127.0.0.1:8000/create-link/"
API_ENDPOINT_LINKS = "http://localhost:8080/links/"

root_logger= logging.getLogger()
root_logger.setLevel(logging.ERROR)
handler = logging.FileHandler('consumer_specimen.log', 'w', 'utf-8')
handler.setFormatter(logging.Formatter('%(name)s %(message)s'))
root_logger.addHandler(handler)

consumer = KafkaConsumer(
    'proband-proband',
    group_id='console-consumer-6420',
     bootstrap_servers=['78.128.251.219:9092'],
     auto_offset_reset='earliest',
     enable_auto_commit=False,
     value_deserializer=lambda x: loads(x.decode('utf-8')))

for msg in consumer:
    r = req.post(url = API_ENDPOINT_OBJECTS, data=json.dumps(msg.value))
    if (r.status_code = 410):
        logging.error("ERROR: Validation failed. Message:" + msg)
        consumer.commit()
    elif (r.status_code = 415):
        logging.error("ERROR: Entity does not exist. Message:" + msg)
        consumer.commit()
    elif (r.status_code = 201):
        consumer.commit()
    else:
        logging.error("ERROR: Error occured. Response message:" + r)
