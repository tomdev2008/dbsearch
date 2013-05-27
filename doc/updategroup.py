#!/usr/bin/python
# -*- coding: GBK -*-
import MySQLdb
import account

conn=MySQLdb.connect(host='10.12.143.108',user='macken',passwd='macken',db='data_gather',port=3306)
cur=conn.cursor();

accountsdict={
			'liye19871989@sina.cn':'72944347:/fb+z+stnbI',
			'dengtengfei5000@sina.cn':'72944622:hPQTV3+54OA'}
#'1505014246@qq.com':'72538099:9ujSxqAJjBQ',

a=account.Account('jokerdu@126.com','73054480:Xu/RthOBGLc')

for g in a.groups:
	print g
	sql="insert into group_info(link) values(%s) on duplicate key update name=1"
	cur.execute(sql,g)

cur.close();
conn.close();

