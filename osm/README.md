# Sujet: La cartographie avec OpenStreetMap

Dans ce projet, nous allons créer et utiliser des services autour de la cartographie digitale. Pour commencer, on va à la base de ces services : la carte.

Comme vous avez pu le voir pendant le cours, il n'est pas simple de gérer la donnée OSM, c'est pour cela qu'on n'ira pas jusqu'à posséder une base OSM. À la place, nous allons utiliser un SVG du monde qui sera tuilé. J'ai déjà créé le service de tuilage, vous n'aurez qu'à appeler cette méthode.

La première partie consiste à créer son serveur de tuiles.

## Prérequis

Pour commencer vous devez déjà avoir [IntelliJ Idea Community ou Ultimate](https://www.jetbrains.com/idea/download/#section=linux) d'installé avec Java.
Vous pouvez utiliser d'autres IDE, mais je ne pourrais pas vous aider... (Non, Atom n'est pas un IDE...)

### Récuperer le code

Vous pouvez cloner le projet avec git `git clone https://github.com/Joxit/IG-Master2` ou télécherger le [zip](https://github.com/Joxit/IG-Master2/archive/master.zip).
Ouvre IntelliJ, cliquez sur `File` -> `New` -> `Project From Existing Sources`. Cela ouvre une fenêtre, naviguez jusqu'au projet et selectionnez le fichier `osm/build.gradle.kts`.
Cela ouvre une fenêtre avec le projet configuré avec Gradle.

Si vous n'avez pas Java de configuré, vous pouvez séléctionner le SDK à utiliser, pour cela cliquez sur `File` -> `Project Structure` -> `Project`. Vous verrez peut-être que vous verrez `<No SDK>`, cliquez dessus et selectionnez une version de Java (s'il y en a), sinon `Add SDK` -> `JDK`. Cela ouvre une fenêtre, naviguez jusuq'à l'endroit où il y a votre JDK. 

Vérifiez que le SDK utilisé par le projet est le même que celui qu'utilisera gradle. Pour cela allez dans `File` -> `Settings`. Cela ouvre une nouvelle fenêtre, sur la gauche séléctionnez `Build, Execution, Deployment` -> `Build Tool` -> `Gradle` et séléctionnez une version de JVM identique à celui du projet (verifiez que c'est positionné sur `Project SDK`).

Pour vérifier que tout fonctionne correctement, vous pouvez lancer le projet, sur la partie de gauche (Projet), ouvrez le projet `osm` -> `osm-boot` -> `src` -> `main` -> `java` -> `io.github.joxit.osm`. Faites un clique droit sur Application puis `Run Application.main()`.

Dès que vous avez dans la console quelque chose ressemblant à cela, c'est que votre serveur est démarré. Un [serveur](https://fr.wikipedia.org/wiki/Serveur_informatique) fournit donc des services à un ou plusieurs clients. Pour ce faire il doit resté démarré sans s'arrêter... Donc si vous voyez que ça ne bouge pas c'est totalement normal.... Il fait déjà son travail !

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.4.RELEASE)

