/* All the needed libraries, macros, helper functions are included here */
#include "../include/headers.h"

/* Function to obtain the available addresses and store them in 'result' */
void obtain_available_addresses(struct addrinfo *&result);

/* Create a socket and bind it to an available address in result */
void socket_and_bind(int &server_socket_fd, struct addrinfo *result);

/* Function that initializes the signal handling */
void signal_handling();

/* Signal handler function */
void signal_handler(int signal);

/* Funtion to receive a message in client_socket_fd */
void receive_message(int client_socket_fd, char* message, char* client_ip_address);

/* Function that initializes the mysql controller object */
void open_connection_to_database(MySQLConnection *&mysql, char* file);

/* Function that constructs a json response to a previous request 
   (ip addresses request by app) and stores the result in 'json_response' */
void build_response(Document &json, MySQLConnection *mysql, std::string &json_response);

int
main(int argc, char* argv[]){

  if(argc != 2){
    printf("Usage: Wrong number of arguments.\nExpected: $ %s <dbinfofile>", argv[0]);
    exit(1);
  }
  
  // getaddrinfo() ----------------

  struct addrinfo *result;
  obtain_available_addresses(result);
  
  // END getaddrinfo() ------------

  // socket() and bind() -------------

  // This server socket file descriptor
  
  int server_socket_fd;
  socket_and_bind(server_socket_fd, result);
  
  // END socket() and bind() -------------

  // listen() ------------------
  
  // Tells the OS to listen in this socket
  // 100: backlog: how many to enqueue
  if(listen(server_socket_fd, BACKLOG) == -1){
    perror("listen()");
    exit(1);
  }
  printf("Socket listening!\n");

  // END listen() ------------------

  // SIGNAL Handling -----------------------

  signal_handling();
  
  // END SIGNAL Handling -------------------
  
  // accept() and recv() -------------
  while(1){

    printf("Waiting for connections...\n");
    
    // Receiving connection from client

    // sockaddr_storage can hold both IPv4 or IPv6 addresses
    struct sockaddr_storage client_addr;
    int client_socket_fd;
    int addr_size = sizeof(client_addr);

    // accept() -----------------
    
    //Accepts the connection and puts the address of the client in client_addr
    client_socket_fd = accept(server_socket_fd, (struct sockaddr *) &client_addr, (socklen_t *) &addr_size);
    
    if( client_socket_fd == -1 ){
      perror("accept()");
      continue;
    }

    char address[INET6_ADDRSTRLEN];
    inet_ntop( client_addr.ss_family,
	       get_internet_address( (struct sockaddr*) &client_addr), address, sizeof(address) );
    printf("accept(): Got connection from: %s \n", address);

    // END accept() -------------------

    // fork(): child process  -----------------

    int child_pid;

    if( (child_pid = fork()) < 0 )
      
      perror("fork()");
    
    else if( child_pid == 0){ // child
    
      // recv(): Receiving message from client ------

      char message[MAX_MESSAGE_SIZE];
      receive_message(client_socket_fd, message, address);

      // END recv() --------------------------------

      try{

	// Analyze data received and store it in MySQL or send it to a nebulizer
	Document json;

	if( json.Parse(message).HasParseError() )
	  fprintf(stderr, "The message received doesn't have the proper JSON format:\n%s\n", message);
	else{

	  // If the root is an object
	  if( json.IsObject() && json.HasMember("message_type") ){
	
	    const char* message_type = json["message_type"].GetString();

	    // Message received from nebulon or mobile app
	    if( (strcmp(message_type, "neb_to_server_llu") == 0)
		|| (strcmp(message_type, "neb_to_server_ipu") == 0)
		|| (strcmp(message_type, "app_to_server_ipr") == 0) ){

	      // database controller object
	      MySQLConnection *mysql;

	      // initializes the controller
	      open_connection_to_database(mysql, argv[1]);

	      if( strcmp(message_type, "neb_to_server_llu") == 0 )
		
		// Make update_query
		mysql -> update_query( "nebulon", "nebulon_liquid_level",
				       std::to_string(json["nebulon_liquid_level"].GetInt()).c_str(), "nebulon_id",
				       std::to_string(json["nebulon_id"].GetInt()).c_str() );
		  
	      
	      else if( strcmp(message_type, "neb_to_server_ipu") == 0 )
		// Make update_query
		mysql -> update_query( "nebulon", "nebulon_private_ip",
				       json["nebulon_ip_address"].GetString(), "nebulon_id",
				       std::to_string(json["nebulon_id"].GetInt()).c_str() );
		
	      else if( strcmp(message_type, "app_to_server_ipr") == 0 ){

		// making JSON response --------------------
		
		std::string json_response;

		build_response(json, mysql, json_response);

		// END making JSON response --------------------

		// send() -----------------------------

		if( send(client_socket_fd, json_response.c_str(), json_response.size(), 0) == -1 )
		  perror("send()");

		// END send() ---------------------------
	      }

	      // deleting dynamic allocation for mysql
	      delete mysql;
	      
	    }else
	      // 'message' does not have a valid message_type
	      fprintf(stderr, "CHILD: Message with unrecognized type: %s\n", message_type);

	  }else
	    // 'message' is not and object or it has not he member 'message_type'
	    fprintf(stderr, "CHILD: I cannot understand the message:\n%s\n", message);
	  
	}
      
      }catch(std::exception &e){
	fprintf(stderr, "CHILD: Exception occured: %s\n", e.what());
      }

      close(client_socket_fd);
      printf("CHILD: Connection from %s closed!\n", address);
      exit(0);
	
    }
    // parent
    
    // END fork() --------------------------------

  }
  // END accept() and recv() -------------
  
  close(server_socket_fd);

  return 0;
}

