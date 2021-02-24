package arknights.util;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import arknights.ArknightsMod;
import arknights.actions.DiscoveryTargetCardsAction;
import arknights.cards.base.ArknightsModCard;

/**
 * @author hundun
 * Created on 2020/11/16
 */
public class LocalizationUtils {
    
    private static String DESCRIPTION_FORMAT_ARGUMENT_PLACEHOLDER = "{}";
    
    public static String formatDescription(String format, Object... args) {
        for (Object arg : args) {
            int index = format.indexOf(DESCRIPTION_FORMAT_ARGUMENT_PLACEHOLDER);
            if (index > 0) {
                format = format.substring(0, index) + (arg == null ? arg : arg.toString()) + format.substring(index + DESCRIPTION_FORMAT_ARGUMENT_PLACEHOLDER.length());
            } else {
                break;
            }
        }
        return format;
    }
    
    public static void main(String[] args) {
        System.out.println(formatDescription("造成{}点伤害{}次", 1, 2, 3));
        System.out.println(formatDescription("造成{}点伤害{}次", 1, 2));
        System.out.println(formatDescription("造成{}点伤害{}次", 1));
        System.out.println(formatDescription("造成1点伤害2次"));
    }
    
    
    
    
    

}
