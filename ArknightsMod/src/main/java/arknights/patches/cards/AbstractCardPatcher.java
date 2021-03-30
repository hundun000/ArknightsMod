package arknights.patches.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.cards.base.ArknightsModCard;


public class AbstractCardPatcher {

    @SpirePatch(
            clz= AbstractCard.class,
            method="renderInLibrary"
    )
    public static class RenderInLibraryPatcher {
        @SpireInsertPatch(
                rloc = 19,
                localvars = {"sb"}
        )
        public static void Insert(AbstractCard __instance, SpriteBatch sb) {
            if(__instance instanceof ArknightsModCard) {
                ArknightsModCard card = (ArknightsModCard) __instance;
                card.renderSp(sb);
            }
        }
    }

    @SpirePatch(
            clz= AbstractCard.class,
            method="renderCard"
    )
    public static class RenderCardPatcher {
        @SpireInsertPatch(
                rloc = 10,
                localvars = {"sb"}
        )
        public static void Insert(AbstractCard __instance, SpriteBatch sb) {
            if(__instance instanceof ArknightsModCard) {
                ArknightsModCard card = (ArknightsModCard) __instance;
                card.renderSp(sb);
            }
        }
    }
}

