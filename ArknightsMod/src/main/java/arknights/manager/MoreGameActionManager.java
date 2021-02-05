package arknights.manager;

import arknights.ArknightsMod;

/**
 * @author hundun
 * Created on 2021/02/06
 */
public class MoreGameActionManager {
    public static int regainBlockThisTurn = -1;
    public static int regainBlockThisCombat = -1;
    
    public static int decrementBlockThisTurn = -1;
    public static int decrementBlockThisCombat = -1;
    
    
    public static void countDecrementBlock(int amount) {
        decrementBlockThisTurn += amount;
        decrementBlockThisCombat += amount;
        ArknightsMod.logger.info("after countDecrementBlock {}, Turn = {}, Combat = {}", amount, decrementBlockThisTurn, decrementBlockThisCombat);
    }
    
    public static void countRegainBlock(int amount) {
        regainBlockThisTurn += amount;
        regainBlockThisCombat += amount;
        ArknightsMod.logger.info("after countRegainBlock {}, Turn = {}, Combat = {}", amount, regainBlockThisTurn, regainBlockThisCombat);
    }

    public static void applyEndOfTurnTriggers() {
        regainBlockThisTurn = 0;
        decrementBlockThisTurn = 0;
        ArknightsMod.logger.info("MoreGameActionManager applyStartOfTurn done");
    }

    public static void applyStartOfCombatLogic() {
        regainBlockThisTurn = 0;
        regainBlockThisCombat = 0;
        decrementBlockThisTurn = 0;
        decrementBlockThisCombat = 0;
        ArknightsMod.logger.info("MoreGameActionManager applyStartOfTurn done");
    }
}
