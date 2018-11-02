#include "../include/database_controller.h"

MySQLConnection::MySQLConnection(const char* host_name, const char* user_name,
				 const char* password, const char* db_name,
				 unsigned int port){

  // initialize the mysql object
  mysql_init(connection);

  // try to connect
  if( !mysql_real_connect(connection, host_name, user_name, password,
			  db_name, port, NULL, 0) )
    fprintf(stderr, "Connection to database failed!: Error %s\n",
	    mysql_error(connection));
  else
    printf("Connection to database successfully stablished\n");
    
}

MySQLConnection::~MySQLConnection(){
  // close connection
  mysql_close(connection);
}
