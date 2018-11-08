import socket
import sys
import os
from _thread import *
import json
# Hostname -I

MAX_QUEUE = 5
def getID():
    enviroment_id = str.encode('NEB_ID')
    id_n = os.getenvb(enviroment_id)
    
    return id_n.decode('utf-8')

def threaded_getID(conn):
    print("Threaded ID")

    id_neb = getID()
    
    conn.send(str.encode(str(id_neb)))
    
    conn.close()


def threaded_setWiFi():
    pass


def threaded_client(conn):
    while True:
        data = conn.recv(2048)
        data_decode = data.decode('utf-8')

        if not data:
            break

        print(type(data_decode))
      
        data_object = json.loads(data_decode)
        message_type = data_object["message_type"]

        print(message_type)

        if(message_type == "id_request"):
                threaded_getID(conn)
                break;
        
        elif(message_type == "i_dont_know"):
                #Others
                break;
        else:
            reply = "404"

            print(reply)
            conn.sendall(str.encode(reply))
            conn.close()
            break;
        
        break;
        

        
def main():
    #Conecction Type,TCP Connection
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    host = '0.0.0.0'
    port = 5555

    print("Server starting...")

    try:
        s.bind((host,port))
    except socket.error as e:
        print(str(e))
        print("There is a problem")
        
    s.listen(MAX_QUEUE)
    print("Maximun queue ->" + str(MAX_QUEUE))
    
    while True:
        print("Server listening...")
        conn, addr = s.accept()
        print('Connected to: ' + addr[0] + ":"+ str(addr[1]))
        start_new_thread(threaded_client, (conn,))

#print("Hola?")
main()
