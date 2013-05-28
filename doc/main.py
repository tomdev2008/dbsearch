#!/usr/bin/python
# -*- coding: GBK -*-
import cookielib, urllib2,os,Cookie
from lxml import etree
import re,time
import random
import urllib
import account


def randtopic():
	t=randomlist(titles)
	key=randomitem(contents)
	c=contents[key]
	filehandle=open(c)
	cc=filehandle.read()
	filehandle.close()
	return t,cc

def randomitem(tmplist):
	length=len(tmplist)
	ran=random.randint(1,length)
	i=1
	for a,v in tmplist.items():
		if i==ran:
			return a
		i+=1
	return 'default'
def randomlist(tmplist):
	length=len(tmplist)
	ran=random.randint(0,length-1)
	return tmplist[ran]

def loadtitle():
	file=open("title.txt")
	lines=file.readlines()
	file.close()
	return lines
def searchaddgroup(account):
	start=0;
	while start <=100:
		url='http://www.douban.com/group/explore?start='+str(start)+'&tag=%e6%98%9f%e5%ba%a7'
		account.addmoregroup(url)
		start+=20
def sitesearchgroup(account):
	start=0
	while start <=120:
		url='http://www.douban.com/group/search?start='+str(start)+'&cat=1019&q=%E5%8C%97%E4%BA%AC'
		try:
			account.addsearchgroup(url)
		except:
			print 'error'
		start+=20
		
def publictopic():
	while True:
		for a in aList:
			if a.expiretime<time.time():
				t,c=randtopic()
				a.addtopic(t,c)
		time.sleep(60*10)
	
accountsdict={"andrewsunli@163.com":"73054568:l+X0Fn8qBSE"}
#'1505014246@qq.com':'72538099:9ujSxqAJjBQ',

aList=[]
for k,v in accountsdict.items():
	aList.append(account.Account(k,v))
titles=loadtitle()
contents={'default':'123.txt',
		'1234':'1234.txt',
		'c1':'content/c1.txt',
		'c2':'content/c2.txt'}
#aList[0].removeallgroup()
#searchaddgroup(aList[0])
sitesearchgroup(aList[0])
#for a in aList:
#	searchaddgroup(a)

#publictopic()
#print randtopic()




