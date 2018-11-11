#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

int
main(int argc, char *argv[]){

  //sets user ID to root
  setuid(0);

  char command[300];
  
  if(argc == 3){ //Opened network
    
    // argv[1] gets a char, but I need the number, so that's why the conversion
    sprintf(command, "./add_network.sh %d %s", *(argv[1]) - 48, argv[2]);
    system(command);
    
  }else if(argc == 4){ //WPA-PSK
    
    sprintf(command, "./add_network.sh %d \"%s\" \"%s\"", *(argv[1]) - 48, argv[2], argv[3]);
    system(command);
    
  }else if(argc == 5){ //WPA-EAP

    sprintf(command, "./add_network.sh %d \"%s\" \"%s\" \"%s\"", *(argv[1]) - 48, argv[2], argv[3], argv[4]);
    system(command);
    
  }else{
    
    fprintf(stderr, "ERROR: Wrong number of arguments!.\n");
    exit(EXIT_FAILURE);
  }
  
  return 0;
}
