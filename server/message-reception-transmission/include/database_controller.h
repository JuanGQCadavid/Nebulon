#ifndef DATABASE_CONTROLLER_H
#define DATABASE_CONTROLLER_H

/* Standard libraries  */
#include <stdlib.h> // constants __FILE__ __FUNCTION__ __LINE__
#include <stdio.h>
#include <string>

/* MySQL needed libraries */
#include <mysql.h>


/* Controller class to connect to and send queries to a MySQL database */
class MySQLConnection{
 private:
  /* Object that holds the connection properties */
  MYSQL *connection;

 public:
  /* Class constructor that receives as parameters the connection information */
  MySQLConnection(const char* host_name, const char* user_name,
		  const char* password, const char* db_name,
		  unsigned int port);

  /* Class destructor that closes the connection to the database */
  ~MySQLConnection();

  /* Wrapper function to send a query to the database */
  void update_query(const char* table, const char* set_attribute, const char* set_value, const char* where_attribute, const char* where_value);

  /* Wrapper function to send a select query to the database, and stores the
     result in 'fetched_value' */
  void select_query(const char* field, const char* table,
		    const char* condition, char *&fetched_value);
  
};

#endif
