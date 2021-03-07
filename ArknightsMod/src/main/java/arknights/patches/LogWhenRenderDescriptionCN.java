package arknights.patches;

import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.regex.Pattern;

//@SpirePatch(
//		clz=AbstractCard.class,
//		method="renderDescriptionCN"
//)
public class LogWhenRenderDescriptionCN
{
	@SpireInsertPatch(
			locator=Locator.class,
			localvars={"tmp"}
	)
	public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef String[] tmp)
	{
	    if (tmp[0].contains("$$")) {
	        ArknightsMod.logger.info("{} RenderDescriptionCN has $$ word: {}", __instance.name, ArknightsModCard.descriptionToString(__instance.description));
	    }
		
	}

	private static class Locator extends SpireInsertLocator
	{
		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
		{
			Matcher finalMatcher = new Matcher.MethodCallMatcher(String.class, "length");
			return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
		}
	}
}
