package arknights.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

/**
 * @author hundun
 * Created on 2020/11/20
 */
public class DiscardWantTargetCardAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
    public static final String[] TEXT = uiStrings.TEXT;
    AbstractPlayer player;
    Class<? extends AbstractCard> exhaustTargetClass;
    public static int numDiscarded;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    public DiscardWantTargetCardAction(AbstractCreature source, int amount, Class<? extends AbstractCard> exhaustTargetClass) {
        this.player = AbstractDungeon.player;
        this.exhaustTargetClass = exhaustTargetClass;
        setValues(player, source, amount);
        this.actionType = AbstractGameAction.ActionType.DISCARD;
        this.duration = DURATION;
    }
  
    
    private boolean discardOrExhaust(AbstractCard card) {
        boolean exhaustHappend;
        if (exhaustTargetClass.isInstance(card)) {
            this.player.hand.moveToExhaustPile(card);
            exhaustHappend = true;
        } else {
            this.player.hand.moveToDiscardPile(card);
            card.triggerOnManualDiscard();
            GameActionManager.incrementDiscard(false);
            exhaustHappend = false;
        }
        return exhaustHappend;
    }
    
    
    @Override
    public void update() {
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            } 
            if (this.player.hand.size() <= this.amount) {
                this.amount = this.player.hand.size();
                int tmp = this.player.hand.size();
                
                int exhaustNum = 0;
                for (int i = 0; i < tmp; i++) {
                    AbstractCard c = this.player.hand.getTopCard();
                    boolean exhaustHappend = discardOrExhaust(c);
                    exhaustNum += exhaustHappend ? 1 : 0;
                } 
                    
                if (exhaustNum > 0) {
                    addToTop(new GainEnergyAction(exhaustNum));
                }
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;
            } 
            
            if (this.amount < 0) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;
            } 
            numDiscarded = this.amount;
            if (this.player.hand.size() > this.amount) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false);
            }
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
            return; 

        } 
        
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            int exhaustNum = 0;
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                boolean exhaustHappend = discardOrExhaust(c);
                exhaustNum += exhaustHappend ? 1 : 0;
            }
            if (exhaustNum > 0) {
                addToTop(new GainEnergyAction(exhaustNum));
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        } 
        tickDuration();
    }
}
