package arknights.variables;

import basemod.abstracts.DynamicVariable;

import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.cards.base.ArknightsModCard;

public class RegainBlockVariable extends DynamicVariable {

    public RegainBlockVariable() {
    }
    
    @Override
    public String key() {
        return "RB";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((ArknightsModCard) card).regainBlockModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((ArknightsModCard) card).regainBlock;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((ArknightsModCard) card).baseRegainBlock;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((ArknightsModCard) card).regainBlockUpgraded;
    }

}