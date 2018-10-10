from database_connection import Connection

# Instead of this will go the liquid level sensor information
# Temporary libraries
import random
import time
# ----------------

def generateRandom():
    return random.randint(1, 500)

if __name__ == '__main__':

    # Connecting to database
    connection = Connection('root', 'passwd', '127.0.0.1', 'nebulon_database')

    while True:

        # Make update (liquid_level)
        connection.updateField('liquid_level', generateRandom(), 0)

        # Send that information each 5 seconds
        time.sleep(5)

    
