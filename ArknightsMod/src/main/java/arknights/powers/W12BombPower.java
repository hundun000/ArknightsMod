package arknights.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
 * Created on 2020/11/27
 */
public class W12BombPower extends AbstractPower {
    public static final String ID_START = ArknightsMod.makeID(W12BombPower.class);
    private static int idOffset = 0;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID_START);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(ArknightsMod.makePowerPngPath(AbstractPower.class, 84));
    private static final Texture tex32 = TextureLoader.getTexture(ArknightsMod.makePowerPngPath(AbstractPower.class, 32));

    private int damage;
    
    public W12BombPower(final AbstractCreature owner, final int turns, final int damage) {
        this.name = NAME;
        this.ID = ID_START + idOffset;
        idOffset++;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        
        this.owner = owner;
        this.amount = turns;
        this.type = PowerType.DEBUFF;
        updateDescription();
        
        this.damage = damage;
    }
    
    
    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
            if (this.amount == 1) {
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }
    
    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = LocalizationUtils.formatDescription(DESCRIPTIONS[1], this.damage);
        } else {
            this.description = LocalizationUtils.formatDescription(DESCRIPTIONS[0], this.amount, this.damage);
        }   
    }
}
