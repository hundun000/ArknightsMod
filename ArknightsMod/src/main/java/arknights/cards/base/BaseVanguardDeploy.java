package arknights.cards.base;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NightmarePower;

/**
 * @author hundun
 * Created on 2020/11/16
 */
public abstract class BaseVanguardDeploy extends AbstractModCard {

    public BaseVanguardDeploy(String id, String img, int cost, CardRarity rarity) {
        super(id, img, cost, CardType.ATTACK, rarity, CardTarget.ENEMY);
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        
        AbstractCard giveCard = getGiveCard();
        if (giveCard != null) {
            if (this.upgraded) {
                giveCard.upgrade();
            }
            addToTop(new ApplyPowerAction(player, player, new NightmarePower(player, 1, giveCard)));
        }
    }
    
    protected AbstractCard getGiveCard(){
        return null;
    }

}
