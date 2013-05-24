# -- coding:gbk --
import sys, time, os, re
import urllib, urllib2, cookielib,Cookie

url="http://www.douban.com"

c=Cookie.SimpleCookie(os.environ["HTTP-COOKIE"]);
c["ck"]="VOAV";
c["dbcl2"]="63136861:k2Qoj9YBn28";

