package arknights.cards.base;


import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.actions.DiscoveryTargetCardsAction;
import arknights.cards.operator.PromotionState;
import arknights.characters.ArknightsPlayer;
import arknights.powers.TurnCountDownGainCardPower;

/**
 * @author hundun
 * Created on 2020/11/16
 */
public abstract class BaseDeployCard extends ArknightsModCard {

    protected List<AbstractCard> giveCards;
    
    
    
    public BaseDeployCard(String id, String img) {
        super(id, img, 0, CardType.SKILL, ArknightsPlayer.Enums.ARKNIGHTS_OPERATOR_CARD_COLOR, CardRarity.SPECIAL, CardTarget.NONE);
        
        this.tags.add(ArknightsCardTag.DEPLOY);
        this.promotionState = PromotionState.ZERO;
    }
    
    protected void initGiveCardsSetting(List<AbstractCard> giveCards) {
        this.giveCards = giveCards;
    }
    
    @Override
    public boolean canUpgrade() {
        boolean isMaxLevel = this.promotionState == PromotionState.TWO && this.timesUpgraded == this.promotionState.getMaxLevel();
        return !isMaxLevel;
    }
    
    
    @Override
    public void upgrade() {
        this.timesUpgraded++;
        ArknightsMod.logger.info("{} upgrade to timesUpgraded = {}", this.toIdString(), this.timesUpgraded);
        this.upgraded = true;
        if (this.timesUpgraded > promotionState.getMaxLevel()) {
            promotionState = promotionState.next();
        }
        updateNameWithPromotionLevel();
        
        switch (promotionState) {
            case ONE:
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
                break;
            case TWO:
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                break;
            default:
                this.rawDescription = cardStrings.DESCRIPTION;
                break;
        }
        initializeDescription();
    }
    

    @Override
    public void triggerWhenDrawn() {
    }


    public List<AbstractCard> getGiveCardsCopy() {
        List<AbstractCard> copys = new ArrayList<>();
        for (AbstractCard card : giveCards) {
            AbstractCard copy = card.makeCopy();
            if (copy instanceof IOperatorCreateable) {
                ((IOperatorCreateable)copy).initByOperatorCreate(this);
            }
            copys.add(copy);
        }
        int range = Math.min(copys.size(), promotionState.getGiveCardRange());
        ArknightsMod.logger.info("{} GiveCards range = {}", this.toIdString(), range);
        copys = copys.subList(0, range);
        return copys;
    }
    
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }
    
    @Override
    public void use(AbstractPlayer arg0, AbstractMonster arg1) {
    }

    
    
    
    
    
    public void addPotentialCount() {
        for (int i = 0; i < 3; i++) {
            if (canUpgrade()) {
                upgrade();
            }
        }
    }

}
