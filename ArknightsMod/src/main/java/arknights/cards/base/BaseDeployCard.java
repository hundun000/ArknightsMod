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
import arknights.characters.Doctor;
import arknights.powers.TurnCountDownGainCardPower;

/**
 * @author hundun
 * Created on 2020/11/16
 */
public abstract class BaseDeployCard extends ArknightsModCard {

    protected List<AbstractCard> giveCards;
    protected int giveCardsBaseRange = 0;
    protected int giveCardsUpgradedRange = 0;
    
    public BaseDeployCard(String id, String img, int cost, CardRarity rarity) {
        super(id, img, cost, CardType.SKILL, Doctor.Enums.ARKNIGHTS_OPERATOR_CARD_COLOR, rarity, CardTarget.ENEMY);

        this.tags.add(ArknightsCardTag.DEPLOY);
    }
    
    protected void initGiveCardsSetting(List<AbstractCard> giveCards, int baseRange, int upgradedRange) {
        this.giveCards = giveCards;
        this.giveCardsBaseRange = baseRange;
        this.giveCardsUpgradedRange = upgradedRange;
    }

    @Override
    public void triggerWhenDrawn() {
        List<AbstractCard> giveCards = getGiveCardsCopy();
        if (giveCards != null && !giveCards.isEmpty()) {
            int giveCardsRange;
            if (this.upgraded) {
                giveCards.forEach(item -> item.upgrade());
                giveCardsRange = Math.min(giveCards.size(), giveCardsUpgradedRange);
            } else {
                giveCardsRange = Math.min(giveCards.size(), giveCardsBaseRange);
            }
            giveCards = giveCards.subList(0, giveCardsRange);
            
            if (giveCards.size() > 1) {
                addToTop(new DiscoveryTargetCardsAction(giveCards, 1));
            } else if (giveCards.size() == 1) {
                addToTop(new MakeTempCardInHandAction(giveCards.get(0), 1));
            }
        }
        addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }


    protected List<AbstractCard> getGiveCardsCopy() {
        List<AbstractCard> copys = new ArrayList<>();
        for (AbstractCard card : giveCards) {
            AbstractCard copy = card.makeCopy();
            if (copy instanceof ArknightsModCard) {
                ArknightsMod.logger.info(this.toIdString() + " add Potential = " + this.potentialCount + " to GiveCard: " + copy.cardID);
                ((ArknightsModCard) copy).addPotentialCount(this.potentialCount);
            }
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


}
