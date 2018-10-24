#include <stdio.h>
#include <stdlib.h>
#include <string.h> // memset()

#include <sys/socket.h> // socket(), bind(), listen(), accept(), inet_aton()
#include <netinet/in.h> // struct sockaddr_in, inet_aton()
#include <arpa/inet.h> // inet_pton()
#include <unistd.h> //close()


// Ports under 1024 are reserved, and you can use them only if you are ROOT
#define PORT "17777"

// Maximum waiting queue
#define BACKLOG 10

// Check /proc/sys/net/core/rmem_default for buffer values for recv()
// Check /proc/sys/net/core/wmem_default for buffer values for send()
#define MAX_MESSAGE_SIZE 5000

int
main(){

  // getaddrinfo() ----------------

  int gai_status; // Holds the status that getaddrinfo() returns
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
  
  // End getaddrinfo() ------------

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

    if( bind(server_socket_fd, it -> ai_addr, it -> ai_addrlen) == 0 )
      break; // success bind()ing

    // socket() worked, but bind() didn't, so close it
    close(server_socket_fd); 
    
  }

  // bind() didn't work in any address 
  if(it == NULL){
    fprintf(stderr, "Could not bind()");
    exit(1);
  }
  
  // End socket() and bind() -------------

  // listen() ------------------
  
  // Tells the OS to listen in this socket
  // 100: backlog: how many to enqueue
  if(listen(server_socket_fd, BACKLOG) == -1){
    perror("Error trying to listen");
    exit(1);
  }
  printf("Socket listening!\n");

  // End listen() ------------------

  // Accepting and receiving connections ---------
  while(1){

    printf("Waiting for connections...\n");
    
    // Receiving connection from client

    struct sockaddr_in client_addr;
    int client_socket;
    int addr_size = sizeof(client_addr);
    
    //Accepts the connection and puts the address of the client in client_addr
    client_socket = accept(server_socket_fd, (struct sockaddr *) &client_addr, (socklen_t *) &addr_size);
    
    if(client_socket == -1){
      perror("Error accepting connection from client");
      exit(1);
    }
    printf("Connection from client accepted!\n");

    // End accept() -----------

    // Receiving message size from client
    
    char message_size[10]; //Size of the message that the client will send
    int numbers_read;
    if((numbers_read = recv(client_socket, message_size, 10, 0)) == -1){
      perror("Error receiving message size");
      exit(1);      
    }

    // End recv() ----------------
    
    message_size[numbers_read] = '\0';
    int size = atoi(message_size);
    
    printf("Message size received: %d\n", size);

    // Receiving client message
    
    char client_message[MAX_MESSAGE_SIZE];
    //receives the message and stores it in client_message
    int read_size = 0;
    while(read_size < size){

      // Handles the currrent read size
      // Without this, if the message sent by the client is shorther than size,
      // it will get stucked in an infinite loop printing messages
      int current_read_size;
      current_read_size += recv(client_socket, client_message, MAX_MESSAGE_SIZE, 0);

      read_size += current_read_size;

      // Nothing in input
      if(current_read_size == 0)
	break;
      
      printf(client_message);
    }

    if(read_size == 0){
      printf("Client disconnected. Message not sent from client!\n");
      
    }else if(read_size == -1){
      
      perror("Error receiving message from client");
      exit(1);
      
    }else if(read_size == size)
      printf("Message completely received!\n");

  }

  // End accepting and receiving connections ---------	
  
  close(server_socket_fd);

  return 0;
}
