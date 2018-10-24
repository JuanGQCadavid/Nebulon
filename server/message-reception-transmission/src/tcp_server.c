#include <stdio.h>
#include <stdlib.h>
#include <string.h> // memset()

#include <sys/socket.h> // socket(), bind(), listen(), accept(), inet_aton()
#include <netinet/in.h> // struct sockaddr_in, inet_aton()
#include <arpa/inet.h> // inet_pton()
#include <unistd.h> //close()

#define PORT 17777

// Maximum waiting queue
#define BACKLOG 100

// Check /proc/sys/net/core/rmem_default for buffer values for recv()
// Check /proc/sys/net/core/wmem_default for buffer values for send()
#define MAX_MESSAGE_SIZE 5000

int
main(){

  // AF_INET: Internet socket
  // SOCK_STREAM: Connection oriented socket
  // 6: TCP protocol (/etc/protocols)
  int server_socket = socket(AF_INET, SOCK_STREAM, 6);

  // Error checking
  if(server_socket == -1){
    // Prints this message followed by the last library error encountered
    perror("Error opening socket");
    exit(1);
  }
  printf("Socket correctly opened!\n");

  // Server address -------------
  struct sockaddr_in server_addr;
  server_addr.sin_family = AF_INET;
  server_addr.sin_port = htons(PORT);
  //server_addr.sin_addr.s_addr = INADDR_ANY;
  // converts the dotted ip into network byte order
  if( inet_pton(AF_INET, "0.0.0.0", &(server_addr.sin_addr)) != 1 ){
    perror("inet_pton error");
    exit(1);
  }
  // server_addr.sin_zero: padding array to make sockaddr_in equal in size to
  // sockaddr
  memset(server_addr.sin_zero, 0, sizeof(server_addr.sin_zero));

  //End server address -----------

  // Binds a socket to address and port ---------
  
  if( bind(server_socket, (struct sockaddr *) &server_addr, sizeof(server_addr) ) == -1 ){
    perror("Error binding socket");
    exit(1);
  }
  printf("Socket correctly binded!\n");

  // End bind() -----------------

  // listen() ------------------
  
  // Tells the OS to listen in this socket
  // 100: backlog: how many to enqueue
  if(listen(server_socket, BACKLOG) == -1){
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
    client_socket = accept(server_socket, (struct sockaddr *) &client_addr, (socklen_t *) &addr_size);
    
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
  
  close(server_socket);

  return 0;
}
