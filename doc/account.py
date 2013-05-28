#!/usr/bin/python
# -*- coding: GBK -*-
import cookielib, urllib2,os,Cookie
from lxml import etree
import re,time
import random
import urllib

class Account:
	def __init__(self,email,dbcl2):
		self.email=email
		self.dbcl2=dbcl2
		self.expiretime=time.time()
		
		cookie=cookielib.LWPCookieJar()
		t=cookielib.Cookie(version=0, name='dbcl2', value=self.dbcl2, port=None, port_specified=False, domain='www.douban.com', domain_specified=False, domain_initial_dot=False, path='/', path_specified=True, secure=False, expires=None, discard=True, comment=None, comment_url=None, rest={'HttpOnly': True}, rfc2109=False)
		cookie.set_cookie(t)
		self.opener=urllib2.build_opener(urllib2.HTTPCookieProcessor(cookie));
		self.opener.open("http://www.douban.com")
		self.ck=''
		for c in list(cookie):
			if c.name=='ck':
				self.ck=c.value.replace('"','')
		self.groups=[]
		self.getmygroup()
		
	def getmygroup(self):
		url='http://www.douban.com/group/mine'
		response=self.opener.open(url)
		html=response.read()
		root=etree.HTML(html)
		nodes=root.xpath('//*[@id="content"]/div/div[1]/div/div/div/ul/li/div[2]/a')
		for node in nodes:
			self.groups.append(node.get("href"))
		
	def addgroup(self,url):
		url=url+'?action=join&ck='+self.ck
		response=self.opener.open(url)
		print 'mail:'+self.email+' add '+url
		time.sleep(1)
		
	def removegroup(self,url):
		url=url+'?action=quit&ck='+self.ck
		response=self.opener.open(url)
		print 'remove '+url
		time.sleep(1)
	
	def removeallgroup(self):
		groups=self.groups
		for l in groups:
			self.removegroup(l)		
	
	def addtopic(self,title,content):
		if len(self.groups) <=0 :
			self.getmygroup()
		group=self.groups[0]
		url=group+"new_topic"
		p={}
		p["ck"]=self.ck
		p["rev_title"]=title
		p["rev_text"]=content
		p["rev_submit"]="r"
		
		request=urllib2.Request(url)
		request.add_header("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.57 Safari/536.11")
		request.add_header("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3")
		request.add_header("Origin", "http://www.douban.com")
		request.add_header("Referer", group)
		response=self.opener.open(request,urllib.urlencode(p))
		print time.ctime()+" "+self.email+" "+url
		self.expiretime=time.time()+15*60
		del self.groups[0]
#		if response.getcode()==200:
#			self.expiretime=time.time()+20*3600
#			print self.email+' fail'
#		else:
#			self.expiretime=time.time()+10*3600
#			print self.email + 'success'
#			del self.groups[0]

	def addmoregroup(self,url):
#		url='http://www.douban.com/group/explore?start='+start+'&tag=%e6%81%8b%e7%88%b1'
		response=self.opener.open(url)
		html=response.read()
		root=etree.HTML(html)
		nodes=root.xpath('//*[@id="content"]/div/div[1]/div[1]/div/div/div/h3/a')
		res=[]
		for node in nodes:
			self.addgroup(node.get("href"))
#			res.append(node.get("href"))

if __name__ == '__main__':
	account=Account('72538099:9ujSxqAJjBQ');
	print account.groups


#def addmoregroup():
#	start=0
#	while start <= 100:
#		list=searchgroup(str(start))
#		for l in list:
#			addgroup(l,ck)
#		time.sleep(1)
#		start+=20

#
#
#def randcookie():
#	key=randomitem(accounts)
#	return accounts[key]
#
#def randtopic():
#	t=randomlist(titles)
#	key=randomitem(contents)
#	c=contents[key]
#	filehandle=open(c)
#	cc=filehandle.read()
#	filehandle.close()
#	return t,cc
#
#def randomitem(tmplist):
#	length=len(tmplist)
#	ran=random.randint(1,length)
#	i=1
#	for a,v in tmplist.items():
#		if i==ran:
#			return a
#	return 'default'
#def randomlist(tmplist):
#	length=len(tmplist)
#	ran=random.randint(0,length-1)
#	return tmplist[ran]
#
#def loadtitle():
#	file=open("title.txt")
#	lines=file.readlines()
#	file.close()
#	return lines
#
#def addtopic(group,title,content):
#	url=group+"new_topic"
#	p={}
#	p["ck"]=ck
#	p["rev_title"]=title
#	p["rev_text"]=content
#	p["rev_submit"]="r"
#	print p
#	print url
#	request=urllib2.Request(url)
#	request.add_header("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.57 Safari/536.11")
#	request.add_header("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3")
#	request.add_header("Origin", "http://www.douban.com")
#	request.add_header("Referer", group)
#	opener.open(request,urllib.urlencode(p))
#
#def sendtopic():
#	groups=getmygroup()
#	for g in groups:
#		st,sc=randtopic()
#		addtopic(g,st,sc)
#		time.sleep(120)


	
#cookie=cookielib.LWPCookieJar()
##dbvalue='72538099:9ujSxqAJjBQ'
#dbvalue='63136861:YvNlHJwJdGc'
#
#t=cookielib.Cookie(version=0, name='dbcl2', value=dbvalue, port=None, port_specified=False, domain='www.douban.com', domain_specified=False, domain_initial_dot=False, path='/', path_specified=True, secure=False, expires=None, discard=True, comment=None, comment_url=None, rest={'HttpOnly': True}, rfc2109=False)
#
#cookie.set_cookie(t)
#opener=urllib2.build_opener(urllib2.HTTPCookieProcessor(cookie));
#opener.open("http://www.douban.com")
#ck=''
#
#for c in list(cookie):
#	if c.name=='ck':
#		ck=c.value.replace('"','')
#
##removeallgroup()
#
#accounts={'default':'72538099:9ujSxqAJjBQ',
#	'1505014246@qq.com':'72538099:9ujSxqAJjBQ'}
#
#titles=loadtitle()
#contents={'default':'123.txt'}
#
##removeallgroup()
##addmoregroup()
#sendtopic()
