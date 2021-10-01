# IG-Master2 - Langages et Environements Évolués

Prérequis:

- [java JDK 11](https://adoptopenjdk.net/) (n'oubliez pas de configurer votre JAVA_HOME)
- [docker](https://docs.docker.com/install/#supported-platforms) (n'oubliez pas de lancer la commande `sudo usermod -aG docker $(whoami)` puis redemarrez votre ordinateur)
- [docker-compose](https://docs.docker.com/compose/install/)
- [nodejs](https://nodejs.org/en/download/package-manager/)

## Cours 1: Introduction à Spring et Hibernate

Le support du cours se trouve [ici](https://joxit.dev/IG-Master2/pizzeria/).

## Cours 2: La cartographie avec OpenStreetMap

Le support du cours se trouve [ici](https://joxit.dev/IG-Master2/osm/).

## FAQ

- Comment installer Java 11 ?
  - En function de votre OS, vous pouvez l'avoir via les repository (`sudo apt get install openjdk-11-jdk` sous Debian/Ubuntu) ou en téléchargeant sur openjdk.java.net. En général les SDK java sont rangés ici : `/usr/lib/jvm/`.
- Comment configurer mon JAVA_HOME ?
  - Pour qu'il soit disponible dans notre shell, vous devez ajouter cette ligne dans votre fichier `$HOME/.profile`: `export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/` (modifiez bien le chemin pour mettre le votre). Fermez votre session puis reconnectez vous pour que ça prenne effet. Vérifiez avec `echo $JAVA_HOME`
- Quand je lance docker-compose j'ai une erreur qui contient `('Connection aborted.', PermissionError(13, 'Permission denied'))`
  - Docker requier des droits administrateur pour s'executer ou sinon appartenir au groupe Docker. Pour cela lancez la commande `sudo usermod -aG docker $(whoami)`. Quand vous lancez la commande `groups` vous devriez voir `docker` dans la liste.
- J'ai lancé mon application et j'ai comme erreur `BindException: Adresse déjà utilisée`.
  - Cela signifie qu'une autre application est déjà executé sur votre ordinateur et écoute le même port. Vous devez l'arrêter (un container ? regardez avec `docker ps`. Via votre IDE ? Regardez si vous ne voyez rien dans les onglets `Run` et `Debug`).
- Qu'est-ce que `docker-compose` ?
  - `docker-compose` est un outil pour executer plusieurs container docker. C'est une surcouche à `docker` car il décrit dans un fichier YAML toutes les options que doivent avoir vos containers. C'est très pratique car vous n'aurez pas à apprendre les commandes docker pour démarrer la base de donnée par exemple.
- Le programme a n'a pas l'air de s'arrêter, est-ce normal ?
  - oui, c'est un serveur, il n'est pas censé s'arrêter car il doit répondre à des requêtes clientes. Allez sur un navigateur web et regardez si http://127.0.0.1:8080 vous renvoie quelque chose