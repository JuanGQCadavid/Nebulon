#include <iostream>
#include <fstream>
#include "../include/rapidjson/document.h"

/* Needed for Rapidjson */
using namespace rapidjson;

/* Writes a job into a cron file with the correct format. See crontab(5) */
void create_job(std::ofstream& cron, const char* day, const char* start, const char*end,
		const char* working_time, const char* sleeping_time, int schedule_type);

/* Reads from a JSON document and returns a pointer to a char* with the text */
char* read_file(char* file_route);

int
main(int argc, char *argv[]){

  if(argc != 3){
    std::cerr << "Usage: " << argv[0] << " <read-from-file> " << " <write-into-file>"
	      << std::endl;
    return 1;
  }

  // JSON handler
  Document json;
  // Buffer with the JSON stored
  char *schedule;

  schedule = read_file(argv[1]);

  // CRON file
  std::ofstream cron(argv[2]);

  if( !cron.is_open() ){
    std::cerr << "Error opening file: " << argv[2] << std::endl;
    return 1;
  }

  // Cron needed header
  cron << "SHELL=/bin/sh\n";
  cron << "CRON_TZ=America/Bogota\n";

  try{

    // Parse the schedule, though its syntax has been already checked
    json.Parse(schedule);
    
    // Days of the week
    const char* days[] = {"monday", "tuesday", "wednesday", "thursday", "friday",
		    "saturday", "sunday"};

    // config: Array of objects with the configuration for each day
    const Value& config = json["schedule"];
    for(SizeType i = 0; i < config.Size(); ++i){
      
      for(int j = 0; j < 7; ++j){ //for each day
	if( config[i].HasMember(days[j]) ){

	  // s: Array with the configurations that a day can have
	  const Value& s = config[i][days[j]];
	  for(SizeType k = 0; k < s.Size(); ++k){ // for each config in day days[k]

	    create_job(cron, days[j], s[k]["start"].GetString(),
		       s[k]["end"].GetString(), s[k]["working_time"].GetString(),
		       s[k]["sleeping_time"].GetString(), s[k]["schedule_type"].GetInt());
	    
	  }

	  break; // each config has only one day
	}
      } 
    }

    // Close cron file
    cron.close();
  
  }catch(std::exception& e){
    std::cerr << "An exception ocurred: " << e.what() << std::endl;
  }
}

void
create_job(std::ofstream& cron, const char* day, const char* start, const char*end,
	   const char* working_time, const char* sleeping_time, int schedule_type){


  // use this guide: https://www.fluentcpp.com/2017/04/21/how-to-split-a-string-in-c/
  
  // cron << ;
  
}

char*
read_file(char* file_route){
  
  // Stream that will read from the file with the schedule
  std::ifstream file(file_route);
  
  if( file.is_open() ){
    
    // Put the stream at the end of the file
    file.seekg(0, file.end);
    // Get the position of the stream in the file
    int file_size = file.tellg();
    // Return to the beginning of file
    file.seekg(0, file.beg);

    // Buffer with that will hold the file's content
    char *buffer = new char[file_size];
    // Read the entire file into buffer
    file.read(buffer, file_size);

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
