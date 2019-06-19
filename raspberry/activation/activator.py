################################################################################
# Activates the specified fragrance switching between working and sleeping
# states.
# Authors:
#   - Juan Gonzalo Quiroz Cadavid.
#   - Hamilton Tobon Mosquera.
# Modified date: 19/06/2019
#
############
#
# Receives as arguments:
# 1: Time in hours that the device will work.
#    - Type: Number.
#    - Format: { <float> | <int> }.
#    - Variable name: t_alive.
# 2: Amount of time that the device will emanate a fragrance without stopping.
#    - Type: Time.
#    - Format: ##:##.
#    - Variable name: t_work.
# 3: Amount of time that the device will rest from emanating a fragrance.
#    - Type: Time.
#    - Format: ##:##.
#    - Variable name: t_work.
# 4: ID of the fragrance.
#    - Type: Integer.
#    - Format: <int>.
#    - Variable name: fragrance_id.
################################################################################

import RPi.GPIO as GPIO
import time
import sys
import signal
import .shutdown

# Usage.
def usage():
    print("Usage:\n", "shell$ python3 ", sys.argv[0],
          " <num> ##:## ##:## <int>", file=sys.stderr)
    sys.exit(1)

# Signal handler.
# This helps to avoid the circuit running even after Ctrl-c pressed.
def signal_handler(sig, frame):
    shutdown.stop()
    print('Ctrl-C pressed')
    sys.exit(0)

signal.signal(signal.SIGINT, signal_handler)

# Command line arguments.
args = sys.argv

# Convert hours into seconds.
try:
    t_alive = float(args[1]) * 60 * 60
    t_work = float ((args[2]).split(":")[0]) * 60 * 60
    + float((args[2]).split(":")[1]) * 60
    t_sleep = float((args[3]).split(":")[0]) * 60 * 60
    + float((args[3]).split(":")[1]) * 60
    fragrance_id = args[4]

    print(str(t_work) + " - " + str(t_sleep))
except:
    print("Error: Command line argumments in the wrong format.",
          file=sys.stderr)
    usage()

# Setup time variables.
t_start = time.time()
t_running = 0
t_working = 0

# Pin setup.
pin_general = 7
pin_lock_left = 11
pin_lock_rigth = 13

GPIO.setmode(GPIO.BOARD)
GPIO.setup(pin_general,GPIO.OUT)
GPIO.setup(pin_lock_left,GPIO.OUT)
GPIO.setup(pin_lock_rigth,GPIO.OUT)

# Main loop. Activates or deactivates the emanators.
while  t_running < t_alive:

    #Time Working
    t_working = 0
    t_start_working = time.time()

    while t_working < t_work:
        GPIO.output(pin_general, True)

        if fragrance_id == "0":
            GPIO.output(pin_lock_left, True)
            GPIO.output(pin_lock_rigth, False)
        elif fragrance_id == "1":
            GPIO.output(pin_lock_rigth, True)
            GPIO.output(pin_lock_left, False)
        else:
            print("Error: Wrong fragrance id. Expected: {0 | 1}")
            usage()

        t_working = time.time() - t_start_working

    GPIO.output(pin_general, False)
    GPIO.output(pin_lock_left, False)
    GPIO.output(pin_lock_rigth, False)

    time.sleep(t_sleep)
    t_running = (time.time() - t_start)

# Close the pins.
shutdown.stop()
