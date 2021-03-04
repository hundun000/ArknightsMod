package arknights.actions;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import arknights.ArknightsMod;

/** 
 * alter from Marisa-mod BinaryStarsAction
 * @author hundun
 * Created on 2021/01/29
 */
public class DiscoveryTargetCardsAction extends AbstractGameAction {
    
    private AbstractPlayer player;
    private List<AbstractCard> targetCards;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ArknightsMod.makeID(DiscoveryTargetCardsAction.class));
    
    
    public DiscoveryTargetCardsAction(AbstractPlayer player, List<AbstractCard> targetCards, int gainCardAmout) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.targetCards = new ArrayList<>(targetCards);
        this.amount = gainCardAmout;
        this.player = player;
        
    }


    @Override
    public void update(){
        CardGroup tmp;
        if (this.duration == Settings.ACTION_DUR_MED){
            tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            targetCards.forEach(item -> tmp.addToBottom(item));
            AbstractDungeon.gridSelectScreen.open(tmp, 1, uiStrings.TEXT[0], false);
            tickDuration();
            return;
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0){
//            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
//                c.unhover();
//                if (this.player.hand.size() == 10){
//                    this.player.createHandIsFullDialog();
//                    this.player.discardPile.addToTop(c);
//                }
//                else{
//                    this.player.hand.addToTop(c);
//            }
            
            for (AbstractCard selectedCard : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(selectedCard, Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 10.0F * Settings.scale, Settings.HEIGHT / 2.0F, true, false));
            }
            
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        tickDuration();
    }

}
