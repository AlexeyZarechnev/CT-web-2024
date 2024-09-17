`ping -c 1 wp.codeforces.com`

PING wp.codeforces.com (95.163.252.67): 56 data bytes\
64 bytes from 95.163.252.67: icmp_seq=0 ttl=55 time=16.010 ms\

--- wp.codeforces.com ping statistics ---\
1 packets transmitted, 1 packets received, 0.0% packet loss\
round-trip min/avg/max/stddev = 16.010/16.010/16.010/nan ms\

`telnet 95.163.252.67 80`

Trying 95.163.252.67...\
Connected to mx1.codeforces.com.\
Escape character is '^]'.\
GET /1d1p HTTP/1.1\
Host: wp.codeforces.com\

HTTP/1.1 301 Moved Permanently\
Server: nginx/1.16.1\
Date: Tue, 17 Sep 2024 14:15:36 GMT\
Content-Type: text/html\
Content-Length: 169\
Location: http://wp.codeforces.com/1d1p/\
Connection: keep-alive\
X-Information: Set the header 'X-Secret' with the value '6ff6b3'\

```html
<html>
<head><title>301 Moved Permanently</title></head>
<body>
<center><h1>301 Moved Permanently</h1></center>
<hr><center>nginx/1.16.1</center>
</body>
</html>
```

`telnet 95.163.252.67 80`

Trying 95.163.252.67...\
Connected to mx1.codeforces.com.\
Escape character is '^]'.\
GET /1d1p HTTP/1.1\
Host: wp.codeforces.com\
X-Secret: 6ff6b3\

HTTP/1.1 200 OK\
Server: nginx/1.16.1\
Date: Tue, 17 Sep 2024 14:16:54 GMT\
Content-Type: text/plain\
Content-Length: 12\
Last-Modified: Tue, 11 Sep 2018 20:32:50 GMT\
Connection: keep-alive\
ETag: "5b982672-c"\
X-Information: Set the header 'X-Secret' with the value '6ff6b3'\
Accept-Ranges: bytes\

`237482042734`