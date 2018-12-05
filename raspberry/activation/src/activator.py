import RPi.GPIO as GPIO
import time
import sys
import signal

# Signals Handling
# This helps to avoid the circuit running even after Ctrl-c pressed
def signal_handler(sig, frame):
    import shutdown
    print('Ctrl-C pressed')
    sys.exit(0)

signal.signal(signal.SIGINT, signal_handler)

# arguments
args = sys.argv

# every time is in hours, lets pass to seconds
t_alive = float(args[1]) * 60 * 60
t_work = float ((args[2]).split(":")[0]) * 60 * 60 + float ((args[2]).split(":")[1]) * 60
t_sleep =float ((args[3]).split(":")[0]) * 60 * 60 + float ((args[3]).split(":")[1]) * 60
fragancy = args[4]

print(str(t_work) + " - " + str(t_sleep))

t_start = time.time()

t_running = 0
t_working = 0

#Pin setup

pin_general = 7
pin_lock_left = 11
pin_lock_rigth = 13

GPIO.setmode(GPIO.BOARD)

GPIO.setup(pin_general,GPIO.OUT)
GPIO.setup(pin_lock_left,GPIO.OUT)
GPIO.setup(pin_lock_rigth,GPIO.OUT)


while  t_running < t_alive:
    
    #Time Working
    t_working = 0
    t_start_working = time.time()

    while t_working < t_work:
        GPIO.output(pin_general, True)
        
        if fragancy == "0":
            
            GPIO.output(pin_lock_left, True)
            GPIO.output(pin_lock_rigth, False)
        else:
            GPIO.output(pin_lock_rigth, True)
            GPIO.output(pin_lock_left, False)            

        t_working =time.time() -  t_start_working

            
    GPIO.output(pin_general, False)
    GPIO.output(pin_lock_left, False)
    GPIO.output(pin_lock_rigth, False)
    
    time.sleep(t_sleep)
            
    t_running = (time.time() - t_start)

import shutdown
    
