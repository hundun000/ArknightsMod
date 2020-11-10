package arknights.variables;

import basemod.abstracts.DynamicVariable;

import static arknights.DefaultMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.cards.HasSecondMagicNumberAbstractCard;

public class DefaultSecondMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("SecondMagic");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((HasSecondMagicNumberAbstractCard) card).secondMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((HasSecondMagicNumberAbstractCard) card).secondMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((HasSecondMagicNumberAbstractCard) card).baseSecondMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((HasSecondMagicNumberAbstractCard) card).upgradedSecondMagicNumber;
    }
}