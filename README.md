tetris
======

Tetris avec intelligence artificielle

Façon rapide de lancer le programme avec java
=============================================

Le port 9000 doit être ouvert...

$ ./setup.sh # explications dans la derniere section

$ cd tmp; ./tetris_server.sh

$ cd tmp; ./tetris_ai.sh # (autre terminal)


Lancer le programme avec scala
==============================

 - installer sbt: [http://repo.scala-sbt.org/scalasbt/sbt-native-packages/org/scala-sbt/sbt/0.13.0/sbt.deb]

 (autres distributions / OS : [http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html]

 - lancer 'sbt'

 - taper 'run'

 - choisir le main à lancer (serveur ou IA)


Compiler le programme pour le lancer avec java
==============================================


 - sbt package # crée  dans target/scala-2.10

Pour l'exécution, il faut les bibliotheques standard de scala, et les
bindings swing : scala-library.jar et scala-swing.jar.

Ils sont récupérables ici : [http://scala-lang.org/files/archive/scala-2.10.3.tgz dans le dossier lib/

Il faut également netty: [http://dl.bintray.com/netty/downloads/netty-3.7.0.Final-dist.tar.bz2]

Vous pouvez également exécuter le script "setup.sh", qui telecharge et met en place les dépendances dans le dossier tmp/. 

 - java -cp scala-library.jar:scala-swing.jar:scalatris_2.10-0.2.jar scalatris.server.ScalatrisServer

 - java -cp scala-library.jar:scala-swing.jar:scalatris_2.10-0.2.jar scalatris.clientAI.AIClient

Des scripts sont fournis: tetris_server.sh et tetris_ai.sh