/* ---------- MAIN FUNCTIONS ---------- */

void
obtain_available_addresses(struct addrinfo *&result){
  // Holds the status that getaddrinfo() returns
  int gai_status;
  // hints used by getaddrinfo
  struct addrinfo hints;

  memset( &hints, 0, sizeof(hints) ); // make sure that hints is empty
  /* AI_PASSIVE: address for a socket that will be bind()ed, that will listen
     on all interfaces (INADDR_ANY, IN6ADDR_ANY_INIT), NOTE: this only if parameter
     node in getaddrinfo = NULL */
  hints.ai_flags = AI_PASSIVE;
  hints.ai_family = AF_UNSPEC; // ipv4 or ipv6
  hints.ai_socktype = SOCK_STREAM; // tcp
  hints.ai_protocol = 6; // 6: TCP protocol (/etc/protocols)

  // result (linked list) where the addresses will be saved
  if( (gai_status = getaddrinfo(NULL, PORT, &hints, &result)) != 0 ){
    fprintf(stderr, "getaddrinfo() error: %s", gai_strerror(gai_status));
    exit(1);
  }
  
}

void
socket_and_bind(int &server_socket_fd, struct addrinfo *result){

  // Try each address until bind() returns success
  struct addrinfo *it;
  for(it = result; it != NULL; it = it -> ai_next){

    // 0: any protocol
    server_socket_fd = socket(it -> ai_family, it -> ai_socktype, 0);

    // If there was an error opening the socket, try with another addr
    if( server_socket_fd == -1 )
      continue;

    // If the port is being used by another socket, you can share the same port
    // setsockopt(): set socket option
    // SOL_SOCKET: level in the protocol stack where the option is gonna be readed
    // SO_REUSEADDR: option
    int yes = 1;
    if( setsockopt(server_socket_fd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) == -1 ){
      perror("setsockopt()");
      exit(1);
    }

    if( bind(server_socket_fd, it -> ai_addr, it -> ai_addrlen) == 0 )
      break; // success bind()ing

    // socket() worked, but bind() didn't, so close it
    close(server_socket_fd); 
    
  }

  // release dinamically alocated linked list
  freeaddrinfo( result );

  // bind() didn't work in any address 
  if(it == NULL){
    fprintf(stderr, "Could not bind()\n");
    exit(1);
  }  
}

