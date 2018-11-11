## Requirements

* python3
* pip
* virtualenvwrapper (installed with pip)

-------------------------------------------------

## Clone the repository

* To be able to use or edit the web server, you have to clone it from github

  1. Cone the repo
     ``` $ git clone https://github.com/JuanGQCadavid/Nebulon.git ```
  2. Go where the server is located
     ``` $ cd Nebulon/server/web-server/ ```
  3. Follow the steps in section "Virtual Environments" until step 4, but use "virtual-env" (without quotes) istead of <folder-name>.
  4. Run the server
     ``` $ cd virtual-env/nebulonwebserver ```
     ``` $ python3 manage.py runserver ```

-------------------------------------------------

## Virtual Environments

* The recommended way of using Django is Python virtual Environments.

* How to install them?

  1. ``` $ sudo pip install virtualenvwrapper ```
  2. Create the environment
     ``` $ virtualenv <folder-name> -p <path/to/python3/binary> ```
  3. Activate it
     ``` $ source <folder-name>/bin/activate ```
  4. Use pip to install modules
     ``` $ pip3 install -r requirements.txt ```
  5. Deactivate it
     ``` $ deactivate ```

-------------------------------------------------

## Create a Django project

  0. WITH THE ENVIRONMENT ACTIVATED:
  1. ``` $ pip3 install django ```
  2. ``` $ django-admin startproject <project-name> ```
  3. Run the server. From the server root directory run:
     ``` $ python3 manage.py runserver ```
  4. Start an app: ``` $ python manage.py startapp <appname> ```

-------------------------------------------------

## HTTP REQUESTS WITH NETCAT

   ``` $ printf "GET / HTTP/1.1\r\nUser-Agent: nc/0.7.1\r\nHost: 127.0.0.1\r\nAccept: */*\r\n\r\n" | nc 127.0.0.1 5000 ```
