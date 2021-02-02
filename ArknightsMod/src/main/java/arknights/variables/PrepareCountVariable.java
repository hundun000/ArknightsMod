package arknights.variables;

import basemod.abstracts.DynamicVariable;

import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.cards.base.ArknightsModCard;

public class PrepareCountVariable extends DynamicVariable {

    public PrepareCountVariable() {
    }
    
    @Override
    public String key() {
        return "PC";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        // will not paint green
        return true;
    }

    @Override
    public int value(AbstractCard card) {
        return ((ArknightsModCard) card).getPrepareCount();
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