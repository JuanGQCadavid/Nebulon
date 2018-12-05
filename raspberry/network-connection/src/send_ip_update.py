#!/usr/bin/python3

import sys
import socket
import os

def get_ip_address():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.connect(("8.8.8.8", 80))
    ip = s.getsockname()[0]
    s.close()
    return ip

if __name__ == '__main__':

    # Build the necessary json
    #    Nebulizer id
    neb_id = os.environ.get('NEB_ID')
    ip = get_ip_address()
        
    json = "\"message_type\":\"neb_to_server_ipu\",\n\t\"nebulon_id\":%s,\n\t\"nebulon_ip_address\":\"%s\"\n}" % (neb_id, ip)
    
    message_size = len(json) + 21
    
    json_aux = ("{\n\t\"message_size\":%d,\n\t" % (message_size + len(str(message_size))))
    
    json = json_aux + json
    
    # Send the JSON
    try:

        remote_server = os.environ.get('REMOTE_SERVER')
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.connect((str(remote_server), 17777))
        sock.send(str.encode(json))
        sock.close()
        
    except:
        
        print("socket: Error trying to send the json to the remote server", file=sys.stderr)

