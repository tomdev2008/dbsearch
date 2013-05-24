#!/usr/bin/python
# -*- coding: GBK -*-
import cookielib, urllib2,os,Cookie
from lxml import etree
import re,time
import random
import urllib
import account


#def searchgroup(start):
#	url='http://www.douban.com/group/explore?start='+start+'&tag=%e6%81%8b%e7%88%b1'
#	print url
#	response=opener.open(url)
#	html=response.read()
#	root=etree.HTML(html)
#	nodes=root.xpath('//*[@id="content"]/div/div[1]/div[1]/div/div/div/h3/a')
#	res=[]
#	for node in nodes:
#		res.append(node.get("href"))
#	return res



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


accountsdict={'1505014246@qq.com':'72538099:9ujSxqAJjBQ'}

aList=[]
for k,v in accountsdict.items():
	aList.append(account.Account(k,v))
titles=loadtitle()
contents={'default':'123.txt'}




