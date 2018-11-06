### Raspbian Working Version

- Raspbian GNU/Linux 9 (Stretch)
- Image: <date>-rasbian-stretch-lite.img. It has the same functionalities as the desktop image, but without the desktop environment.

-------------------------------

### Install Raspbian in SD card

1. Download the image from: https://www.raspberrypi.org/downloads/raspbian/
2. Unzip the file.
3. Run the following command as ROOT:
``` # dd bs=4M if=<date>-raspbian-stretch-lite.img of=/dev/mmcblk0 status=progress conv=fsync ```. Wehre /dev/mmcblk0 is the device file of the SD card; you can check it with ``` $ lsblk ``` or ``` # fisk -l ```. IMPORTANT: Don't chose an SD card's partition, choose the SD card itself.
4. Insert the SD card in the Raspberry. IMPORTANT: You MUST use +5V to get the Raspberry working OK!.
5. To configure TIME, KEYBOARD, LANGUAGE, and other things run in the Rasperry: ``` $ sudo raspi-config ```
   5.1 You might have to enable ssh daemon.
6. Connect it to the internet and run ``` $ sudo apt-get update ``` and ``` $ sudo apt-get upgrade ```
7. It should work perfectly right now.

-------------------------------------------

## Important!

* Set time zone to properly use cron
  ``` $ sudo ln -sf /usr/share/America/Bogota /etc/localtime ```
* Run the command to update to the lastest firmware
  ``` $ sudo rpi-update ```
* Download the repository: https://github.com/JuanGQCadavid/Nebulon.git

--------------------------------------------

## Errors Registered

* Under voltage due to bad power supply.
* Internal error: Oops: 17 [#1] SMP ARM.

  - This error seems to be due to high WiFi traffic.
  - Try to use a wried connection to make updates and stuff.