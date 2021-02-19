package arknights.manager;

import com.megacrit.cardcrawl.characters.AbstractPlayer;

import arknights.ArknightsMod;

/**
 * @author hundun
 * Created on 2021/02/06
 */
public class MoreGameActionManager {
    private static int regainBlockThisTurn = -1;
    private static int regainBlockThisCombat = -1;
    
    private static int numBlockLastTurnEnd = -1;

    public static void countRegainBlock(int amount) {
        regainBlockThisTurn += amount;
        regainBlockThisCombat += amount;
        ArknightsMod.logger.info("after countRegainBlock {}, Turn = {}, Combat = {}", amount, regainBlockThisTurn, regainBlockThisCombat);
    }

    public static void applyEndOfTurnTriggers(AbstractPlayer player) {
        regainBlockThisTurn = 0;
         numBlockLastTurnEnd = player.currentBlock;
         ArknightsMod.logger.info("numBlockLastTurnEnd = {}", numBlockLastTurnEnd);
        ArknightsMod.logger.info("MoreGameActionManager applyStartOfTurn done");
    }

    public static void applyStartOfCombatLogic() {
        regainBlockThisTurn = 0;
        regainBlockThisCombat = 0;
        numBlockLastTurnEnd = 0;
        ArknightsMod.logger.info("MoreGameActionManager applyStartOfTurn done");
    }
    
    public static int getCurrentRegainBlockAmountLimit() {
        int currentRegainAmountLimit = Math.max(0, MoreGameActionManager.numBlockLastTurnEnd - MoreGameActionManager.regainBlockThisTurn);
        return currentRegainAmountLimit;
    }
}
