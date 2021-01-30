package arknights.variables;

import basemod.abstracts.DynamicVariable;

import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.cards.base.AbstractModCard;

public class UseCountVariable extends DynamicVariable {

    public UseCountVariable() {
    }
    
    @Override
    public String key() {
        return "UC";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        // will not paint green
        return true;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractModCard) card).getUseTimeCount();
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