
## Steps to write and install a crontab

* Crontabs are under /var/spool/cron/. But it is not recommended to edit them directly.

1. Create a file called 'mycontrab'. Or if you already have a crontab file installed and you wnat to edit it, run:
   ``` $ crontab -l > mycrontab ```
2. Edit the file according to the crontab's syntax. See crontab(5) for more information.
3. When you think your crontab is ready, install it.
   ``` $ crontab mycrontab ```
   * If you have syntax errors it will prompt you if you want to edit it again.
