#ifndef DATABASE_CONTROLLER_H
#define DATABASE_CONTROLLER_H

/* Standard libraries  */
#include <stdlib.h> // constants __FILE__ __FUNCTION__ __LINE__
#include <stdio.h>
#include <string>

/* MySQL needed libraries */
#include <mysql.h>

class MySQLConnection{
 private:
  MYSQL *connection;

 public:
  MySQLConnection(const char* host_name, const char* user_name,
		  const char* password, const char* db_name,
		  unsigned int port);
  
  ~MySQLConnection();

  // queries
};

#endif
