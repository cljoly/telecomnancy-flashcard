# Jour 4 : spécification et choix d'implémentation

## Planning de la journée

- Simplifier l’onglet de recherche : retirer les filtres **Laury**
- Changer la création des cartes réversible **Laury**
- Màj des 
- Extraction d’information de la base (méthdes dans User)
  - nombre de carte par paquet et par état **Clément**
  - nombre de carte total **Clément**
  - suppression de carte avec l’association **Morgan**
  - suppression d’un paquet (supprimer toutes les cartes associées et toutes les associations) **Morgan**
  - modification d’une carte (verso non unique , set verso), modification deck (set description) **Clément**
- Amélioration des stats **Lucas**

## Interface d’affichage des statistiques

L’interface d’affichage des statistiques a été spécifiée. L’idée de base est
d’utiliser deux graphiques, l’un pour le nombre de carte vue pour chaque jour et
l’autre illustrant les états des cartes dans chaque paquet (apprises, en cours d’apprentissage, jamais vues…).

![Spécification de l’interface des statistiques]

## Bilan de la journée

Toutes les tâches du planning ont été achevées.

Nous sommes allés plus vite que prévu et nous avons donc implanté certaines
extensions : ajout d’un manuel utilisateur, export des données.

Nous avons également travaillé sur l’intégration continue, sans grand succès.
