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

  days
  if( )

  for(SizeType i = 0; i < days.Size(); ++i){

    // s: represents each configuration that a day might have
    Value::ConstMemberIterator s = days[i][].MemberBegin()["monday"];
    
    
    string day = s -> name.GetString();
    if( day == "monday" ){
      
      const Value& config = s -> GetValue();
      
      
    }else if( day == "tuesday" ){
      
    }else if( day == "wednesday" ){
      
    }else if( day == "thursday" ){
      
    }else if( day == "friday" ){
      
    }else if( day == "saturday" ){
      
    }else if( day == "sunday" ){
      
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
