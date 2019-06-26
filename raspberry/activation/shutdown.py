################################################################################
# Properly stops the raspberry.
# Author: Juan Gonzalo Quiroz Cadavid.
# Modified date: 19/06/2019.
################################################################################

import RPi.GPIO as GPIO
import time
import sys

def stop():
    pin_general = 7
    pin_lock_left = 11
    pin_lock_rigth = 13

    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(pin_general,GPIO.OUT)
    GPIO.setup(pin_lock_left,GPIO.OUT)
    GPIO.setup(pin_lock_rigth,GPIO.OUT)
    GPIO.output(pin_lock_rigth, False)
    GPIO.output(pin_lock_left, False)
    GPIO.output(pin_general, False)
    GPIO.cleanup()
