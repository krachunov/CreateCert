This program automates the process of creating certificates
Program consists of two parts - a client side and a server side
The client side send request into server part using Data Input/Output Stream and the server side connect with Oracle DB with JDBC and get necessary info. After that server part create certificate file, save certificate information into SQL DB and sent back information to clients part.
Client side, get information from server and send mail using javax.mail
