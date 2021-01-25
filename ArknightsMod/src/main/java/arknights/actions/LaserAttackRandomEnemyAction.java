package arknights.actions;
/**
 * @author hundun
 * Created on 2020/11/26
 */

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Metallicize;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class LaserAttackRandomEnemyAction extends AbstractGameAction {
    
    private DamageInfo info;
    boolean removeBlock;
    boolean removePower;
    public LaserAttackRandomEnemyAction(DamageInfo info, AbstractGameAction.AttackEffect effect,
            boolean removeBlock,
            boolean removePower
            ) {
        this.info = info;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
        this.removeBlock = removeBlock;
        this.removePower = removePower;
    }
    
    @Override
    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        this.source = info.owner;
        if (this.target != null) {
            if (removeBlock) {
                addToTop(new RemoveAllBlockAction(this.target, source));
            }
            if (removePower) {
                addToTop(new RemoveSpecificPowerAction(target, source, Metallicize.ID));
                addToTop(new RemoveSpecificPowerAction(target, source, PlatedArmorPower.POWER_ID));
            }
            addToTop(new DamageAction(this.target, this.info, this.attackEffect));
        }
        this.isDone = true;
    }
}