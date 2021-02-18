package arknights.variables;

import basemod.abstracts.DynamicVariable;

import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.cards.base.ArknightsModCard;

public class SpThresholdVariable extends DynamicVariable {

    public SpThresholdVariable() {
    }
    
    @Override
    public String key() {
        return "SPT";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        // will not paint green
        return false;
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