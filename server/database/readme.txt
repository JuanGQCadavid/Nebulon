TO BE ABLE TO USE THE DATABASE

1. You need to install 'mariadb' package.
2. Run
   # mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql
   # systemctl start mariadb.service
   # mysql_secure_installation
   $ mysql -u root -p < nebulon_database.sql
     Enter password: [password you choose]
