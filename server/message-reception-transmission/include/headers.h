#ifndef HEADERS_H
#define HEADERS_H

/* Libraries needed */

#include <fstream>
#include <stdio.h> // printf() fprintf()
#include <stdlib.h> // strtol()

/* Networking, Signaling, Forking */

#include <string.h> // memset()
#include <sys/socket.h> // socket() bind() listen() accept() inet_aton() getaddrinfo()
#include <netinet/in.h> // struct sockaddr_in
#include <arpa/inet.h> // inet_pton()
#include <netdb.h> // getaddrinfo() freeaddrinfo()
#include <unistd.h> // close() fork()
#include <signal.h> // sigaction()
#include <sys/wait.h> // waitpid()
#include <sys/types.h> // getaddrinfo() waitpid() freeaddrinfo() fork()
#include <errno.h> // global variable errno

/* JSON */
#include "rapidjson/document.h"

/* Connection to MySQL */
#include "database_connection.h"

using namespace rapidjson;

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

/* Returns a pointer to the sin_addr structure containing the ip */
void *get_internet_address(struct sockaddr *sa);

/* Stores in 'number' the number in the message's JSON header */
void get_number_in_header(const char* message, int *number_of_chars);

/* -------- END FUNCTION DECALARATION ------- */

/* -------- FUNCTION DEFINITION ------- */

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

void
get_number_in_header(const char* message, int *number_of_chars){

  // 50: max size of the first line in the json message
  for(int i = 0; i < 50; ++i){
    
    if( message[i] == ':' ){
      
      // the message_size will never be a 10 digit number
      char number[10];

      int n = 0, j = i+1;
      while( message[j] != ',' ){

	if( message[j] != ' ' || message[j] != '\t' ){
	  number[n] = message[j];
	  ++n;
	}
	
	++j;
      }
      number[n+1] = '\0';

      *number_of_chars = strtol(number, NULL, 10);
      break;
    }
  }
}

/* -------- END FUNCTION DEFINITION ------- */

#endif
