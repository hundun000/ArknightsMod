package arknights.cards.base;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

/**
 * @author hundun
 * Created on 2020/11/10
 */
public abstract class BaseSummon extends AbstractModCard {

    public BaseSummon(String id, String img, int cost, CardRarity rarity) {
        super(id, img, cost, CardType.SKILL, rarity, CardTarget.SELF);        
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new ChannelAction(getSummonOrb()));
        }
    }
    
    protected abstract AbstractOrb getSummonOrb();

}
