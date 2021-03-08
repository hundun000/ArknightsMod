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

    public static final double RATE = 0.75;
    
    public BlastDamageAllEnemiesAction(AbstractCreature source, AbstractMonster mainTarget, int[] amount, DamageType type, AttackEffect effect) {
        this(source, mainTarget, RATE, amount, type, effect);
    }
    
    public BlastDamageAllEnemiesAction(AbstractCreature source, AbstractMonster mainTarget, double blastDamageRate, int[] amount, DamageType type, AttackEffect effect) {
        super(source, amount, type, effect);
        
        int mainTargetIndex = -1;
        for (int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); i++) {
            AbstractMonster monster = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            if (monster == mainTarget) {
                mainTargetIndex = i;
                ArknightsMod.logger.debug("monster {} is mainTarget", monster.toString());
                break;
            } else {
                ArknightsMod.logger.debug("monster {} is not mainTarget", monster.toString());
            }
        }
        ArknightsMod.logger.debug("before BlastDamage init, mainTargetIndex = {}, damage[] = {}", mainTargetIndex, damage);
        if (mainTargetIndex != -1) {
            for (int i = 0; i < this.damage.length; i++) {
                if (i != mainTargetIndex) {
                    int blastDamage = (int) Math.ceil(this.damage[i] * blastDamageRate);
                    this.damage[i] = blastDamage;
                }
            }
        }
        ArknightsMod.logger.info("after BlastDamage init, damage[] = {}", damage);
    }

}
