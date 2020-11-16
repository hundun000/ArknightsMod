package arknights.orbs;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomOrb;

/**
 * @author hundun
 * Created on 2020/11/09
 */
public abstract class AbstractModOrb extends CustomOrb {

    public AbstractModOrb(
            String id, 
            int basePassiveAmount, 
            int baseEvokeAmount, 
            String passiveDescription,
            String evokeDescription, 
            String imgPath) {
        super(id, languagePack.getOrbString(id).NAME, basePassiveAmount, baseEvokeAmount, passiveDescription, evokeDescription, imgPath);
        
    }
    
    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
  
    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
    
    @Override
    public void applyFocus() {
        passiveAmount = basePassiveAmount;
        evokeAmount = baseEvokeAmount;
    }

}
