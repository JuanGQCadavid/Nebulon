COMPILE AND RUN THE SERVER

1. Install the packages 'gcc' and 'make'.
2. run
   $ make
   $ ./bin

KILL THE SERVER

1. press the key combination Ctrl - c.

PACKAGES TO USE NETSTAT AND KILL PROCESSES ON PORTS

1. Install 'net-tools' package or 'netstat'
2. to identify PIDs using sockets on port 17777
   $ sudo netstat -p | grep 17777

3. to kil that PID
   $ kil -9 <PID>
