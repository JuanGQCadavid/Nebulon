## TO BE ABLE TO USE THE DATABASE

### Install Mariadb Ubuntu 16.04

1. Update and Upgrade Ubuntu
   ``` $ sudo apt update ```
   ``` $ sudo apt upgrade ```
   ``` $ sudo apt dist-upgrade ```
   
2. Install mariadb
   ``` $ sudo apt install mariadb-server mariadb-client  ```
   
3. Start and enbale mysql daemon
   ``` $ sudo systemctl start mysql ```
   ``` $ sudo systemctl enable mysql ```
   
4. Mariadb Secure Installation
   ``` $ sudo mysql_secure_installation ```
   
   4.1 When prompted. Answer no.
   ``` Disallow root login remotely? [Y/n] n ```
   
5. Log in to mysql
   ``` $ mysql -u root -p ```
   
   5.1 In case of error
   ``` ERROR 1698 (28000): Access denied for user 'root'@'localhost' ```
   Edit the file: /etc/mysql/mariadb.conf.d/50-server.cnf, or locate the correct my.cnf file
   Add skip-grant-tables under [mysqld], and then you can log in without password. Run:
   
   ``` MariaDB [(none)]> UPDATE mysql.user SET plugin = '' WHERE plugin = 'unix_socket'; ```
   
   Remove or comment skip-grant-tabes in the my.cnf, and restart mysql daemon. Now you can
   log in with password.

6. Create database.
   ``` $ mysql -u root -p < nebulon_database.sql ```
   ``` Enter password: [password you choose] ```
   OR inside mysql
   ``` MariaDB [(none)]> SOURCE <path/to/nebulon_database.sql> ```
     
--------------------------------------

### Compatibility between TCP Server and database

1. Create new user ``` CREATE USER 'nebulonHJGR'@'localhost' IDENTIFIED BY 'password-already-defined'; ```.
2. Grant UPDATE, SELECT privileges:
   ``` GRANT UPDATE ON nebulon_database.nebulon TO 'nebulonHJGR'@'localhost'; ```
   ``` GRANT SELECT ON nebulon_database.nebulon TO 'nebulonHJGR'@'localhost'; ```
   ``` GRANT SELECT ON nebulon_database.staff TO 'nebulonHJGR'@'localhost'; ```
3. Check for granted privileges:
   ``` SHOW GRANTS FOR 'nebulonHJGR'@'localhost'; ```
4. If you need to revoke a privilege:
   ``` REVOKE type_of_permission ON database_name.table_name FROM ‘username’@‘localhost’; ```

--------------------------------------

### Open Mariadb (Mysql) to the world. (Remote LogIn)

# Guide: https://mariadb.com/kb/en/library/configuring-mariadb-for-remote-client-access/

1. Comment the line bind-addres = <some-ip-address> in the my.cnf file.
   Note: my.cnf file is usually located at /etc/mysql/mariadb.conf.d/50-server.cnf

2. Create new user and give privileges to it.
   Note: root@localhost is different from root@<some-ip>
   ``` MariaDB [none]> GRANT ALL PRIVILEGES ON *.* TO 'user'@'%' IDENTIFIED BY 'nebulon-database' WITH GRANT OPTION; ```

3. Open port 3306.
   ``` $ sudo iptables -A INPUT -i eth0 -p tcp -m tcp --dport 3306 -j ACCEPT ```

4. Add rule to Azure to open port 3306

5. Add skip-name-resolve to the my.cnf (WARNING! Bad practice)
   
--------------------------------------

### Install Mysql not Mariadb

1. Download Mysql **v 0.8** Apt Repository
   ``` $ wget -c https://dev.mysql.com/get/mysql-apt-config_0.8.10-1_all.deb ```
2. Add Mysql repository
   ``` $ sudo dpkg -i mysql-apt-config_0.8.10-1_all.deb ```
3. Press Ok
4. Update apt
   ``` $ sudo apt update ```
5. Finally, Install Mysql.
   ``` $ sudo apt-get install mysql-server ```

**Check:**
   ``` $ sudo systemctl status mysql ```
**Optional:**
   ``` $ sudo systemctl enable mysql ```