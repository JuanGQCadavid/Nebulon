import mysql.connector


# Class that represents a MySQL connection with the capabilities of making queries
class Connection:
    
    # Class constructor that specifies the port on which the database
    # is listening
    def __init__(self, user, password, host, database, port):
            
        # Get connected
        self.connection = mysql.connector.connect(user=user, password=password, host=host, database=database, port=port)

        # Check connection
        if self.connection.is_connected():
            print('Connection to database stablished')
        

    # Class constructor that does not specify port number
    def __init__(self, user, password, host, database):
        
        # Get connected
        self.connection = mysql.connector.connect(user=user, password=password, host=host, database=database)
        
        # Check connection
        if self.connection.is_connected():
            print('Connection to database stablished')

    # Class destructor
    def __del__(self):
        # Closes the connection with the database
        self.connection.close()
        print('Connection closed')


    # Function that updates two of the fields of the table nebulizers:
    # - liquid_level or state
    def updateField(self, field, value, id_nebulizer):

        # Field should take only two values: liquid_level, state

        # Database cursor
        cursor = self.connection.cursor()
        
        # Query that updates an specified field
        u_query = 'UPDATE TABLE nebulizers SET ' + field + '=' + value + ' WHERE id_nebulizer=' + id_nebulizer + ';'

        # Escpae the values to prevent SQL Injection
        # query = 'UPDATE TABLE table SET %s = %s WHERE address = %s'
        # values = ('asd','asd','asd')
        # cursor.execute(query, values)
        
        # Making query
        cursor.execute(u_query)

        # Confirm update. Use .rollback() to unconfirm
        # This is needed by the DB and only for UPDATE
        self.connection.commit() 
