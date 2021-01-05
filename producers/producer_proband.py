from time import sleep
from json import dumps
from kafka import KafkaProducer

def sendData(topic, data):
  producer = KafkaProducer(bootstrap_servers=['78.128.251.219:9092'],
                         value_serializer=lambda x: 
                         dumps(x).encode('utf-8'))
  try: 
    producer.send(topic, value=data)
  except Exception as e:
    return False
  return True


def prepareDataForLink(registeredIdLeft, systemLeft, typeLeft, registeredIdRight, systemRight, typeRight, createdBy):
  linkType = systemLeft + "." + typeLeft + "-" + systemRight + "." + typeRight
  data = {
        "entityLeft": {
        "registeredId": registeredIdLeft,
        "type": typeLeft,
        "system": systemLeft
        },
        "entityRight": {
        "registeredId": registeredIdRight,
        "type": typeRight,
        "system": systemRight
        },
        "linkArgs": {
            "oriented": "true",
            "type": linkType
        },
        "userPropertyWho": {
            "key": "createdBy",
            "value": createdBy
        },
        "userPropertyWhen": {
            "key": "createdAt",
            "value": "whencreated"
        }
      }
  return data
