package arknights.actions;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import arknights.ArknightsMod;

/** 
 * alter from DiscoveryAction
 * @author hundun
 * Created on 2021/01/29
 */
public class DiscoveryTargetCardsAction extends AbstractGameAction {
    private boolean retrieveCard = false;

    private ArrayList<AbstractCard> targetCards;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ArknightsMod.makeID(DiscoveryTargetCardsAction.class));
    
    public DiscoveryTargetCardsAction(List<AbstractCard> targetCards, int gainCardAmout) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.targetCards = new ArrayList<>(targetCards);
        this.amount = gainCardAmout;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(targetCards, uiStrings.TEXT[0], false);
            tickDuration();
            return;
        } 
        if (!this.retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                AbstractCard disCard2 = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                
                if (AbstractDungeon.player.hasPower(MasterRealityPower.POWER_ID)) {
                    disCard.upgrade();
                    disCard2.upgrade();
                } 
                disCard.setCostForTurn(0);
                disCard2.setCostForTurn(0);
                
                disCard.current_x = -1000.0F * Settings.scale;
                disCard2.current_x = -1000.0F * Settings.scale + AbstractCard.IMG_HEIGHT_S;
                
                if (this.amount == 1) {
                    if (AbstractDungeon.player.hand.size() < Settings.MAX_HAND_SIZE) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    } 
                    disCard2 = null;
                } else if (AbstractDungeon.player.hand.size() + this.amount <= Settings.MAX_HAND_SIZE) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard2, Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                } else if (AbstractDungeon.player.hand.size() == Settings.MAX_HAND_SIZE - 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                } 
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            } 
            this.retrieveCard = true;
        } 
        tickDuration();
    }

}
