package arknights.cards.base;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import arknights.characters.Doctor;
import arknights.powers.TurnCountDownGainCardPower;

/**
 * @author hundun
 * Created on 2020/11/16
 */
public abstract class BaseOperatorCard extends AbstractModCard {

    protected int countDownTurnNum;
    protected int cardNum;
    public BaseOperatorCard(String id, String img, int cost, CardRarity rarity) {
        super(id, img, cost, CardType.ATTACK, Doctor.Enums.ARKNIGHTS_OPERATOR_CARD_COLOR, rarity, CardTarget.ENEMY);
        this.countDownTurnNum = 3;
        this.cardNum = 1;
        this.tags.add(ArknightsCardTag.OPERATOR);
        this.cardsToPreview = getGiveCard();
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        
        AbstractCard giveCard = getGiveCard();
        if (giveCard != null) {
            if (this.upgraded) {
                giveCard.upgrade();
            }
            addToTop(new ApplyPowerAction(player, player, new TurnCountDownGainCardPower(player, countDownTurnNum, giveCard, cardNum)));
        }
    }
    
    protected abstract AbstractCard getGiveCard();


}
