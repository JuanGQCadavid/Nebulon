#include "../include/database_controller.h"

MySQLConnection::MySQLConnection(const char* host_name, const char* user_name,
				 const char* password, const char* db_name,
				 unsigned int port){
  
  // initialize the mysql object
  connection = mysql_init(NULL);
  
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
  printf("Connection to database closed\n");
}

std::string
MySQLConnection::make_query(const char* query,
			    bool select){

  // Execute the query
  printf("Making query: %s\n", query);
  
  // returns 0 on success
  if( mysql_query(connection, query) )    
    fprintf(stderr, "Error making query: %s\n", mysql_error(connection));

  // Obtain the retreived values if it was a SELECT query
  if( select ){
    std::string fetched_value;
    
    MYSQL_RES *result = mysql_store_result(connection);
    
    if( result == NULL )
      fprintf(stderr, "No values retrieved in select query: %s\n", mysql_error(connection));
    
    else{

      MYSQL_ROW row;
      row = mysql_fetch_row(result);
      fetched_value = row[0];
      
    }

    return fetched_value;
  }
  return "";
}
