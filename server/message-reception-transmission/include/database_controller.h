#ifndef DATABASE_CONTROLLER_H
#define DATABASE_CONTROLLER_H

/* Standard libraries  */
#include <stdlib.h> // constants __FILE__ __FUNCTION__ __LINE__
#include <stdio.h>
#include <string>
#include <vector>

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

  /* Wrapper function to send a query to the database and to return the fetched
     value, if any */
  std::string make_query(const char* query,
			 bool select);
  
};

#endif
