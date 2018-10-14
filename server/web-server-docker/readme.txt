NEEDED TO RUN DOCKER

1. Install 'docker' package.
2. Start the docker daemon.
   $ sudo systemctl start docker.service

3. Add the current user to the docker group, to avoid using sudo all the time.
   $ sudo usermod -a -G docker <username>
   
   3.1 Log-out and then log-in to the changes have effect.

4. Test the installation
   $ docker pull hello-world
   $ docker run hello-world

   You will see something like
   
     Hello from Docker!
     This message shows that your installation appears to be working correctly.
     ...

5. After you have created your Dockerfile and you have all the files needed run:
   $ docker build -t <arbitrary-image-name> .
   $ docker run [-p hostPortThatWillBeExposed:portExposedInContainer] <your-image-name>
   
-------------------

COMMON COMMANDS

## List Docker CLI commands
docker
docker container --help

## Display Docker version and info
docker --version
docker version
docker info

## Execute Docker image
docker run hello-world

## List Docker images
docker image ls

## List Docker containers (running, all, all in quiet mode)
docker container ls
docker container ls --all
docker container ls -aq

DOCKERFILE TAGS

- FROM tells Docker which image you base your image on (in the example, Python 3).
  FROM python:3
  
- RUN tells Docker which additional commands to execute.
- CMD tells Docker to execute the command when the image loads.
