# POO_Project
Voici la version avancée dans laquelle vous retrouverez tout ce qui est demandé dans l’énoncé si nous l'avons bien compris.
En ouvrant le jeu vous trouverez un mini menu permettant de :
- lancer une partie avec des options déterminées dans la fichier Main ligne 436, il crée un royaume avec un Joueur (il faudra conserver un joueur on a pas traité le cas IA uniquement), 2 IA et 6 Châteaux Neutres. Les châteaux de joueurs auront 3 piquiers et 2 chevaliers initialement. Ces données peuvent être changées mais il faut éviter de mettre trop de châteaux car la génération aléatoire des châteaux pourrait faire une boucle infinie à cause de la restriction de distance entre les châteaux.
- Charger une partie qui a été sauvegardée (1 seule sauvegarde).

Le jeu lancé vous verrez un interface qui a été crée sur un ordinateur avec donc l'affichage adaptée à sa résolution et on ne s'est pas attardés a arranger l'affichage pour tous types d'écrans alors avoir une résolution d'écran plus grande n'affectera pas le jeu mais ce sera moins adapté.

Le château du joueur est bleu.
On peut sélectionner les châteaux ou désélectionner en cliquant dans le fond, en cliquant sur le château du joueur on peut cliquer sur un autre château pour envoyer une ost, qui ouvrira en faite un popup qui met le jeu en pause et permet de choisir le nombre de troupes à envoyer (on peut très bien n'en envoyer aucune).
Il y a désormais des casernes en plus des autres troupes, elles permettent de produire des troupes en même temps sur plusieurs file d'attentes
De plus, des boucliers sont mis a disposition en début de jeu, ils permettent de tanker un nombre important de coups mais il ne peux y en avoir qu'un seul d'actif a la fois.
Pour le réparer il faut donc attendre qu'il soit brisé.
Les barons neutres, auront ou n'auront pas de bouclier, c'est aléatoire.

La touche espace permet de mettre le jeu en pause, la touche L permet en partie de charger une partie si le fichier de sauvegarde existe.

Les troupes se déplacent sur 8 axes vers leur cible, ils contournent bien les châteaux, elles sortent de leur château sans se superposer par 3 au maximum.

Concernant l'IA c'est une basique, on va en faire une deuxième pour la version avancée qui sera plus intelligente, pour le moment elle a deux cycles :
 - Cycle de production(durée aléatoire), le château crée une troupe ou s'améliore (le niveau max du château est 5), si possible il fait une amélioration, sinon crée onagre, sinon chevalier sinon piquier.
 - Cycle d'attaque(durée fixe), le château envoie 1/3 des troupes à différents intervalles de temps vers le château adverse le plus proche.