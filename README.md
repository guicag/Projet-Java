# Projet Mars

Un robot vient d'atterrir sur Mars ! Un panorama désertique et une planète peu accueillante par les robots
concurrents. Voici l'intrigue de ce projet...

**Partie 1 - Exploiration sur Mars :**  
Implémentation d'un robot qui doit collecter différent minérais en fonction d'une carte de la planète, d'équipements futuristes comme des batteries aux vibranium ou encore des laser Adegan.

* [Documentation Conception]()
* [Documentation Technique]()


**Partie 2 - Combats :**   
Implémentation d'un drone de défense pour se défendre des ennemis !!!

* [Documentation Conception]()
* [Documentation Technique]()

## Installation Projet

Avant de commencer, petit [tuto](https://rogerdudler.github.io/git-guide/index.fr.html) sur git :)


- Génerer une clé SSH avec la commande --> `ssh-keygen`
- Ouvrir le fichier qui contient la clé --> `cat ~/.ssh/id_rsa.pub`
- Copier la clé 
- Aller sur le profil dans l'onglet ssh key puis ajouter la clé
- Cloner le projet avec la commande --> `GIT_SSL_NO_VERIFY=true git clone ssh://git@rodez.3il.fr:51200/Promotion_19/Promotion_19_-_Projet_Java_-_Equipe_Tatou/projet-java-objectif-mars.git`

## Installation JavaFX

- Telecharger e(fx) dans le marketplace d'Eclispe, puis redemarrer Eclipse
- Download la librairie JavaFX Windows SDK [lien](https://gluonhq.com/products/javafx/), telecharger la librairie dans un dossier (ex : JavaFX)
- Dans Eclipse, Windows/Preference/Build Path/User librairies/New, donner un nom (ex :JavaFX)
- Puis cliquer sur cette librairie, importer avec le bouton Add External JARs, selectionner les fichiers .jar qui sont dans le sdkJavaFx/lib
- Ensuite cliquer droit sur le projet; Build Path -> Configure -> Librairie -> Cliquer sur ClassPath -> Add librairie -> User Librairie et selectionner votre librairie
- Aller dans votre main ou vous lancez votre application, Run -> Run Configuration -> Argument  -> Ajouter '--module-path "votre Chemin ...dkJavaFX/Lib " --add-modules javafx.controls,javafx.fxml'

## Installation Scene Builder

- Telecharger Scene Builder [lien](https://gluonhq.com/products/scene-builder/#download) dans le meme dossier crée précédemment
- Retourner sur Eclipse, Windows -> Preference -> JavaFx -> Mettre le chemin du .exe Scene Builder
- Maintenant vous pouvez ouvrir les fichier fxml avec Scene Builder en faisant un clique droit sur le fichier, Ouvrir avec Scene Builder

Un lien qui resume l'installation [video](https://www.youtube.com/watch?v=bC4XB6JAaoU)


## Auteurs

* **Clément Jouret** - *Chef de projet/Développeur* - Mail : clementjouret@hotmail.fr - [GitLab](https://rodez.3il.fr/gitlab/c.jouret.19)
* **Paul Espinosa** - *Développeur* - Mail : p.espinosa11@hotmail.fr - [GitLab](https://rodez.3il.fr/gitlab/p.espinosa.19)
* **Théo Bouviala** - *Développeur* - Mail : theobouviala@gmail.com - [GitLab](https://rodez.3il.fr/gitlab/t.bouviala.19)
* **Guillaume Cagniard** - *Développeur* - Mail : gui.cagniard@gmail.com - [GitLab](https://rodez.3il.fr/gitlab/g.cagniard.19)

## License

Ce projet réalisé dans le cadre de notre formation d'Ingénieur Informatique à 3iL afin de valider le module de Java sous la direction de notre excellent professeur M. Philippe Roussille



