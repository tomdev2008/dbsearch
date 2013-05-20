# -- coding:gbk --
import sys, time, os, re
import urllib, urllib2, cookielib

url="http://www.douban.com"

cookie=cookielib.LWPCookieJar("cookiedata")
opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cookie))

response=opener.open(url)

for c in list(cookie):
    print c.value
