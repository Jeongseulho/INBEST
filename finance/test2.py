import mysql.connector

mysql_con = mysql.connector.connect(host='stg-yswa-kr-practice-db-master.mariadb.database.azure.com',
                                    port='3306', 
                                    database='S09P22D110',
                                    user='S09P22D110@stg-yswa-kr-practice-db-master.mariadb.database.azure.com', 
                                    password='M2hFMrhLIv')

cur = mysql_con.cursor()

cur.execute("select * from user")

myresult = cur.fetchall()
print(myresult)