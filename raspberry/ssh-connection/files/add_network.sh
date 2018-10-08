#!/bin/bash

# Check who is running the script
if [ "$(whoami)" != "root" ]
then
    echo "SH-ERROR: You must be root to run this script!"
    exit -1
fi

# Function to overwrite the file wpa_supplicant.conf adding a new network block
function add_network {

    # Erase file content
    > /etc/wpa_supplicant/wpa_supplicant-wlan0.conf

    # Always will have this part at the beginning of the file
    echo -n -e "network={\n\tssid=" > /etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    
    if [ $# -eq 1 ] # no network security
    then 
	
	echo -n  -e "\"$1\"\n\tscan_ssid=1\n\tkey_mgmt=NONE" >> /etc/wpa_supplicant/wpa_supplicant-wlan0.conf
	
    elif [ $# -eq 2 ] # WPA-PSK (Wifi Protected Access, Pre-shared Key)
    then

	echo -n -e "\"$1\"\n\tscan_ssid=1\n\tkey_mgmt=WPA-PSK\n\tpsk=\"$2\"" >> /etc/wpa_supplicant/wpa_supplicant-wlan0.conf

    else # WPA-EAP (Wifi Protected Access, Extensible Authentication Protocol type PEAP)
	 # PEAP: Protected Extensible Authentication Protocol

	echo -n -e "\"$1\"\n\tscan_ssid=1\n\tkey_mgmt=WPA-EAP\n\teap=PEAP\n\t" >> /etc/wpa_supplicant/wpa_supplicant-wlan0.conf
	echo -n -e "identity=\"$2\"\n\tpassword=\"$3\"\n\tca_cert=/etc/cert/ca.pem" >> /etc/wpa_supplicant/wpa_supplicant-wlan0.conf
	echo -n -e "\n\tphase1=\"peaplabel=0\"\n\tphase2=\"auth=MSCHAPV2\"" >> /etc/wpa_supplicant/wpa_supplicant-wlan0.conf
	
    fi

    # Always will have this part at the end of the file
    # The more priority the more problale to select the network
    echo -e "\n\tpriority=2\n}" >> /etc/wpa_supplicant/wpa_supplicant-wlan0.conf

    # Nebulon's app network
    echo -n -e "network={\n\tssid=\"nebulon\"\n\tkey_mgmt=WPA-PSK\n\t" >> /etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    echo -e "psk=\"nebulon-hotspot\"\n\tpriority=1\n}" >> /etc/wpa_supplicant/wpa_supplicant-wlan0.conf
}

type=$1
new_ssid=$2
new_password=$3
new_user=$4

if [ $type -eq 0 ]
then
    
    add_network $new_ssid
    
elif [ $type -eq 1 ]
then
    
    add_network $new_ssid $new_password
    
elif [ $type -eq 2 ]
then
    
    add_network $new_ssid $new_user $new_password
    
else
    
    1>&2 echo "SH-ERROR: Unrecognized type of network!"
    
fi
