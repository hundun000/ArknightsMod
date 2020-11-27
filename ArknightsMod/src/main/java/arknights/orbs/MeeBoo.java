package arknights.orbs;

import static arknights.ArknightsMod.makeCardPath;
import static arknights.ArknightsMod.makeOrbPath;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import arknights.ArknightsMod;
import arknights.cards.FourStarSingleTargetMedicDeploy;
import arknights.util.LocalizationUtils;
import arknights.util.TextureLoader;
import basemod.abstracts.CustomOrb;

/**
 * @author hundun
 * Created on 2020/11/09
 */
public class MeeBoo extends AbstractModOrb {
    
    public static final String ID = ArknightsMod.makeID(MeeBoo.class);
    public static final String IMG_PATH = makeOrbPath(MeeBoo.class.getSimpleName() + ".png");
   
    // Standard ID/Description
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String[] DESC = orbString.DESCRIPTION;



    // special const
    private static final int BASE_EVOKE_DAMAGE = 0; 
    private static final int BASE_PASSIVE_DAMAGE = 3;  
    
    
    public MeeBoo() {
        super(ID, BASE_PASSIVE_DAMAGE, BASE_EVOKE_DAMAGE, "", "", IMG_PATH);
        

        
        
        this.evokeAmountChargeSpeed = 5;
        updateDescription();
    }
    
    @Override
    public AbstractOrb makeCopy() {
        return new MeeBoo();
    }

    @Override
    public void onEvoke() {
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(evokeAmount, true, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        addToBot(new SFXAction("TINGSHA")); 
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
        addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
    }
    
    



}