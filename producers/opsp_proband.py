import mysql.connector
from producer import sendData, prepareDataForLink
import time
from get_db import read_db_config

db_config = read_db_config()

while True:
  mydb = None
  try:
        print('Connecting to MySQL database...')
        mydb = mysql.connector.connect(**db_config)

        if mydb.is_connected():
            print('Connection established.')
        else:
            print('Connection failed.')

  except Error as error:
        print(error)
        
  mycursor = mydb.cursor()
  mycursor.execute("SELECT * FROM idmanagement_proband")

  myresult = mycursor.fetchall()
  for x in myresult:
    if all(x):
      data = prepareDataForLink(x[0], "CELSPACAdmin", "proband",  x[1], "OpenSpecimen", "proband")
      if (sendData('proband-proband', data) == True):
        query = "DELETE FROM idmanagement_proband WHERE opsp_proband = %s ;"
        mycursor.execute(query, (x[1],))
        mydb.commit()
        print("deleted")
      else:
        print("Not successfuly deleted" + x[0])

  mydb.close()
  time.sleep(30)

    
    
