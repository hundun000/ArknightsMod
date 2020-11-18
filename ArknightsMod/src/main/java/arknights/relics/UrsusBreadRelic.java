package arknights.relics;

import static arknights.ArknightsMod.makeRelicOutlinePath;
import static arknights.ArknightsMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import arknights.ArknightsMod;
import arknights.util.TextureLoader;
import basemod.abstracts.CustomRelic;

/**
 * @author hundun
 * Created on 2020/11/06
 */
public class UrsusBreadRelic extends CustomRelic {
    /*
     * At the start of each combat, draw 1 card
     */

    public static final String ID = ArknightsMod.makeID(UrsusBreadRelic.class);

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(UrsusBreadRelic.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(UrsusBreadRelic.class.getSimpleName() + ".png"));

    public UrsusBreadRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }


    public void atBattleStart() {
        flash();
        addToBot((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, this));
        addToBot((AbstractGameAction)new DrawCardAction((AbstractCreature)AbstractDungeon.player, 1));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
