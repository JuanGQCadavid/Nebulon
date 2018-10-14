#include <stdio.h>
#include <stdlib.h>

#include <sys/socket.h> // socket()
#include <netinet/in.h> // struct sockaddr_in

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
  }else
    printf("Socket correctly opened!\n");

  // Server address
  struct sockadd_in server_addr;
  server_addr.sin_family = AF_INET;
  server_addr.sin_port = htons(55555);
  inet_aton("0.0.0.0", &server_addr.sin_addr.s_addr); //takes the ip and v

  // Bind socket to address and port
  if( bind(server_socket, (struct sockaddr *)server_addr, sizeof(server_address)) == -1 ){
    perror("Error binding socket");
    exit(1);
  }

  // Tells the OS to listen in this socket
  // 100: backlog: how many to enqueue
  if(listen(server_socket, 100) == -1){
    perror("Error trying to listen");
    exit(1);
  }

  while(true){
    
    // Receiving connection from client
    int client_socket;
    client_socket = accept(server_socket, NULL, NULL);

    
    
  }
  

  

  
  
  close(server_socket);

  return 0;
}
