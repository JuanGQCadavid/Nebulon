import socket
import sys
import os
from _thread import *
# Hostname -I

MAX_QUEUE = 5
def getID():
    id_n = os.getenvb("NEB_ID")
    print(id_n)
    
def threaded_client(conn):
    conn.send(str.encode('Welcome, Type your info \n'))

    while True:
        data = conn.recv(2048)
        reply = 'Server output: ' + data.decode('utf-8')

        if not data:
            break

        conn.sendall(str.encode(reply))
    conn.close()

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
