package arknights.orbs;

import static arknights.ArknightsMod.makeOrbPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import arknights.ArknightsMod;
import arknights.actions.LaserAttackRandomEnemyAction;
import arknights.util.LocalizationUtils;

/**
 * @author hundun
 * Created on 2020/11/25
 */
public class MiningSupportDrone extends AbstractModOrb {
    public static final String ID = ArknightsMod.makeID(MiningSupportDrone.class);
    public static final String IMG_PATH = makeOrbPath(MiningSupportDrone.class.getSimpleName() + ".png");
   
    // Standard ID/Description
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String[] DESC = orbString.DESCRIPTION;



    // special const
    private static final int BASE_EVOKE_DAMAGE = 0; 
    private static final int BASE_PASSIVE_DAMAGE = 2;  
    
    
    public MiningSupportDrone() {
        super(ID, BASE_PASSIVE_DAMAGE, BASE_EVOKE_DAMAGE, "", "", IMG_PATH);
        

        
        
        this.evokeAmountChargeSpeed = 5;
        updateDescription();
    }
    @Override
    public AbstractOrb makeCopy() {
        return new MiningSupportDrone();
    }

    @Override
    public void onEvoke() {
        
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1f);
    }


    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = LocalizationUtils.formatDescription(orbString.DESCRIPTION[0], this.passiveAmount, this.evokeAmountChargeSpeed, this.evokeAmount);
    }
    
    @Override
    public void onStartOfTurn() {
        chargeEvokeAmount();
        addToBot(new LaserAttackRandomEnemyAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageType.THORNS), AttackEffect.NONE, true, false));
    }
    @Override
    public void onOverload() {
        addToBot(new LaserAttackRandomEnemyAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageType.THORNS), AttackEffect.NONE, true, true));
        addToBot(new SFXAction("TINGSHA")); 
    }

}
