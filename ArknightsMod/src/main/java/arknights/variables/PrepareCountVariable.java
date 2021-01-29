package arknights.variables;

import basemod.abstracts.DynamicVariable;

import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.cards.base.AbstractModCard;

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
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractModCard) card).getPrepareCount();
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