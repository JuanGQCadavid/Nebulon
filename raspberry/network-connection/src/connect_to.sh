#!/bin/bash

# $1 -> Type of network
# $2 -> SSID
# $3 -> USER or PASS
# $4 -> PASS

# Add the network to wpa_supplicant
if [ $1 -eq 0 ]
then
    
    /home/pi/workspace/Nebulon/raspberry/network-connection/src/add_network_bin $1 "$2"
    
elif [ $1 -eq 1 ]
then
    
    /home/pi/workspace/Nebulon/raspberry/network-connection/src/add_network_bin $1 "$2" "$3"
    
elif [ $1 -eq 2 ]
then
    
    /home/pi/workspace/Nebulon/raspberry/network-connection/src/add_network_bin $1 "$2" "$3" "$4"    
    
fi

# Restart units
sudo systemctl restart wpa_supplicant@wlan0.service
sudo systemctl restart dhcpcd.service

# Send ip to remote server
python3 /home/pi/workspace/Nebulon/raspberry/network-connection/src/send_ip_update.py
