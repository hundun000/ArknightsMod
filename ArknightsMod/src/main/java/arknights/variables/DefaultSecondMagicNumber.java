package arknights.variables;

import basemod.abstracts.DynamicVariable;

import static arknights.DefaultMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.cards.base.AbstractModCard;

public class DefaultSecondMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("2ndM");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractModCard) card).extraMagicNumberModifieds[0];
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractModCard) card).extraMagicNumbers[0];
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractModCard) card).baseExtraMagicNumbers[0];
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractModCard) card).upgradedExtraMagicNumbers[0];
    }
}