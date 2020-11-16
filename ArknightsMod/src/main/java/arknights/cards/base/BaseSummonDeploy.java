package arknights.cards.base;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import arknights.orbs.MeeBooAttackTypeOrb;

/**
 * @author hundun
 * Created on 2020/11/10
 */
public abstract class BaseSummonDeploy extends AbstractModCard {

    AbstractOrb summonOrb;
    
    public BaseSummonDeploy(String id, String img, int cost, CardRarity rarity) {
        super(id, img, cost, CardType.SKILL, rarity, CardTarget.SELF);        
    }
    
    public void setSummonOrb(AbstractOrb orb) {
        this.summonOrb = orb;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new ChannelAction(summonOrb.makeCopy()));
        }
    }

}
