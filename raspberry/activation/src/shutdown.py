import RPi.GPIO as GPIO
import time
import sys



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
