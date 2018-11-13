#!/bin/python3

import sys # argv, stderr, exit()
import os # os.environ.get
import socket

# Instead of this will go the liquid level sensor information
# Temporary libraries
import random
import time
# ----------------

def generateRandom():
    return random.randint(1, 500)

if __name__ == '__main__':

    try:

        liquid_level = generateRandom()

    except:

        print("Error getting liquid level data", file=sys.stderr)
        exit()

    # Build the necessary json
    try:
        #    Nebulizer id
        neb_id = os.environ.get('NEB_ID')
        
    except:
        
        print("Error trying to get the environment variable NEB_ID", file=sys.stderr)

        
    json = "\"message_type\":\"neb_to_server_llu\",\n\t\"nebulon_id\":%s,\n\t\"nebulon_liquid_level\":%d\n}" % (neb_id, liquid_level)

    message_size = len(json) + 21
    
    json_aux = ("{\n\t\"message_size\":%d,\n\t" % (message_size + len(str(message_size))))

    json = json_aux + json

    # Send the JSON
    try:
        remote_server = os.environ.get('REMOTE_SERVER')
        socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        socket.connect((remote_server, 17777))
        socket.send(str.encode(json))
        socket.close()
    except:
        print("socket: Error trying to send the json to the remote server", file=sys.stderr)
