IMPORTANT THINGS TO BE ABLE TO GET CONNECTED

1. Units wpa_supplicant@wlan0.service, dhcpcd.service, ssh.service enabled
2. Package 'sleep', 'openssl' installed
3. Create ca_cert in the following path /etc/cert/ca.pem
   $ openssl genrsa -des3 -out ca.key 2048
     - private key pass: nebulon-private-key
   $ openssl req -x509 -new -nodes -key ca.key -sha256 -days 1825 -out ca.pem

4. Arguments for the binary add_network_bin

  - Opened network: ./add_network_bin 0 SSID
  - WPA-PSK: ./add_network_bin 1 SSID PASS
  - WPA-EAP: ./add_network_bin 2 SSID USER PASS

5. Arguments for the script connectin.sh

  - ./connectin.sh seconds 0 SSID
  - ./connectin.sh seconds 1 SSID PASS
  - ./connectin.sh seconds 2 SSID USER PASS
  
----------------------------

IMPLEMENTATION

1. Have in the raspi a static wpa.conf with the nebulon network.
   -> static_wpa_supplicant.conf
   
2. Have in the raspi a volatile wpa.conf with the nebulon network.
   -> wpa_supplicant.conf

3. Create a script that writes the general network configuration into
   wpa_supplicant.conf receiving parameters and followed by the nebulon
   network.
   -> add_network.sh

4. Call that script from a C program and compile the program
   $ run make from the ~/Workspace/Nebulon/shh directory

5. Give user ID permissions to that binary, and switch user:group to root:root

6. Run the script connectin.sh

7. Exit ssh connection (this from the app)

---------------------------

IF THE NEBULIZER GOES OUT OF INTERNET

1. Create a script that reinitilizes the wpa_supplicant.service if there is
   NOT internet connection. If there is internet connection do nothing.
   -> cron_try_again.sh

2. Create a crontab with the cron_try_again.sh