package arknights.powers;

import basemod.interfaces.CloneablePowerInterface;

import static arknights.ArknightsMod.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

import arknights.ArknightsMod;
import arknights.util.LocalizationUtils;
import arknights.util.TextureLoader;

@Deprecated
public class MagicStrengthPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = ArknightsMod.makeID(MagicStrengthPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(ArknightsMod.makePowerPngPath(MagicStrengthPower.class, 84));
    private static final Texture tex32 = TextureLoader.getTexture(ArknightsMod.makePowerPngPath(MagicStrengthPower.class, 32));

    public MagicStrengthPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    
    
    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount == 0) {
            addToTop((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, "Strength"));
        }
    }
    
    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount == 0) {
            addToTop((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, "Strength"));
        }
    }
    
    public void updateDescription() {
        if (this.amount > 0) {
            this.description = LocalizationUtils.formatDescription(DESCRIPTIONS[0], this.amount);
            this.type = AbstractPower.PowerType.BUFF;
        } else {
            int tmp = -this.amount;
            this.description = LocalizationUtils.formatDescription(DESCRIPTIONS[1], tmp);
            this.type = AbstractPower.PowerType.DEBUFF;
        } 
    }
    
    @Override
    public float atDamageGive(float damage, DamageType type) {
        // TODO Auto-generated method stub
        return super.atDamageGive(damage, type);
    }

}
