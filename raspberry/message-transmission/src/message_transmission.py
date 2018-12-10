import sys
import socket
import os

import Adafruit_ADS1x15

def get_liquid_level():
    adc = Adafruit_ADS1x15.ADS1015()
    GAIN = 1
    level = adc.read_adc(0, gain=GAIN)

    if level < 640:
        return 0
    elif level >= 640 and level < 660:
        return 5
    elif level >= 670 and level < 700:
        return 10
    elif level >= 810 and level < 830:
        return 25
    elif level >= 840:
        return 50

if __name__ == '__main__':

    # WARNING: cron does not recognize the environment variables defined in .bashrc

    # Build the necessary json
    #    Nebulizer id
    # neb_id = os.environ.get('NEB_ID')
    neb_id = "1"
    liquid_level = str(get_liquid_level())
        
    json = "\"message_type\":\"neb_to_server_llu\",\n\t\"nebulon_id\":%s,\n\t\"fragrance\":1,\n\t\"liquid_level\":%s\n}" % (neb_id, liquid_level)
    
    message_size = len(json) + 21
    
    json_aux = ("{\n\t\"message_size\":%d,\n\t" % (message_size + len(str(message_size))))
    
    json = json_aux + json
    
    # Send the JSON
    try:

        # remote_server = os.environ.get('REMOTE_SERVER')
        remote_server = "nebulon-enterprises.eastus.cloudapp.azure.com"
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.connect((remote_server, 5001))
        sock.send(str.encode(json))
        sock.close()
        
    except:
        os.makedirs('/home/pi/error/')
        print("socket: Error trying to send the json to the remote server", file=sys.stderr)
