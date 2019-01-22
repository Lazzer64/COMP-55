/*
CLASS YellowWizard:
-------------------
Stores information and subroutinues
associated with the Yellow Wizard


FUNCTION attack:
----------------
Calculate a magnitude of the attack from the match size and current combo
The magnitude will be scaled if the match type is *yellow*

If the match was a *pink* match:
    Heal for the magnitude scaled DOWN
Else If the match was a *yellow* match:
    Gain energy equal to the magnitude scaled UP
Else:
    Damage the target equal to the magnitude scaled DOWN
 */
