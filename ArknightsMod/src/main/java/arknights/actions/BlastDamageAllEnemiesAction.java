package arknights.actions;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;

/**
 * 除了mainTarget。其余目标受到的伤害变为blastDamageRate倍
 * @author hundun
 * Created on 2021/02/07
 */
public class BlastDamageAllEnemiesAction extends DamageAllEnemiesAction {

    public BlastDamageAllEnemiesAction(AbstractCreature source, AbstractCreature mainTarget, double blastDamageRate, int[] amount, DamageType type, AttackEffect effect) {
        super(source, amount, type, effect);
        
        int mainTargetIndex = -1;
        for (int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); i++) {
            AbstractMonster monster = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            if (monster == mainTarget) {
                mainTargetIndex = i;
                break;
            }
        }
        ArknightsMod.logger.debug("before BlastDamage init, mainTargetIndex = {}, damage[] = {}", mainTargetIndex, damage);
        if (mainTargetIndex != -1) {
            for (int i = 0; i < this.damage.length; i++) {
                int blastDamage = (int) Math.ceil(this.damage[i] * blastDamageRate);
                this.damage[i] = blastDamage;
            }
        }
        ArknightsMod.logger.debug("after BlastDamage init, damage[] = {}", damage);
    }

}
