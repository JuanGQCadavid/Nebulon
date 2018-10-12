import mysql.connector

# Class that represents a MySQL connection with the capabilities of making queries
class Connection:
    
    # Class constructor that specifies the port on which the database
    # is listening
    def __init__(self, user, password, host, database, port):

        try:
            
            # Get connected
            self.connection = mysql.connector.connect(user=user, password=password, host=host, database=database, port=port)

            # Check connection
            if self.connection.is_connected():
                print('Connection to database stablished')
                
        except:
            raise Exception('Error connecting to the database')
            
    # Class constructor that does not specify port number
    def __init__(self, user, password, host, database):

        try:
            
            # Get connected
            self.connection = mysql.connector.connect(user=user, password=password, host=host, database=database)
            
            # Check connection
            if self.connection.is_connected():
                print('Connection to database stablished')
                
        except:
            raise Exception('Error connecting to the database')
            
    # Class destructor
    def __del__(self):
        try:
            # Closes the connection with the database
            self.connection.disconnect()
            print('Connection closed')
        except:
            raise Exception('Error closing connection to database')


    # Function that updates two of the fields of the table nebulizers:
    # - liquid_level or state
    def updateField(self, field, value, id_nebulizer):

        # Field should take only two values: liquid_level, state

        # Database cursor
        cursor = self.connection.cursor()

        # Query that updates an specified field
        u_query = 'UPDATE nebulizer SET ' + field + ' = ' + value +  ' WHERE id_nebulizer = ' + id_nebulizer + ';'

        print('Sending query: ' + u_query)

        # Making query
        cursor.execute(u_query)

        # Confirm update. Use .rollback() to unconfirm
        # This is needed by the DB and only for UPDATE
        self.connection.commit() 
