#include "../include/rapidjson"
#include <iostream>
#include <fstream>

char* read_file(char* file_route);

int
main(char *argv[]){

  // JSON handler
  Document json;
  // Buffer with the JSON stored
  char *schedule;

  schedule = read_file(argv[1]);

  // Parse the schedule, its syntax has been already checked
  json.Parse(schedule);

  // Array of objects with the configuration for each day
  const Value& days = json["schedule"];

  const Value& s = ;


  for(SizeType i = 0; i < days.Size(); ++i){

    // 
    const Value& day = days[i].MemberBegin();
    
    std::string day_name = day -> name.GetString();

    // s: represents an array with each configuration that a day might have
    const Value& config = s -> value;
    
    if( day_name == "monday" ){
      
      // iterate over s and make things xD
      
    }else if( day_name == "tuesday" ){
      
    }else if( day_name == "wednesday" ){
      
    }else if( day_name == "thursday" ){
      
    }else if( day_name == "friday" ){
      
    }else if( day_name == "saturday" ){
      
    }else if( day_name == "sunday" ){
      
    }
    
  }
}

char*
read_file(char* file_route){
  
  // Stream that will read from the file with the schedule
  std::ifstream file(file_route);
  
  if( file.is_iopen() ){
    
    // Put the stream at the end of the file
    file.seekg(0, file.end);
    // Get the position of the stream in the file
    int file_size = file.tellg();
    // Return to the beginning of file
    file.seekg(0, file.beg);

    // Buffer with that will hold the file's content
    char *buffer = new char[length];
    // Read the entire file into buffer
    file.read(buffer, length);

    // Error checking
    if( file )
      std::cout << "Entire file read successfully." << std::endl;
    else
      std::cerr << "Error: only read "<< file.gcount() << " characters"
		<< std::endl;
    file.close();

    return buffer;
  }

  return NULL;
}
