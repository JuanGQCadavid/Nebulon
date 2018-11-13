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

void
MySQLConnection::update_query(const char* table,
			      const char* set_attribute, const char* set_value,
			      const char* where_attribute, const char* where_value){

  // Holds a MySQL query
  // UPDATE $table SET $set_attribute = $set_value WHERE $where_attribute = $where_value
  // The mysql_query() function does not require the ';' at the end
  std::string query;

  query = "UPDATE ";
  query.append(table);
  query.append(" SET ");
  query.append(set_attribute);
  query.append(" = ");
  query.append(set_value);
  query.append(" WHERE ");
  query.append(where_attribute);
  query.append(" = ");
  query.append(where_value);

  printf("Making query: %s\n", query.c_str());

  // returns 0 on success
  if( mysql_query(connection, query.c_str()) )
    
    fprintf(stderr, "Error making update query: %s\n", mysql_error(connection));
    
}

void
MySQLConnection::select_query(const char* field, const char* table,
			      const char* condition, char *&fetched_value){
  
  // The mysql_query() function does not require the ';' at the end
  std::string query;

  query = "SELECT ";
  query.append(field);
  query.append(" FROM ");
  query.append(table);
  query.append(" WHERE ");
  // query.append(where_attribute);
  // query.append(" = ");
  // query.append(where_value);
  query.append(condition);

  printf("Making query: %s\n", query.c_str());

  // returns 0 on success
  if( mysql_query(connection, query.c_str()) )
    
    fprintf(stderr, "Error making select query: %s\n", mysql_error(connection));

  MYSQL_RES *result = mysql_store_result(connection);

  if( result == NULL )
    fprintf(stderr, "No values retrieved in select query: %s\n", mysql_error(connection));
  
  else{

    MYSQL_ROW row;

    row = mysql_fetch_row(result);

    fetched_value = row[0];

    mysql_free_result(result);
    
  }
    
}
