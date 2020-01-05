# POO_Project
Voici la version basique dans laquelle vous retrouverez tout ce qui est demand? dans lÅf?nonc? si nous l'avons bien compris.
En ouvrant le jeu vous trouverez un mini menu permettant de :
- lancer une partie avec des options d?termin?es dans la fichier Main ligne 436, il cr?e un royaume avec un Joueur (il faudra conserver un joueur on a pas trait? le cas IA uniquement), 2 IA et 6 Ch?teaux Neutres. Les ch?teaux de joueurs auront 3 piquiers et 2 chevaliers initialement. Ces donn?es peuvent ?tre chang?es mais il faut ?viter de mettre trop de ch?teaux car la g?n?ration al?atoire des ch?teaux pourrait faire une boucle infinie ? cause de la restriction de distance entre les ch?teaux.
- Charger une partie qui a ?t? sauvegard?e (1 seule sauvegarde).

Le jeu lanc? vous verrez une interface qui a ?t? cr??e sur un ordinateur avec donc l'affichage adapt? sa r?solution et on ne s'est pas attard?s a arranger l'affichage pour tous types d'?crans alors avoir une r?solution d'?cran plus grande n'affectera pas le jeu mais ce sera moins adapt?.

Le ch?teau du joueur est bleu.
On peut s?lectionner les ch?teaux ou d?s?lectionner en cliquant dans le fond, en cliquant sur le ch?teau du joueur on peut cliquer sur un autre ch?teau pour envoyer une ost, qui ouvrira en fait un popup qui met le jeu en pause et permet de choisir le nombre de troupes ? envoyer (on peut tr?s bien n'en envoyer aucune).

La touche espace permet de mettre le jeu en pause, la touche L permet, en partie, de charger une partie si le fichier de sauvegarde existe.

Les troupes se d?placent sur 8 axes vers leur cible, ils contournent bien les ch?teaux, elles sortent de leur ch?teau sans se superposer, 3 par 3 au maximum.

Concernant l'IA c'est une basique, on va en faire une deuxi?me pour la version avanc?e qui sera plus intelligente, pour le moment elle a deux cycles :
 - Cycle de production(dur?e al?atoire), le ch?teau cr?e une troupe ou s'am?liore (le niveau max du ch?teau est 5), si possible il fait une am?lioration, sinon cr?e onagre, sinon chevalier, sinon piquier.
 - Cycle d'attaque(dur?e fixe), le ch?teau envoie 1/3 des troupes ? diff?rents intervalles de temps vers le ch?teau adverse le plus proche.
