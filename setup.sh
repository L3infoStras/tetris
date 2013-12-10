#!/bin/sh
mkdir tmp
cd tmp
wget -c http://dl.bintray.com/netty/downloads/netty-3.7.0.Final-dist.tar.bz2
wget -c http://scala-lang.org/files/archive/scala-2.10.3.tgz

tar xvf scala-2.10.3.tgz
tar xvf netty-3.7.0.Final-dist.tar.bz2

cp scala-2.10.3/lib/scala-swing.jar .
cp scala-2.10.3/lib/scala-library.jar .

cp netty-3.7.0.Final/jar/netty-3.7.0.Final.jar .

cp ../scalatris_2.10-0.2.jar .

echo "Vous pouvez maintenant :"
echo "Lancer le serveur: cd tmp; ../tetris_server.sh"
echo "Lancer l'AI: cd tmp; ../tetris_ai.sh"
