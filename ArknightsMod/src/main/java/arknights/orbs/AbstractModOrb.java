package arknights.orbs;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import basemod.abstracts.CustomOrb;

/**
 * @author hundun
 * Created on 2020/11/09
 */
public abstract class AbstractModOrb extends CustomOrb {
    
    protected Integer evokeAmountChargeSpeed;
    
    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;
    
    public AbstractModOrb(
            String id, 
            int basePassiveAmount, 
            int baseEvokeAmount, 
            String passiveDescription,
            String evokeDescription, 
            String imgPath) {
        super(id, languagePack.getOrbString(id).NAME, basePassiveAmount, baseEvokeAmount, passiveDescription, evokeDescription, imgPath);
        super.angle = 0;
        super.channelAnimTimer = 0.5f;
    }
    
    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
  
    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
    
    
    protected void chargeEvokeAmount() {
        if (evokeAmountChargeSpeed != null) {
            addToBot(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1f));
            this.baseEvokeAmount += this.evokeAmountChargeSpeed;
            this.evokeAmount = baseEvokeAmount;
            updateDescription();
        }
    }
    
    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, c.a));
        sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, scale, angle, 0, 0, 96, 96, false, false);
        
        renderTextWithChargeSpeed(sb);
        
        super.hb.render(sb);
    }

    protected void renderTextWithChargeSpeed(SpriteBatch sb) {
        String evokeText = Integer.toString(evokeAmount);
        if (evokeAmountChargeSpeed != null) {
            evokeText += "(+" + evokeAmountChargeSpeed + ")";
        }
        
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, 
                evokeText, this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, 
                Integer.toString(passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET + 20.0F * Settings.scale, this.c, this.fontScale);
    }
    
    public abstract void onOverload();
    

}
