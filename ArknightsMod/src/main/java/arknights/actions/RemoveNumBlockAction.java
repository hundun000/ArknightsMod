package arknights.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

/**
 * @author hundun
 * Created on 2021/02/19
 */
public class RemoveNumBlockAction extends AbstractGameAction {

    private static final float DUR = 0.25F;
    
    public RemoveNumBlockAction(AbstractCreature target, AbstractCreature source, int amount) {
        setValues(target, source, amount);
        this.actionType = AbstractGameAction.ActionType.BLOCK;
        this.duration = 0.25F;
    }

    public void update() {
        if (!this.target.isDying && !this.target.isDead && 
                this.duration == 0.25F && this.target.currentBlock > 0) {
            int numLose = Math.min(this.target.currentBlock, this.amount);
            this.target.loseBlock(numLose);
        }
        tickDuration();
    }
    
}
