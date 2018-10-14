#include <stdio.h>
#include <stdlib.h>

#include <sys/socket.h> // socket(), bind(), listen(), accept(), inet_aton()
#include <netinet/in.h> // struct sockaddr_in, inet_aton()
#include <arpa/inet.h> // inet_aton()
#include <unistd.h> //close()

#define PORT 55555
#define BACKLOG 100
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

  // Server address
  struct sockaddr_in server_addr;
  server_addr.sin_family = AF_INET;
  server_addr.sin_port = htons(PORT);
  //inet_aton("0.0.0.0", &server_addr.sin_addr.s_addr); //takes the ip and v
  server_addr.sin_addr.s_addr = INADDR_ANY;

  // Bind socket to address and port
  if( bind(server_socket, (struct sockaddr *) &server_addr, sizeof(server_addr)) == -1 ){
    perror("Error binding socket");
    exit(1);
  }
  printf("Socket correctly binded!\n");
  
  // Tells the OS to listen in this socket
  // 100: backlog: how many to enqueue
  if(listen(server_socket, BACKLOG) == -1){
    perror("Error trying to listen");
    exit(1);
  }
  printf("Socket listening!\n");

  while(1){

    printf("Waiting for connections...\n");
    
    // Receiving connection from client (mobile app)

    struct sockaddr_in client_addr;
    int client_socket;
    int addr_size = sizeof(client_addr);
    client_socket = accept(server_socket, (struct sockaddr *) &client_addr, (socklen_t *) &addr_size);
    
    if(client_socket == -1){
      perror("Error receiving connection from client");
      exit(1);
    }
    printf("Connection from client accepted");

    // Receiving message from client
    char client_message[MAX_MESSAGE_SIZE];
    int read_size = recv(client_socket, client_message, MAX_MESSAGE_SIZE, 0);

    if(read_size == -1){
      perror("Error receiving message from client");
      exit(1);      
    }
    printf("Message received!\n");

    printf("Message:\n%s", client_message);
  }
  
  close(server_socket);

  return 0;
}