void signal_handling(){
  
  // Contains information about which signals handle and how
  struct sigaction signal_action;
  signal_action.sa_handler = signal_handler; // how to handle the signal
  // signals to block, that is,there may be some code in the handler that
  // shouldn't be interrupted by a signal.
  // Set it to empty
  sigemptyset( &signal_action.sa_mask );
  // System calls interrupted by this signal will be restarted
  signal_action.sa_flags = SA_RESTART;

  if( sigaction(SIGCHLD, &signal_action, NULL) == -1 ){
    perror("sigaction()");
    exit(1);
  }
  
}

void
signal_handler(int signal){

  // waitpid() might change errno value that might be in use by
  // another function
  int errno_temp = errno;

  // waitpid(): waits for a child process to terminate and reap it
  // if you are here is because a child has already termianted, so
  // it will return immediately
  while( waitpid(-1, NULL, WNOHANG) > 0 );

  errno = errno_temp;

}

void
receive_message(int client_socket_fd, char* message, char* client_ip_address){
  int bytes_read;

  // MAX_MESSAGE_SIZE - 1: avoids writing to the null byte character
  if( (bytes_read = recv(client_socket_fd, message, MAX_MESSAGE_SIZE - 1, 0)) == -1 ){
	  
    perror("recv()");
    close(client_socket_fd);
    printf("CHILD: Connection from %s closed!\n", client_ip_address);
    exit(0);
	  
  }else if( bytes_read == 0 ){
    // no message and peer closed the connection
    close(client_socket_fd);
    printf("CHILD: Connection from %s closed!\n", client_ip_address);
    exit(0);
  }
      
  // Obtain message size
  int message_size;
  get_number_in_header(message, &message_size);
      
  // Read missing part of the message
  while( bytes_read != message_size )
	
    bytes_read += recv(client_socket_fd, message + bytes_read, (MAX_MESSAGE_SIZE - 1) - bytes_read, 0);

  message[bytes_read] = '\0'; // null byte to mark the end of the message
	
  // Message completely received
  printf("CHILD: Message from %s completely received\n", client_ip_address);
  
}

void
open_connection_to_database(MySQLConnection *&mysql, char* file){
  
  // open database info file
  std::ifstream db_info(file, std::ifstream::in);
	    
  if( db_info.is_open() ){

    std::string host, user, pass, database;
    unsigned int port;

    // read from file
    db_info >> user;
    db_info >> pass;
    db_info >> host;
    db_info >> database;
    db_info >> port;

    db_info.close(); // close database info file

    // Create connection
    mysql = new MySQLConnection(host.c_str(), user.c_str(), pass.c_str(), database.c_str(), port);
    
  }else
    // could not open db info file
    fprintf(stderr, "CHILD: Unable to open database information file: %s\n", file);
}

void
build_response(Document &json, MySQLConnection *mysql, std::string &json_response){
  
  json_response = "\t\"message_type\" : \"serv_to_app_ips\",\n\t\"nebulons_ips\" : [\n";

  const Value& nebulons_ids = json["nebulons_ids"];
  for( SizeType it = 0; it < nebulons_ids.Size(); ++it ){

    char* neb_privateip;
    const char* neb_id = std::to_string(nebulons_ids[it].GetInt()).c_str();
    // Makes a select query, and stores the result in neb_privateip
    mysql -> select_query( "nebulon_private_ip", "nebulon",
			   "nebulon_id", neb_id,
			   neb_privateip);

    json_response.append("\t\t{\n\t\t\t\"neb_id\" : ");
    json_response.append(neb_id);
    json_response.append(",\n\t\t\t\"neb_ip\" : \"");
    json_response.append(neb_privateip);
    json_response.append("\"\n\t\t}");

    if( (it + 1) < nebulons_ids.Size() )
      json_response.append(",\n");
    else
      json_response.append("\n");
  }

  json_response.append("\t]\n}");
  std::string json_aux = "{\n\t\"message_size\" : ";

  int message_size = json_response.size() + json_aux.size();
  // +2: the comma and the \n
  message_size += ( std::to_string(message_size).size() + 2 );
  json_aux.append( (std::to_string(message_size) += ",\n") );
  json_aux.append( json_response );
  json_response = json_aux;
  
}

/* ---------- END MAIN FUNCTIONS ---------- */

