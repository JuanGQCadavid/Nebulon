import sys # argv, stderr, exit()
from database_connection import Connection

# Instead of this will go the liquid level sensor information
# Temporary libraries
import random
import time
# ----------------

def generateRandom():
    return random.randint(1, 500)

if __name__ == '__main__':

    if len(sys.argv) != 2:
        print('Usage: message_transmission.py <database_info_file>', file = sys.stderr)
        sys.exit()

    # argv[1]: path to database info file
    databaseInfo = open(sys.argv[1], 'r')

    # List that will contain the information
    info = ['', '', '', '']
    
    # read file and split by lines
    if databaseInfo.mode == 'r':
        info = databaseInfo.read().splitlines()
    else:
        print('Error opening database information file with \'read\' permissions', file = sys.stderr)
        sys.exit()

    
    # Connecting to database
    # info = [user, pass, host, db_name]
    connection = Connection(info[0], info[1], info[2], info[3])

    while True:

        # Make update (liquid_level)
        connection.updateField('nebulon_liquid_level', str(generateRandom()), str(0))
        # Send that information each 5 seconds
        time.sleep(5)
