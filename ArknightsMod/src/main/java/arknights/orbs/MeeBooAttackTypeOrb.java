package arknights.orbs;

import static arknights.DefaultMod.makeCardPath;
import static arknights.DefaultMod.makeOrbPath;

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

import arknights.DefaultMod;
import arknights.cards.FourStarSingleTargetMedicDeploy;
import arknights.util.LocalizationUtils;
import arknights.util.TextureLoader;
import basemod.abstracts.CustomOrb;

/**
 * @author hundun
 * Created on 2020/11/09
 */
public class MeeBooAttackTypeOrb extends AbstractModOrb {
    
    public static final String ID = DefaultMod.makeID(MeeBooAttackTypeOrb.class);
    public static final String IMG_PATH = makeOrbPath(MeeBooAttackTypeOrb.class.getSimpleName() + ".png");
   
    // Standard ID/Description
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String[] DESC = orbString.DESCRIPTION;

    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;

    // special const
    private static final int BASE_EVOKE_DAMAGE = 20;  
    private static final int BASE_PASSIVE_DAMAGE = 3;  
    
    
    public MeeBooAttackTypeOrb() {
        super(ID, BASE_PASSIVE_DAMAGE, BASE_EVOKE_DAMAGE, "", "", IMG_PATH);
        

        super.angle = 0; //MathUtils.random(360.0f); // More Animation-related Numbers
        super.channelAnimTimer = 0.5f;
        
    }
    
    @Override
    public AbstractOrb makeCopy() {
        return new MeeBooAttackTypeOrb();
    }

    @Override
    public void onEvoke() {
        addToBot(
                new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(evokeAmount, true, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        
        addToBot(new SFXAction("TINGSHA")); 
        
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1f);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, c.a));
        sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, scale, angle, 0, 0, 96, 96, false, false);
        
//        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0f));
//        sb.setBlendFunction(770, 1);
//        sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, -angle, 0, 0, 96, 96, false, false);
//        
//        sb.setBlendFunction(770, 771);
        renderText(sb);
        
        super.hb.render(sb);
    }

    @Override
    public void updateDescription() {
        applyFocus(); 
        super.description = LocalizationUtils.formatDescription(orbString.DESCRIPTION[0], this.passiveAmount, this.evokeAmount);
    }
    
    @Override
    public void onEndOfTurn() {
        addToBot(
                new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), 0.1f));

        addToBot(
                new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
        
    }

//    @Override
//    public void applyFocus() {
//        passiveAmount = basePassiveAmount;
//        evokeAmount = baseEvokeAmount;
//    }

}
