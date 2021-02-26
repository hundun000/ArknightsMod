package arknights.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsCardTag;
import arknights.util.LocalizationUtils;
import arknights.util.TextureLoader;

/**
 * @author hundun
 * Created on 2021/02/23
 */
public class SpellStrengthPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = ArknightsMod.makeID(SpellStrengthPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(ArknightsMod.makePowerPngPath(AbstractPower.class, 84));
    private static final Texture tex32 = TextureLoader.getTexture(ArknightsMod.makePowerPngPath(AbstractPower.class, 32));

    public SpellStrengthPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    
    
    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount == 0) {
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
    
    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount == 0) {
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
    
    public void updateDescription() {
        int scaleRate = Math.max(0, 100 + this.amount * 25);
        if (this.amount > 0) {
            this.description = LocalizationUtils.formatDescription(DESCRIPTIONS[0], scaleRate);
            this.type = AbstractPower.PowerType.BUFF;
        } else {
            this.description = LocalizationUtils.formatDescription(DESCRIPTIONS[1], scaleRate);
            this.type = AbstractPower.PowerType.DEBUFF;
        } 
    }
    
    @Override
    public float atDamageGive(float damage, DamageType type, AbstractCard card) {
        float temp = damage;
        
        if (card.hasTag(ArknightsCardTag.SPELL_DAMAGE)) {
            int scaleRate = Math.max(0, 100 + this.amount * 25);
            ArknightsMod.logger.info("SpellStrength make {} damage from {} scaleRate {}", card.name, temp, scaleRate);
            temp *= scaleRate / 100.0;
        }
        
        return temp;
    }

}
