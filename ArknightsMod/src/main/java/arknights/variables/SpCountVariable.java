package arknights.variables;

import basemod.abstracts.DynamicVariable;

import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.cards.base.ArknightsModCard;

public class SpCountVariable extends DynamicVariable {

    public SpCountVariable() {
    }
    
    @Override
    public String key() {
        return "SPC";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        // will not paint green
        return true;
    }

    @Override
    public int value(AbstractCard card) {
        return ((ArknightsModCard) card).getSpCount();
    }

    @Override
    public int baseValue(AbstractCard card) {
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }

}