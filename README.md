First of all:
In this project I used already created Restful server, from Seminar 6.
Also I used some examples from https://github.com/caprica/vlcj-examples

One very important thing, to run this poject correctly you need to download Vlc player but with the same bits version of your JVM.

Some instructions to run a project:
To run this program you need a Maven and Vlc player.
1. Download(Clone) a branch.
2. Open your terminal and go to directory with files.
3. To start Server or Client just run a .bat file.
By default server is running on localhost and listenint 8080 port, you can change it in .bat file. But don`t forget to write new server ip and port to client.


And the list of commands that implemented in this project and their syntax:
- ping
- echo ***some text***
- exit
- login ***Login*** ***Password*** //Login or Register in Server
- list //Return the list of currently available content
- stream ***Destination Port*** ***ID content*** //Send a request to server, to start stream certain content
