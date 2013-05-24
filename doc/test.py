import cookielib, urllib2,os,Cookie

cookie=cookielib.LWPCookieJar()
c=Cookie.SimpleCookie();
c.domain=".douban.com"
c.path="/"
c.name="ck"
c.value="VOAV"
c.version="1.0"

n=Cookie.SimpleCookie();
n.domain=".douban.com"
n.path="/"
n.name="dbcl2"
n.value="63136861:k2Qoj9YBn28"
n.version="1.0"
n.httponly="true"
rest={'HttpOnly':True}
#t=cookielib.Cookie(None,"dbcl2",None,"80","80","www.douban.com",None,None,"/",None,False,False,"63136861:k2Qoj9YBn28",None,None,rest,True)
t=cookielib.Cookie(version=0, name='dbcl2', value='63136861:qByMUFQjsQw', port=None, port_specified=False, domain='www.douban.com', domain_specified=False, domain_initial_dot=False, path='/', path_specified=True, secure=False, expires=None, discard=True, comment=None, comment_url=None, rest={'HttpOnly': True}, rfc2109=False)
#c["ck"]["domain"]=".douban.com"
#c["dbcl2"]="63136861:k2Qoj9YBn28"
#cookie.set_cookie(c)
cookie.set_cookie(t)
#print t.value
cookie.save("test")
opener=urllib2.build_opener(urllib2.HTTPCookieProcessor(cookie));
response=opener.open("http://www.douban.com/group/mine")

print response.getcode()

html=response.read();
for item in cookie:
    print item.name+":"+item.value
#print html

