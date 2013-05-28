#!/usr/bin/python
# -*- coding: GBK -*-
import MySQLdb
import account
from lxml import etree
import cookielib, urllib2
import lxml.html

def updategroupname(gurl):
	print gurl
	response=urllib2.urlopen(gurl)
	data=response.read()
	root=etree.HTML(data)
	title=root.xpath('//title')
#	head=root.SubElement(root,'head')
#	title=root.SubElemet(head,'title')
#	title=root.title
#	print etree.tostring(title[0].text)
	clean_title=title[0].text.replace('\n','').replace('小组','').replace(' ','')
	sql="update group_info set `name`='"+clean_title+"' where link='"+gurl+"'"

	try:
		cur.execute(sql)
	except:
		print gurl

def addgroup():
	for g in a.groups:
		print g
		sql="insert into group_info(link) values(%s) on duplicate key update name=1"
		cur.execute(sql,g)
	
def getdbgroup():
	sql="select link from group_info"
	cur.execute(sql)
	return cur.fetchall()
	
	
		
	

conn=MySQLdb.connect(host='10.12.143.108',user='macken',passwd='macken',db='data_gather',port=3306,charset='utf8')
cur=conn.cursor();
	
#a=account.Account('jokerdu@126.com','73054480:Xu/RthOBGLc')
#for g in a.groups:
#	updategroupname(g)
res=getdbgroup()
for s in res:
	updategroupname(s[0])

cur.close();
conn.close();