CC = javac

SRC_UDP = ./src/udp
SRC_TCP = ./src/tcp

CLASS_UDP = ./class/udp
CLASS_TCP = ./class/tcp

all: mkdir udpClient udpServer tcpClient tcpServer

tcpClient:
	$(CC) $(SRC_TCP)/tcpClient.java -d $(CLASS_TCP)

tcpServer:
	$(CC) $(SRC_TCP)/tcpServer.java -d $(CLASS_TCP)

udpClient:
	$(CC) $(SRC_UDP)/udpClient.java -d $(CLASS_UDP)

udpServer:
	$(CC) $(SRC_UDP)/udpServer.java -d $(CLASS_UDP)

mkdir:
	mkdir -p $(CLASS_UDP)
	mkdir -p $(CLASS_TCP)

clean:
	rm -rf ./class