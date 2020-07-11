---
REF:https://www.alibabacloud.com/blog/building-game-leaderboards-with-redis-on-alibaba-cloud_593849
---

Building Game Leaderboards with Redis on Alibaba Cloud
======================================================

With the massive growth of internet and mobile phone adoption, many games nowadays target people from around the world as their users. These games have millions if not tens of millions (or potentially hundreds of millions) of players globally playing their game. Obviously at such a scale, game developers require a platform that can provide a reliable and highly available solution. Therefore, it is not uncommon to see game developers migrating their platform to the cloud.

In this article, we will look at a relatively simple problem -- creating a leaderboard for a game. If you're using a traditional relational database that is built on-premises, this problem can be challenging. You would have to devise an efficient solution to produce real-time results while considering millions of users from around the world.

The answer? Using a Redis database deployed on the cloud. Redis has a sorted set API that can help you to achieve that. Coupled with Alibaba Cloud's ability to manage Redis clusters, your needs will be covered in the best possible way. In this how to guide, we will be using Python programming language to achieve this.

Objective
---------

The main idea of this demonstration is to use caching layer (Redis) instead of hitting the database to generate leaderboards in gaming applications. This approach is suitable for massive databases that require real-time responses.

Prerequisites
-------------

While it's not compulsory, the basic understanding of Python is an added advantage (the sample code is in Python). Since we're utilizing Redis, it's also good to read up about it to get a better idea of what Redis is as well.

I will not go through creating Redis cluster in Alibaba Cloud, since you can read and follow the step-by-step command separately [in this guide](https://www.alibabacloud.com/help/doc-detail/26351.htm). It's easy and straight forward to set it up.

Implementation
--------------

1.  [Setup Elastic Compute Service (ECS)](https://www.alibabacloud.com/help/doc-detail/25424.htm) and [ApsaraDB for Redis](https://www.alibabacloud.com/help/doc-detail/26351.htm). This tutorial uses Ubuntu 16.04 as the OS, but the solution itself can be done on any OS. The code may differ slightly based on your OS of choice.
2.  Login into the ECS server:

    ```
    ssh -i  root@
    ```

3.  Run steps to install the environment:

    ```
    rm /usr/bin/python # Change python into version 3
    ln -s /usr/bin/python3 /usr/bin/python
    apt-get update # Update Ubuntu
    export LC_ALL=C # Set Locale
    apt-get install python3-pip # Install pip
    pip install redis # Install python-redis
    apt-get install apache2 # Install apache
    mkdir /var/www/python # Set Environment
    a2dismod mpm_event
    a2enmod mpm_prefork cgi
    ```

4.  Replace /etc/apache2/sites-enabled/000-default.conf

    ```
    -----
    <VirtualHost *:80>
            DocumentRoot /var/www/python
            <Directory /var/www/python>
                    Options +ExecCGI
                    DirectoryIndex leaderboards.py
            </Directory>
            AddHandler cgi-script .py
            ErrorLog ${APACHE_LOG_DIR}/error.log
            CustomLog ${APACHE_LOG_DIR}/access.log combined
    </VirtualHost>
    -----
    ```

5.  Put the file in /var/www/leaderboards.py, and edit the settings on line 8 (source code below).
6.  Edit permission in the file.

    ```
    chmod 755 /var/www/python/leaderboards.py
    ```

7.  Restart Apache

    ```
    service apache2 restart
    ```

8.  Go to your web browser and type the public IP address. You should see it working now.

Code Explanation
----------------

Below is the code sample in Python, which I'll explain further:

![1](https://yqintl.alicdn.com/58c8a6c8777bfaef6832d24599c717d2fe0c2d50.png "1")

Code explanation:

1.  Connect to Redis (Line 8):

    ```
    r = redis.StrictRedis(host='', port=6379, db=0, password='')
    ```\
    For this to work correctly, you'll need to update the hostname and password. You can get the from the "Connection Address" during ApsaraDB for Redis creation. The is the password you set during creation as well.

    ![2](https://yqintl.alicdn.com/00301d81fc618f3c10f1a168a24469375c41f435.png "2")

2.  Adding score to leaderboard (Line 16):

    ```
    r.zincrby(leaderboardName, gamer, random.randint(1,1000000))
    ```

    LeaderboardName is the key that you set for your leaderboard's name, gamer is the username or id of the gamer, and the last parameter is where you put the score (which is in this case is a random number).

3.  Get the top 10 highest score (Line 19):

    ```
    r.zrange(leaderboardName, 0, 9, desc=True, withscores=True)
    ```

    LeaderboardName is the key that you set for your leaderboard's name, the second parameter is from which rank do you want to start (0 is the start), and the third parameter is where do you want to stop (-1 to show until the last). The value desc=True is to make the leaderboard's sorted in descending order (False by default) while withscores=True is to return the names along with the scores (False by default).
4.  Get the current player's rank (Line 30):

    ```
    r.zrevrank(leaderboardName, gamer)+1
    ```

    LeaderboardName is the key that you set for your leaderboard's name and gamer is the username or id of the gamer. You'll need to add one (+1) because the rank starts at 0 instead of 1 in the database.

5.  Get the current player (or anyone)'s score (Line 34):

    ```
    r.zscore(leaderboardName, gamer)
    ```

    LeaderboardName is the key that you set for your leaderboard's name and gamer is the username or id of the gamer.

Running the Code
----------------

Below is the expected response of the code when you run it on a webserver.

![3](https://yqintl.alicdn.com/573c8f7c709de78609d4ac57768f67d7e129805c.png "3")

Conclusion
----------

Redis stores data in-memory, and the maturity of the product makes it able to reach performance of millions of requests per second. This makes it the perfect database for this use case and other caching needs.