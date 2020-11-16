package arknights.cards.base;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.NightmarePower;

import arknights.cards.derivations.SwordRain;

/**
 * @author hundun
 * Created on 2020/11/16
 */
public abstract class BaseVanguardDeploy extends AbstractModCard {

    private AbstractCard giveCardPrototype;
    
    public BaseVanguardDeploy(String id, String img, int cost, CardRarity rarity) {
        super(id, img, cost, CardType.ATTACK, rarity, CardTarget.ENEMY);
    }
    
    public void setGiveCardPrototype(AbstractCard giveCard) {
        this.giveCardPrototype = giveCard;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        
        if (giveCardPrototype != null) {
            AbstractCard giveCardInstance = giveCardPrototype.makeCopy();
            if (this.upgraded) {
                giveCardInstance.upgrade();
            }
            addToTop(new ApplyPowerAction(player, player, new NightmarePower(player, 1, giveCardInstance)));
        }
    }
    
    

}
