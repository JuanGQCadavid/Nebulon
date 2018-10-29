/* All the needed libraries are included here */
#include "../include/headers.h"

/* -------- MACROS -------- */

// Ports under 1024 are reserved, and you can use them only if you are ROOT
#define PORT "17777"

// Maximum waiting queue
#define BACKLOG 10

// Check /proc/sys/net/core/rmem_default for buffer values for recv()
// Check /proc/sys/net/core/wmem_default for buffer values for send()
#define MAX_MESSAGE_SIZE 5000

/* -------- END MACROS -------- */

/* -------- FUNCTION DECALARATION ------- */

/* Signal handler function */
void signal_handler(int signal);

/* Returns the sin_addr structure containing the ip */
void *get_internet_address(struct sockaddr_in *sa);

/* -------- END FUNCTION DECALARATION ------- */

int
main(){
  
  // getaddrinfo() ----------------

  // Holds the status that getaddrinfo() returns
  int gai_status;
  // hints used by getaddrinfo, results (linked list) where the addresses will be saved
  struct addrinfo hints, *result;

  memset( &hints, 0, sizeof(hints) ); // make sure that hints is empty
  /* AI_PASSIVE: address for a socket that will be bind()ed, that will listen
  on all interfaces (INADDR_ANY, IN6ADDR_ANY_INIT), NOTE: this only if parameter
  node in getaddrinfo = NULL */
  hints.ai_flags = AI_PASSIVE;
  hints.ai_family = AF_UNSPEC; // ipv4 or ipv6
  hints.ai_socktype = SOCK_STREAM; // tcp
  hints.ai_protocol = 6; // 6: TCP protocol (/etc/protocols)

  if( (gai_status = getaddrinfo(NULL, PORT, &hints, &result)) != 0 ){
    fprintf(stderr, "getaddrinfo() error: %s", gai_strerror(gai_status));
    exit(1);
  }
  
  // END getaddrinfo() ------------

  // socket() and bind() -------------

  // This server socket file descriptor
  int server_socket_fd;

  // Try each address until bind() returns success
  struct addrinfo *it;
  for(it = result; it != NULL; it = it -> ai_next){

    server_socket_fd = socket(it -> ai_family, it -> ai_socktype,
			      it -> ai_protocol);
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
    fprintf(stderr, "Could not bind()");
    exit(1);
  }
  
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
  
  // END SIGNAL Handling -------------------
  
  // accept() and recv() -------------
  while(1){

    printf("Waiting for connections...\n");
    
    // Receiving connection from client
    
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
    inet_ntop( client_addr.sa_family,
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
      int bytes_read;

      // MAX_MESSAGE_SIZE - 1: avoids writing to the null byte character
      if( (bytes_read = recv(client_socket_fd, message, MAX_MESSAGE_SIZE - 1, 0)) == -1 ){
	  
	perror("recv()");
	exit(0);
	  
      }else if( bytes_read == 0 ) // no message and peer closed the connection
	exit(0);

      // JSON
      for(int i = 0; i < bytes_read; ++i){
	
      }

      while( bytes_read != message_size )
	
	bytes_read += recv(client_socket_fd, message + bytes_read, (MAX_MESSAGE_SIZE - 1) - bytes_read, 0);
	
      
      // Message completely received
      printf("Message from %s completely received", address);

      printf("The message is %s: ", message);

      // Analyze data received and store it in MySQL or send it to a nebulizer

      close(client_socket_fd);
      exit(0);

      // END recv() --------------------------------
	
    }
    // parent
    
    // END fork() --------------------------------

  }
  // END accept() and recv() -------------
  
  close(server_socket_fd);

  return 0;
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
*get_internet_address(struct sockaddr *sa){

  // How these parallel structures work?
  // sockaddr and sockaddr_in
  if( (((struct sockaddr_in *)sa) -> sin_family) == AF_INET ) // IPv4
    return &(((struct sockaddr_in *)sa) -> sin_addr);

  return &(((struct sockaddr_in6*)sa) -> sin6_addr); // IPv6
  
}
