package arknights.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import arknights.ArknightsMod;
import arknights.manager.MoreGameActionManager;
import arknights.util.LocalizationUtils;
import arknights.util.TextureLoader;

/**
 * @author hundun
 * Created on 2021/02/18
 */
public class NextTurnRegainBlockPower extends AbstractPower {
    public static final String POWER_ID = ArknightsMod.makeID(NextTurnRegainBlockPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID); 
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(ArknightsMod.makePowerPngPath(AbstractPower.class, 84));
    private static final Texture tex32 = TextureLoader.getTexture(ArknightsMod.makePowerPngPath(AbstractPower.class, 32));
    
    
    public NextTurnRegainBlockPower(AbstractPlayer owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        
        this.owner = owner;
    }
    
    
    public void updateDescription() {
        this.description = LocalizationUtils.formatDescription(DESCRIPTIONS[0], this.amount);
    }

    public void atStartOfTurn() {
        // update currentRegainAmount
        int currentRegainAmountLimit = MoreGameActionManager.getCurrentRegainBlockAmountLimit();
       
        flash();
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.owner.hb.cX, this.owner.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
        int currentRegainAmount = Math.min(currentRegainAmountLimit, this.amount);
        MoreGameActionManager.countRegainBlock(currentRegainAmount);
        addToBot(new GainBlockAction(owner, owner, currentRegainAmount));
        addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, "Next Turn Block"));
    }
}
