#!/usr/bin/python3

import sys
import socket
import os

if __name__ == '__main__':

    # Build the necessary json
    #    Nebulizer id
    neb_id = os.environ.get('NEB_ID')
        
    json = "\"message_type\":\"neb_to_server_ipu\",\n\t\"nebulon_id\":%s\n}" % (neb_id)
    
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

