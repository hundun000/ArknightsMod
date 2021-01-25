package arknights.relics;

import static arknights.ArknightsMod.makeRelicOutlinePath;
import static arknights.ArknightsMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import arknights.ArknightsMod;
import arknights.util.TextureLoader;
import basemod.abstracts.CustomRelic;

/**
 * @author hundun
 * Created on 2020/11/06
 */
public class StereoProjectorRelic extends CustomRelic {
    /*
     * 立即获得目标生命+6
     */

    // ID, images, text.
    public static final String ID = ArknightsMod.makeID(StereoProjectorRelic.class);

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(StereoProjectorRelic.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(StereoProjectorRelic.class.getSimpleName() + ".png"));

    public static final int INCREASE_MAX_HP_NUM = 6;
    
    
    public StereoProjectorRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }


    @Override
    public void onEquip() {
        flash();
        AbstractDungeon.actionManager.addToBottom(
            new RelicAboveCreatureAction(AbstractDungeon.player, this)
        );
        AbstractDungeon.player.increaseMaxHp(INCREASE_MAX_HP_NUM, true);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
