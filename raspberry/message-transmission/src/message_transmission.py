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

    liquid_level = generateRandom()

    # Build the necessary json

    # Nebulizer id
    neb_id = os.environ.get('NEB_ID')
    
    json = "\"message_type\":\"neb_to_server_llu\",\n\t\"nebulon_id\":%s,\n\t\"nebulon_liquid_level\":%d\n}" % (neb_id, liquid_level)

    print('Json len: %d' % len(json))

    message_size = len(json) + 21
    
    json_aux = ("{\n\t\"message_size\":%d,\n\t" % (message_size + len(str(message_size))))

    json = json_aux + json

    print('Json len: %d' % len(json))

    # Send the JSON
    socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    socket.connect(("localhost", 17777))
    socket.send(str.encode(json))
    socket.close()
    
