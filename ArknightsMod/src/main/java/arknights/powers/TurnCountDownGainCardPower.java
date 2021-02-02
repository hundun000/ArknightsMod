package arknights.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import arknights.ArknightsMod;
import arknights.util.LocalizationUtils;
import arknights.util.TextureLoader;

/**
 * @author hundun
 * Created on 2020/11/25
 */
public class TurnCountDownGainCardPower extends AbstractPower {
    public static final String ID_START = ArknightsMod.makeID(TurnCountDownGainCardPower.class);
    private static int idOffset = 0;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID_START);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(ArknightsMod.makePowerPngPath(AbstractPower.class, 84));
    private static final Texture tex32 = TextureLoader.getTexture(ArknightsMod.makePowerPngPath(AbstractPower.class, 32));

    private final AbstractCard gainCard;
    private final int cardNum;
    
    public TurnCountDownGainCardPower(final AbstractCreature owner, final int countDownTurnNum, AbstractCard gainCard, int cardNum) {
        this.name = NAME;
        this.ID = ID_START + idOffset;
        
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        
        this.owner = owner;
        this.amount = countDownTurnNum;
        this.type = PowerType.DEBUFF;
        updateDescription();
        
        
        
        this.gainCard = gainCard;
        this.cardNum = cardNum;
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
            if (this.amount == 1) {
                addToBot(new MakeTempCardInHandAction(gainCard, cardNum, false));
            }
        }
    }
    
    @Override
    public void updateDescription() {
        if (this.amount > 1) {
            this.description = LocalizationUtils.formatDescription(DESCRIPTIONS[0], amount, cardNum, gainCard.name);
        } else {
            this.description = LocalizationUtils.formatDescription(DESCRIPTIONS[1], cardNum, gainCard.name);
        } 
    }
}
