## COMPILE AND RUN THE SERVER

1. Install the packages 'gcc', 'g++' and 'make'.
2. Download the library RapidJSON from ``` https://github.com/Tencent/rapidjson.git ```.
  2.1. Include the folder ``` rapidjson/include/rapidjson ``` into ``` ./include/ ```.
3. Have ``` mysql ``` installed. See database section for more information. And ...
   ``` sudo apt install libmysqlclient-dev ```
4. Run.
   ``` $ make ```
   ``` $ ./bin/server <path/to/dbinfo>```

-------------------------

## SERVER ADMINISTRATION

# KILL THE SERVER

*. Press the key combination ``` Ctrl - c ```.
*. Send a SIGKILL using the command ``` kill ```.

# PACKAGES TO USE NETSTAT AND KILL PROCESSES ON PORTS

1. Install ``` net-tools ``` package or the ``` netstat ``` package.

2. to identify PIDs using sockets on port 17777
   ``` $ sudo netstat -p | grep 17777 ```

3. to kil that PID
   ``` $ kil -9 <PID> ```
