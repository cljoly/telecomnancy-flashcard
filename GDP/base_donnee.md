# Modèle de la base de donnée

La base de donnée H2 contient 4 tables. Celle-ci sont associées de manière
transparente à des classes Java grâce à ORMLite.

![Schéma de donnée](modele_donnee.png)

## Card

Cette table contient les informations propres au cartes.

L’état d’une carte est un énum java dans notre code, il est stocké sous forme de chaîne de caractère dans la base de donnée.

## Deck

Cette table contient les informations associées aux paquets

## DeckCard

On associe les cartes aux paquets dans cette table, avec les clés étrangères
`deck_id` et `card_id`.

Ceci permettrait d’associer une carte à plusieurs paquets ou à aucun, même si
l’application n’exploite pas cette possibilité et associe toujours une carte à
un et un seul paquet.

## VisitePerDay

Cette table agrège le nombre de carte (`nb_card`) vues dans une journée. Ces informations
sont nécessaires à l’affichage des statistiques.

Les dates sont stockées comme des chaînes de caractères.
