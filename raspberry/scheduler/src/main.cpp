#include <iostream>
#include <fstream>
#include <sstream> // istringstream
#include <string>
#include <vector>
#include <stdlib.h> //abs, system
#include "../include/rapidjson/document.h"

/* Needed for Rapidjson */
using namespace rapidjson;

/* Writes a job into a cron file with the correct format. See crontab(5) */
void create_job(std::ofstream& cron, const char* day, const char* start, const char*end,
		const char* working_time, const char* sleeping_time, int schedule_type);

/* Splits a string according to a delimiter */
std::vector<std::string> split(const std::string& s, char delimiter);

/* Reads from a JSON document and returns a pointer to a char* with the text */
char* read_file(char* file_route);

int
main(int argc, char *argv[]){

  if(argc != 3){
    std::cerr << "Usage: " << argv[0] << " <json-file> " << " <cron-file>"
	      << std::endl;
    return 1;
  }

  // JSON handler
  Document json;
  // Buffer with the JSON stored
  char *schedule;

  try{

    schedule = read_file(argv[1]);
    
    // Parse the schedule, though its syntax has been already checked
    if( json.Parse(schedule).HasParseError() ){
      std::cerr << "Error parsing the JSON: " << std::endl
		<< schedule << std::endl;
      return 1;
    }

    // CRON file
    std::ofstream cron(argv[2]);

    if( !cron.is_open() ){
      std::cerr << "Error opening file: " << argv[2] << std::endl;
      return 1;
    }
    
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

    // Add cron file to the crontab
    // c++ does not allow asignation of a string to a non const char *
    char crontab[256] = "crontab ";
    char* command = strcat(crontab, argv[2]);
    system(command);
  
  }catch(std::exception& e){
    std::cerr << "An exception ocurred: " << e.what() << std::endl;
  }
}

void
create_job(std::ofstream& cron, const char* day, const char* start, const char*end,
	   const char* working_time, const char* sleeping_time, int schedule_type){
  
  std::vector<std::string> hours;
  hours = split(std::string(start) + ":" + std::string(end), ':');

  int s_hour, s_min, e_hour, e_min;
  // start = "10:00"
  s_hour = std::stoi(hours[0]);
  s_min = std::stoi(hours[1]);

  // min and hour for crontab
  cron << s_min << " " << s_hour << " ";

  // day month and month, always the same values
  cron << "* * ";

  // end = "23:30"
  e_hour = std::stoi(hours[2]);
  e_min = std::stoi(hours[3]);

  // day of week
  if( day == "monday" )
    cron << "1 ";
  else if( day == "tuesday" )
    cron << "2 ";
  else if( day == "wednesday" )
    cron << "3 ";
  else if( day == "thursday" )
    cron << "4 ";
  else if( day == "friday" )
    cron << "5 ";
  else if( day == "saturday" )
    cron << "6 ";
  else if( day == "sunday" )
    cron << "7 ";

  // command
  cron << "python3 /home/pi/workspace/Nebulon/raspberry/activation/src/activator.py ";

  // arguments for the command
  float total_working_time;
  //total_working_time = (float)(e_hour + e_min) + ((float)())
  //total_working_time = (float)(e_hour - s_hour) + ((float)(abs(e_min - s_min) / (float)100));

  //cron << total_working_time << " ";
  cron << 10.0 << " ";

  cron << working_time << " ";
  cron << sleeping_time << " ";
  cron << schedule_type << std::endl;
}

std::vector<std::string>
split(const std::string& s, char delimiter){

  std::vector<std::string> tokens;
  std::string token;
  std::istringstream token_stream(s);

  while(std::getline(token_stream, token, delimiter))
    tokens.push_back(token);

  return tokens;
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
      std::cout << "Json file read successfully." << std::endl;
    else
      std::cerr << "Error: only read "<< file.gcount() << " characters"
		<< std::endl;
    file.close();

    return buffer;
  }

  return NULL;
}
