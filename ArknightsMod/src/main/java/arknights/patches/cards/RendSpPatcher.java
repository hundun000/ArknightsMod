package arknights.patches.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import basemod.ReflectionHacks;
import javassist.CtBehavior;



public class RendSpPatcher {

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
                card.renderSpForCard(sb, false);
            }
        }
    }

    @SpirePatch(
            clz= AbstractCard.class,
            method="renderCard"
    )
    public static class RenderCardPatcher {
        @SpireInsertPatch(
        		locator = Locator.class,
                localvars = {"sb"}
        )
        public static void Insert(AbstractCard __instance, SpriteBatch sb) {
            if(__instance instanceof ArknightsModCard) {
                ArknightsModCard card = (ArknightsModCard) __instance;
                card.renderSpForCard(sb, false);
            }
        }
        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "renderTitle");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
    
    @SpirePatch(
            clz= SingleCardViewPopup.class,
            method="render"
    )
    public static class SingleCardViewPopupRenderCardPatcher {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"sb"}
        )
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb) {
        	AbstractCard card = (AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if(card instanceof ArknightsModCard) {
                ArknightsModCard arknightsModCard = (ArknightsModCard) card;
                //ArknightsMod.logger.info("SingleCardViewPopupRenderCardPatcher called for card {}, its SpThreshold = {}", arknightsModCard.toIdString(), arknightsModCard.getSpThreshold());
                arknightsModCard.renderSpForSingleCardViewPopup(sb, true);
            }
        }
        
        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SingleCardViewPopup.class, "renderTitle");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}

