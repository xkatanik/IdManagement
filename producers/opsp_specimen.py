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
  mycursor.execute("SELECT * FROM idmanagement_specimen")

  myresult = mycursor.fetchall()

  print(myresult)
  for x in myresult:
    data = ""
    if x[3] == "New":
        query = "SELECT collection_protocol_reg_id FROM catissue_specimen_coll_group WHERE identifier = %s"
        mycursor.execute(query, (x[1],))
        reg_id = mycursor.fetchall()
        query = "SELECT participant_id FROM catissue_coll_prot_reg WHERE identifier = %s"
        mycursor.execute(query, (reg_id[0][0],))
        participant = mycursor.fetchall()
        data = prepareDataForLink(participant[0][0], "OpenSpecimen", "proband", x[0],"OpenSpecimen","specimen")
    
    elif x[3] == "Derived":
      query = "SELECT barcode FROM catissue_specimen WHERE identifier = %s"
      mycursor.execute(query, (x[2],))
      registeredIdLeft = mycursor.fetchall()
      data = prepareDataForLink(registeredIdLeft[0][0],"OpenSpecimen","specimen",x[0],"OpenSpecimen","derivative")
    elif x[3] == "Aliquot":
        query = "SELECT lineage FROM catissue_specimen WHERE identifier = %s"
        mycursor.execute(query, (x[2],))
        lineage = mycursor.fetchall()
        if (lineage[0][0] == "New"):
          lineage_type = "specimen"
        elif (lineage[0][0] == "Derived"):
          lineage_type = "derivative"
        elif (lineage[0][0] == "Aliquot"):
          lineage_type = "aliquot"
        else:
          lineage = ""

        query = "SELECT barcode FROM catissue_specimen WHERE identifier = %s"
        mycursor.execute(query, (x[2],))
        registeredIdLeft = mycursor.fetchall()
        data = prepareDataForLink(registeredIdLeft[0][0],"OpenSpecimen",lineage_type,x[0],"OpenSpecimen","aliquot")
    else:

    
    if (sendData('specimen', data) == True):
        query = "DELETE FROM idmanagement_specimen WHERE barcode = %s ;"
        mycursor.execute(query, (x[0],))
        mydb.commit()
        print("deleted")
    else:
        print("Not successfuly deleted" + x[0])

  mydb.close()
  time.sleep(30)
  

    
    
