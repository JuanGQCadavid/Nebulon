#!/bin/bash

sudo cp /etc/wpa_supplicant/static_wpa_supplicant.conf /etc/wpa_supplicant-wlan0.conf
sudo systemctl restart wpa_supplicant@wlan0.service
sudo systemctl restart dhcpcd.service
