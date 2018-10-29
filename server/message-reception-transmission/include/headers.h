#include <stdio.h>
#include <stdlib.h>

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
