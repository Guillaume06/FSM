# Projet FSM
## Structure du projet
**src** : contiens les fichiers sources qui génèrent le code de la FSM à partir d'un fichierscxml

**test** : contiens des tests, avec un dossier par test (voir section tests)
## Fonctionnalités supportées
* Transitions
    * Trigger avec un event
    * Trigger sans event *(dans ce cas on ne dois pas avoir d'event qui trigger une autre transition en même temps)*
* Déclaration état initial
    * Par la première balise
    * Si rien dans la première balise, prends le premier état déclaré comme état initial
* Log
* Send
* Multiples actions dans un onentry
* Multiples actions dans un onexit
* Liaison avec le code métier (voir section appropriée)

## Liaison avec le code métier
Pour un exemple compler regarder le test *buisnessCodeCall*


Pour la liaison avec le code métier, elle se fait dans le fichier *Main.java* situé dans *GeneratedCode*, il faut (dans set ordre):
* Créer la FSM : *FSM m = new FSM();*
* Lier des fonctions à des éléments (si besoin) *m.register("Nom de l'event pour lequel il y aura trigger", classe contenant la fonction, fonction);*
    * **OBLIGATOIRE** initialiser la FSM
    * Envoyer des events à la FSM *m.submitEvent("Nom de l'event");*

## Utilisation
### Tests déjà présents
Dans le dossier *test* se trouvent les différents tests répartis dans leurs dossiers respectifs.

Excepté le dossier perso qui est prévu pour tester ses propres chartes.

Chaque dossier comprends :
* Un compile.sh qui sers pour compiler tous les fichiers nécéssaires
* Un execute.sh qui sers à lancer la FSM
* Un Readme.md décrivant l'utilité du test
* **OBLIGATOIRE** *StateChart.scxml* qui contiens la FSM au format scxml
* **OBLIGATOIRE** Un dossier *Generatedcode* qui contiens : 
    * *FSM.java* : code généré à partir de *StateChart.xml*
    * **OBLIGATOIRE** *Main.java* qui contiens le fichier client qui crée la FSM et contiens les associations event/fonctions et par lequel on interagit avec la FSM (envoi d'event)
* Un dossier *out* généré automatiquement qui est utilisé pour la compilation des sources

### Mettre ses propres scxml
Deux solutions sont possibles :
* Utiliser le dossier *perso* prévu à cet effet dans le dossier *test*, il suffit juste de mettre son scxml dans *StateChart.xml*, ainsi que le code métier dans *Main.java* situé dans *GeneratedCode*
* Faire son popre dossier au même niveau que les autres dossiers de tests, le plus simple est de copier-coller un dossier de test existant et de changer le code (mêmes fichiers à modifier que pour la première méthode)


## Domaine d'utilisation
Pourquoi choisir de faire du java vers le java?
Je fais régulièrement du JDR, et j'ai déjà fait de petits programmes en Java pour m'assister lorsque je mène des parties (statistiques initiales des personnages en fonction de leurs classes et races, ...).

Les FSM sont intéréssantes pour simuler les différentes évolutions possibles d'un scénario, en injectant dedans les décisions faites par les joueurs.

Mes autres programmes étant aussi en java, faire celui-ci en java me permet de l'intégrer plus facilement aux autres, de plus java ayant une bonne portabilité, je peux avoir mes applications sur moi facilement, même si je suis sur une machine différente.