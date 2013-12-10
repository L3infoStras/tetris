mkdir tmp
cd tmp
wget http://dl.bintray.com/netty/downloads/netty-4.0.7.Final.tar.bz2
wget http://scala-lang.org/files/archive/scala-2.10.3.tgz

tar xvf scala-2.10.3.tgz
tar xvf netty-4.0.7.Final.tar.bz2

cp netty-4.0.7.Final/jar/all-in-one/netty-all-4.0.7.Final.jar .
cp scala-2.10.3/lib/scala-2.10.3/lib/scala-{swing, library}.jar .

cp ../scalatris_2.10-0.2.jar

echo "Vous pouvez maintenant :"
echo "Lancer le serveur: ../tetris_server.sh"
echo "Lancer l'AI: ../tetris_ai.sh"
