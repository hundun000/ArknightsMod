package arknights.relics;

import static arknights.DefaultMod.makeRelicOutlinePath;
import static arknights.DefaultMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import arknights.DefaultMod;
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

    // ID, images, text.
    public static final String ID = DefaultMod.makeID(UrsusBreadRelic.class);

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(UrsusBreadRelic.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(UrsusBreadRelic.class.getSimpleName() + ".png"));

    public UrsusBreadRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }


    // Gain 1 Strength on on equip.
    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(
            new RelicAboveCreatureAction(AbstractDungeon.player, this)
        );
        AbstractDungeon.actionManager.addToBottom(
            new DrawCardAction(AbstractDungeon.player, 1)
        );
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
