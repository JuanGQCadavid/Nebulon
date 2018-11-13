## Connect to an specified Network
### Important things to have in count

1. Units wpa_supplicant@wlan0.service, dhcpcd.service, ssh.service enabled
     
2. Package 'openssl' installed

3. Create ca_cert in the following path /etc/cert/ca.pem. NOTE: Give 755 permissions to the directory
   ``` $ openssl genrsa -des3 -out ca.key 2048 ``` 
   3.1 private key pass: nebulon-private-key
   ``` $ openssl req -x509 -new -nodes -key ca.key -sha256 -days 1825 -out ca.pem ```

3. Give execution permissions to add_network.sh

4. Arguments for the binary add_network_bin

  - Opened network: ``` ./add_network_bin 0 SSID ```
  - WPA-PSK: ``` ./add_network_bin 1 SSID PASS ```
  - WPA-EAP: ``` ./add_network_bin 2 SSID USER PASS ```

5. Arguments for the script connect_to.sh

  - ``` ./connect_to.sh 0 SSID ```
  - ``` ./connect_to.sh 1 SSID PASS ```
  - ``` ./connect_to.sh 2 SSID USER PASS ```
  
----------------------------

## Send IP address update

1. Add to your ``` .bashrc ``` the following lines:
   ``` export NEB_ID=## ```
   ``` export REMOTE_SERVER="nebulon-enterprises.eastus.cloudapp.azure.com" ```

2. Install ``` cron ``` package and enable the service
   ``` $ sudo systemctl enable cron ```
   ``` $ sudo systemctl start cron ```

3. Inside ``` /etc/cron.hourly/ ``` create an script with the following inside
   ``` /home/pi/workspace/Nebulon/raspberry/network-connection/src/send_ip_update.py ```
   
---------------------------

### IF THE NEBULIZER GOES OUT OF INTERNET

1. Create a script that reinitilizes the wpa_supplicant@wlan0.service if there is
   NOT internet connection. If there is internet connection do nothing.
   -> cron_try_again.sh

2. Create a crontab with the cron_try_again.sh
