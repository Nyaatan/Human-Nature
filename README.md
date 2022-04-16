# Human-Nature
A project created for Object-oriented Programming classes at Wrocław University of Science and Technology. 

It's a minimalistic, turn-based survival game, where the world is represented by a hexagonal board. The objective is to survive in a hostile, infinitely-generated world filled with both flora and fauna. Every action the player takes marks end of a turn and each element of the world makes their action like moving, attacking or reproducing.

## Encounters
Upon entering a tile on the map where there is already a being one of three events can occur.
* Aggresive - a fight breaks out and the one with higher strength wins
* Neutral - the tile cannot be accessed
* Reproduction - if two animals of the same kind collide, an offspring is spawned

Type of an encounter may depend on player's equipment, e.g. colliding with a tree with bare hands invokes *neutral* event and results in gathering branches whilist having equipped an axe player invokes aggressive encounter and kills the tree in exchange of gathering wood.

## Crafting
Upon collecting enough materials from other beings player can craft new equipment that may help in survival.

## Life cycle
Every being has defined it's chance of natural death in given turn based on it's age, it's expected living age and UK Mortality Statistics for year 2005. It may happen that a wolf, expected to live 20 turns, will die aged 4 turns or 24 turns.
