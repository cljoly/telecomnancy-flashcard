# Memscarlo

Notre application dispose d’une interface graphique et du modèle
adossé à une base de données. L'application supporte la création de 
paquets et de cartes, leur modification, ainsi que l'apprentissage de paquets avec le 
[système de Leitner](https://fr.wikipedia.org/wiki/Syst%C3%A8me_Leitner). 
De plus, il est possible de créer une carte qui soit réversible. Aussi l'utilisateur a la possibilité d'exporter les paquets créées, ainsi que d'importer des paquets sous format JSON.
Enfin, l'utilisateur peut consulter ses statistiques dans un onglet particulier.



## Lancement de l’application

Nous avons développé Memscarlo avec Java 8. Pour la lancer, faire :

``` sh
$ ./gradlew run
```

Des paquets tests sont disponibles au format JSON dans le dossier **Paquets_de_test**. Pour les importer, il suffit de renseigner leur chemin absolu dans l'onglet d'accueil et de cliquer sur importer.
Le paquet sample2 a déjà subi un apprentissage très léger afin que lors de son import, on puisse voir directement des statistiques.

## Création du jar

``` sh
$ ./gradlew jar
```

## Autre

Nous avons documenté notre code avec Javadoc. Pour générer la documentation :

``` sh
$ ./gradlew javadoc
```

# Groupe 9
- Laury de Donato
- Morgan Ebandza
- Lucas Fenouillet
- Clément Joly

# Fondements de l'applications

## Utilisation d'une base de données

Nous avons pris la décision dès le début du projet d'utiliser une base de données accompagnée d'un ORM pour stocker les données de l'utilisateur de l'application. Pour ce faire nous avons utilisé **ORMLite** qui a l'avantage d'être très bien documenté.

L'utilisation d'un ORM nous a permis notamment de garder les données de l'utilisateurs et de pouvoirs les visualiser de manière claire. La base de données de plus est très adaptée à une utilisation de beaucoup de cartes et decks, nous nous sommes en effet demandés en début de projet de quelle manière nous allions visualiser les données lorsque nous lancerions un test avec un grand jeu de données, il nous est alors apparu que l'utilisation d'une base de données étaient la manière la plus simple de régler tous les problèmes à nos questions.

Ainsi, nous avons pu utiliser des requêtes SQL pour accéder aux éléments de la base de données et pour les inserer dedans plutôt que de devoir créer des fonctions d'écriture et de lecture dans  un fichier qui impacterait fortement les performances et obligerait à lire et écrire dans un fichier.

## Tests 

Toutes les fonctions associées à la base de données sont testées avec **JUnit**.

## Lien entre la base de données et l'interface utilisateur

Pour utiliser et lier la partie interface à la partie modèle principalement conçue autour de la base de données nous avons appliqué le design pattern Façade à la classe **User**. Ainsi toutes les intéractions entre le modèle et l'interface se font grâce à une instance de **User** ce qui nous permet également de mettre à jour la base de données en conséquences en plus des instances des autres classes.

## Accès à l'utilisateur courrant

Dans l'objectif de pouvoir récépurer l'utilisateur courant quel que soit le module ou la classe dans laquelle on se situe, nous avons appliqué le design patter Singleton à la classe **GameUser**. L'élément clé de cette méthode et que grâce à la fonction *static* de cette classe nous pouvons récupérer le joueur courant. 

## Intégration continue 

L’intégration continue (**Gitlab CI**) a été utilisée pour contrôler la bonne exécution des phases de compilation et de test. Elle permet aussi de générer un .JAR de notre application sous la forme d'un artefact



