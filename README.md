# Memscarlo

Notre application dispose pour l’instant d’une interface graphique et du modèle
adossé à une base de données. L'interface graphique supporte la création de 
paquets et de cartes, ainsi que l'apprentissage de paquets avec le 
[système de Leitner](https://fr.wikipedia.org/wiki/Syst%C3%A8me_Leitner)

Nous avons utilisé une **base de donnée** et testé avec **JUnit**. L’intégration continue (**Gitlab CI**) a été utilisée pour contrôler la bonne exécution des phases de compilation et de test.

Enfin, le dernier jour a été en grande partie consacré au test de l’application et à la gestion des cas limites (appuit sur le boutton de modification de description d’une carte quand aucune carte n’est sélectionnée par exemple).

## Lancement de l’application

Nous avons développé Memscarlo avec Java 8. Pour la lancer, faire :

``` sh
$ ./gradlew run
```

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
