package arknights.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
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
public class CommonRarityCardStrengthPower extends AbstractPower implements IModifyRegainBlockPower {

    public static final String POWER_ID = ArknightsMod.makeID(CommonRarityCardStrengthPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);


    private static final Texture tex84 = TextureLoader.getTexture(ArknightsMod.makePowerPngPath(AbstractPower.class, 84));
    private static final Texture tex32 = TextureLoader.getTexture(ArknightsMod.makePowerPngPath(AbstractPower.class, 32));

    public CommonRarityCardStrengthPower(final AbstractCreature owner, final int amount) {
        name = powerStrings.NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    
    
    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount <= 0) {
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
    
    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount <= 0) {
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
    
    public void updateDescription() {
        this.description = LocalizationUtils.formatDescription(powerStrings.DESCRIPTIONS[0], this.amount);
        this.type = AbstractPower.PowerType.BUFF;
    }
    
    @Override
    public float modifyBlock(float blockAmount, AbstractCard card) {
        float temp = blockAmount;
        
        if (hasBonus(card)) {
            temp += this.amount;
        }
        
        return temp;
    }
    
    @Override
    public float atDamageGive(float damage, DamageType type, AbstractCard card) {
        float temp = damage;
        
        if (hasBonus(card)) {
            temp += this.amount;
        }
        
        return temp;
    }
    
    private boolean hasBonus(AbstractCard card) {
        return card.rarity == CardRarity.COMMON || card.rarity == CardRarity.BASIC;
    }


    @Override
    public float modifyRegainBlock(float amount, AbstractCard card) {
        float temp = amount;
        
        if (hasBonus(card)) {
            temp += this.amount;
        }
        
        return temp;
    }

    
    
}
