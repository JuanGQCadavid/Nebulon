#!/bin/bash

# $1 -> Seconds to wait
# $2 -> Type of network
# $3 -> SSID
# $4 -> USER or PASS
# $5 -> PASS

# Wait for $1 seconds
sleep $1

# Add the network to wpa_supplicant
if [ $2 -eq 0 ]
then
    
    ./add_network_bin $2 $3
    
elif [ $2 -eq 1 ]
then
    
    ./add_network_bin $2 $3 $4
    
elif [ $type -eq 2 ]
then
    
    ./add_network_bin $2 $3 $4 $5    
    
fi

# Restart units
sudo systemctl restart wpa_supplicant@wlan0.service
sudo systemctl restart dhcpcd.service

# Close phone ssh connection
# sudo systemctl stop ssh.service
# sudo systemctl start ssh.service
