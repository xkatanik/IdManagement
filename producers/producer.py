from time import sleep
from json import dumps
from kafka import KafkaProducer

def sendData(topic, data):
  producer = KafkaProducer(bootstrap_servers=['78.128.251.94:9092'],
                         value_serializer=lambda x: 
                         dumps(x).encode('utf-8'),
                        security_protocol='SASL_SSL',
                         sasl_mechanism='PLAIN',
                         sasl_plain_username='alice',
                         sasl_plain_password='alice-secret',
                         ssl_check_hostname=True,
                         ssl_cafile='cacert.pem',)
  try: 
    producer.send(topic, value=data)
  except Exception as e:
    return False
  return True


def prepareDataForLink(registeredIdLeft, systemLeft, typeLeft, registeredIdRight, systemRight, typeRight):
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
        }
      }
  return data