2020-11-02 07:11:55.234  INFO 8 --- [           main] io.github.joxit.osm.Application          : Starting Application v1.0-SNAPSHOT on 333bac29f1ba with PID 8 (/usr/src/osm/osm-boot.jar started by root in /usr/src/osm)
2020-11-02 07:11:55.243  INFO 8 --- [           main] io.github.joxit.osm.Application          : No active profile set, falling back to default profiles: default
2020-11-02 07:11:55.385  WARN 8 --- [kground-preinit] o.s.h.c.j.Jackson2ObjectMapperBuilder    : For Jackson Kotlin classes support please add "com.fasterxml.jackson.module:jackson-module-kotlin" to the classpath
2020-11-02 07:11:56.844  INFO 8 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2020-11-02 07:11:56.863  INFO 8 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2020-11-02 07:11:56.863  INFO 8 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.38]
2020-11-02 07:11:56.955  INFO 8 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2020-11-02 07:11:56.955  INFO 8 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1557 ms
2020-11-02 07:11:57.623  INFO 8 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2020-11-02 07:11:57.639  INFO 8 --- [           main] io.github.joxit.osm.Application          : Started Application in 3.349 seconds (JVM running for 4.319)
2020-11-02 07:13:35.754  INFO 8 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2020-11-02 07:13:35.754  INFO 8 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2020-11-02 07:13:35.769  INFO 8 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 15 ms
```

Vous pourrez contacter le serveur sur le port 8080. Si vous essayez d'aller sur <http://localhost:8080/>, vous allez voir une page avec écrit `Whitelabel Error Page`, c'est que le serveur répond, vous pouvez commencer à coder! Pour le moment si vous voulez voir la carte, cela ne marchera pas car vous n'avez pas encore dit à votre serveur de renvoyer les morceaux de tuile !

## Sujet du TP

### Le serveur de tuiles

Pour le serveur, nous allons utiliser Spring Boot, la base de code est disponible dans osm-boot. Voici quelques étapes que vous pouvez suivre pour vous aider.

  1. Créez le `RestControlleur` et vos endpoints, typiquement le `/{z}/{x}/{y}.png` vu en cours. Hint: `TileController`.
  2. Créez votre `Service` qui va appeler `Svg.getTile(Tile t)`. Hint: `TileService`.
  3. Utilisez votre service créé juste avant dans votre contrôleur.
  4. Faites de la validation sur les tuiles dans votre Service. Hint: `IllegalArgumentException`, nombres négatifs, valeurs de x et y trop grands, z ne doit pas dépasser 24.
  5. Renvoyez des codes d'erreur 400 avec votre validation. Hint: `ControllerAdvice`, `ExceptionHandler`.
  6. Bonus: Utilisez un cache pour ne pas à avoir à générer les tuiles à chaque fois.
  7. Bonus: Améliorez le code de la classe `Svg` pour améliorer les perfs. Hint: Attention à l'Input-Output ;).
  8. Démarrez votre serveur de tuiles. Hint: Il écoutera sur le port 8080, <http://127.0.0.1:8080/0/0/0.png> devrait fonctionner.

Maintenant vous avez toutes les fonctionnalités de base d'un serveur de tuiles. Maintenant il faut pouvoir l'afficher, pour cela il faut page web qui pourra afficher votre carte.

### Afficher une carte

Pour cela, nous allons utiliser une librairie nommée [Mapbox GL JS](https://docs.mapbox.com/mapbox-gl-js/api/), il y a d'autres alternatives comme [Leaflet](https://leafletjs.com/).

Tout a déjà été fait, nous n'allons nous retarder sur du dev front/carto. Vous pouvez utiliser le résultat [ici](https://joxit.dev/IG-Master2/osm/osm-ui/?url=http://127.0.0.1:8080). Vous pouvez utiliser le query parameter `url=http://127.0.0.1:8080` ou un autre si votre serveur est sur un autre port.

### Ajouter des points

Maintenant nous allons ajouter des points à la carte. Nous avons à disposition la liste des préfectures de France au format GeoJSON. Le but sera de renvoyer cette liste via notre API de tuiles.

  1. Créez un endpoint pour servire le GeoJSON. Hint: `TileController`.
  2. Ajoutez le code pour récupérer le GeoJSON des ressources. Hint: Vous pouvez vous inspirer de `Svg` ou utilisez `@Value` de Spring, faites tout cela dans `TileService`. Attention la méthode [`getFile`](https://stackoverflow.com/questions/14876836/file-inside-jar-is-not-visible-for-spring) est à proscrire quand on utilise des fichiers dans le classpath.
  3. Utilisez votre Service dans votre Controlleur.
  4. Bonus: Créez une persistance pour vos points avec une base de données pour les récupérer. Hint: Utilisez PostgreSQL avec l'extension PostGIS, il y a un type `Geometry` spécial, vous avez le choix entre Hibernate et JDBCTemplate.
  5. Bonus: Vous devez renvoyer un GeoJSON, vous avez plusieurs solutions, soit récuperer du GeoJSON via [PostgreSQL/PostGIS](https://postgis.net/docs/ST_AsGeoJSON.html), soit utiliser une [librairie GeoJSON](https://github.com/ngageoint/simple-features-geojson-java)
