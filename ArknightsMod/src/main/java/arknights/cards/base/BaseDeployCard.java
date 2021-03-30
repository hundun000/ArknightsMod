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
    protected List<AbstractCard> baseGiveCards;
    protected List<AbstractCard> currentGiveCards;

    
    protected int timesUpgradeLimit;
    
    public BaseDeployCard(String id, String img) {
        super(id, img, 0, CardType.SKILL, ArknightsPlayer.Enums.ARKNIGHTS_CARD_COLOR, CardRarity.SPECIAL, CardTarget.NONE);
        
        this.tags.add(ArknightsCardTag.DEPLOY);
        
        this.currentGiveCards = new ArrayList<>();
        this.timesUpgradeLimit = 0;
        this.baseGiveCards = new ArrayList<>();
        
    }
    
    protected void initStar(int star) {
        switch (star) {
            case 3:
                this.color = ArknightsPlayer.Enums.ARKNIGHTS_OPERATOR_3_STAR_CARD_COLOR;
                break;
            case 4:
                this.color = ArknightsPlayer.Enums.ARKNIGHTS_OPERATOR_4_STAR_CARD_COLOR;
                break;
            case 5:
                this.color = ArknightsPlayer.Enums.ARKNIGHTS_OPERATOR_5_STAR_CARD_COLOR;
                break;
            default:
                break;
        }
    }
    
    protected void initGiveCardsSetting(List<AbstractCard> baseGiveCards) {
        this.baseGiveCards.clear();
        appendGiveCardsSetting(baseGiveCards);
    }
    
    protected void appendGiveCardsSetting(List<AbstractCard> baseGiveCards) {
        this.baseGiveCards.addAll(baseGiveCards);
        this.timesUpgradeLimit = calculateUpgradeLimit(baseGiveCards.size());
        updateCurrentGiveCards();
    }
    
    @Override
    public boolean canUpgrade() {
        return this.timesUpgraded < timesUpgradeLimit;
    }
    
    
    @Override
    public void upgrade() {
        this.timesUpgraded++;
        ArknightsMod.logger.info("{} upgrade to timesUpgraded = {}", this.toIdString(), this.timesUpgraded);
        this.upgraded = true;
      
        updateForTimesUpgradedChange();
    } 
    
    /**
     * 执行所有TimesUpgraded变动引起的更新
     */
    protected void updateForTimesUpgradedChange() {
        updateNameWithPromotionLevel();
        updateCurrentGiveCards();
        
        int index = Math.min(cardStrings.EXTENDED_DESCRIPTION.length - 1, this.timesUpgraded - 1);
        if (index < 0) {
            this.rawDescription = cardStrings.DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[index];
        }
        initializeDescription();
    }
    
    @Override
    protected void customPostMakeCopy(ArknightsModCard from) {
        updateForTimesUpgradedChange();
    }
    
    protected void updateCurrentGiveCards() {

        currentGiveCards.clear();
        currentGiveCards.add(baseGiveCards.get(0).makeCopy());
        if (this.timesUpgraded >= 1 && currentGiveCards.get(0).canUpgrade()) {
            currentGiveCards.get(0).upgrade();
            currentGiveCards.add(baseGiveCards.get(1).makeCopy());
        }
        if (this.timesUpgraded >= 2) {
            currentGiveCards.get(1).upgrade();
            currentGiveCards.add(baseGiveCards.get(2).makeCopy());
        }
        if (this.timesUpgraded >= 3) {
            currentGiveCards.get(2).upgrade();
        }
        
        if (currentGiveCards.size() > 0) {
            this.cardsToPreview = baseGiveCards.get(0);
            this.moreCardsToPreview = baseGiveCards.subList(1, baseGiveCards.size());
        } 
    }



    public List<AbstractCard> getCurrentGiveCardsCopy() {
        List<AbstractCard> copys = new ArrayList<>();
        for (AbstractCard card : currentGiveCards) {
            AbstractCard copy = card.makeCopy();
            copys.add(copy);
        }
        return copys;
    }
    
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }
    
    @Override
    public void use(AbstractPlayer arg0, AbstractMonster arg1) {
    }

    
    
    private int calculateUpgradeLimit(int giveCardsSize) {
        switch (giveCardsSize) {
            case 0:
            case 1:
                return 0;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }
    
    
    public void addPotentialCount() {
        for (int i = 0; i < 1; i++) {
            if (canUpgrade()) {
                upgrade();
            }
        }
    }

}
