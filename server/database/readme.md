## TO BE ABLE TO USE THE DATABASE

### Using Mysql

1. Add Mysql **v 0.8** Apt Repository
   ``` $ wget -c https://dev.mysql.com/get/mysql-apt-config_0.8.10-1_all.deb ```
2. Install the Mysql Repository
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

### Using Mariadb
1. You need to install mariadb package.
2. Run
   ``` 
   # mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql
   # systemctl start mariadb.service
   # mysql_secure_installation
   $ mysql -u root -p < nebulon_database.sql
     Enter password: [password you choose] 
     ```

