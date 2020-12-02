package arknights.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import arknights.orbs.AbstractModOrb;

/**
 * @author hundun
 * Created on 2020/12/02
 */
public class OrbOverloadAction extends AbstractGameAction {
    private AbstractModOrb orb;

    public OrbOverloadAction(AbstractModOrb orb)
    {
        duration = this.startDuration;
        this.orb = orb;
        actionType = ActionType.DAMAGE;
    }

    @Override
    public void update()
    {
        if (duration == this.startDuration) {
            if (orb != null) {
                AbstractDungeon.player.orbs.remove(orb);
                orb.triggerEvokeAnimation();
                orb.overload();
            }
        }
        this.tickDuration();
    }
}
