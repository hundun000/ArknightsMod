package arknights.actions;

import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import arknights.manager.MoreGameActionManager;

/**
 * @author hundun
 * Created on 2021/02/27
 */
public class RegainBlockAction extends AbstractGameAction {
    private int regainBlockAmount;
    private int regainBlockAmountLimit;
    private AbstractCreature target;
    
    public RegainBlockAction(AbstractCreature target, int regainBlockAmount, int regainBlockAmountLimit) {
        this.regainBlockAmount = regainBlockAmount;
        this.regainBlockAmountLimit = regainBlockAmountLimit;
        this.target = target;
    }
    
    public void update() {
        int finalRegainAmount = Math.min(this.regainBlockAmountLimit, this.regainBlockAmount);
        MoreGameActionManager.countRegainBlock(finalRegainAmount);
        addToTop(new GainBlockAction(target, target, finalRegainAmount));
        this.isDone = true;
    }
}
