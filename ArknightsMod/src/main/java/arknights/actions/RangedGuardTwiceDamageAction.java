package arknights.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;

/**
 * @author hundun
 * Created on 2021/02/27
 */
public class RangedGuardTwiceDamageAction extends AbstractGameAction {
    

    int damage;
    DamageType damageType;
    
    public RangedGuardTwiceDamageAction(AbstractCreature target, AbstractCreature source, int damage, DamageType damageType, AttackEffect attackEffect) {
        this.damage = damage;
        this.attackEffect = attackEffect;
        this.source = source;
        this.target = target;
        this.damageType = damageType;
    }

    @Override
    public void update() {
        addToTop(new DamageAction(target, new DamageInfo(source, damage, damageType), attackEffect));
        int rangedWeakerDamage = MathUtils.floor(damage * 0.8F);
        addToTop(new DamageAction(target, new DamageInfo(source, rangedWeakerDamage, damageType), attackEffect));
    }
    
    
    
}
