package arknights.orbs;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.AbstractPower;

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
    
//    /**
//     * copy from the game-source-code, add null-pointer check.(why the game-source-code hasn't?)
//     */
//    @Override
//    public void applyFocus() {
//        AbstractPower power = AbstractDungeon.player != null ? AbstractDungeon.player.getPower("Focus") : null;
//        if (power != null && !this.ID.equals(Plasma.ORB_ID)) {
//            this.passiveAmount = Math.max(0, this.basePassiveAmount + power.amount);
//            this.evokeAmount = Math.max(0, this.baseEvokeAmount + power.amount);
//        } else {
//            this.passiveAmount = this.basePassiveAmount;
//            this.evokeAmount = this.baseEvokeAmount;
//        }
//    }

}
