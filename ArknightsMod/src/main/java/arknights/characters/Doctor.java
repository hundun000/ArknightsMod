package arknights.characters;

import java.util.ArrayList;
import java.util.Arrays;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import arknights.ArknightsMod;
import arknights.cards.ChargingShot;
import arknights.cards.ChargingStrike;
import arknights.cards.Cooking;
import arknights.cards.OrbOverload;
import arknights.cards.PrepareShot;
import arknights.cards.RegainBlockCard;
import arknights.cards.ShellDefense;
import arknights.cards.SummonFreezingSupportDrone;
import arknights.cards.SummonMeeboo;
import arknights.cards.SummonMiningSupportDrone;
import arknights.cards.ToxicOverload;
import arknights.cards.VorpalEdge;
import arknights.cards.W12Bomb;
import arknights.cards.WaveStrike;
import arknights.cards.operator.FangDeploy;
import arknights.cards.operator.TexasDeploy;
import arknights.cards.simple.DurinStrike;
import arknights.cards.simple.F12Strike;
import arknights.cards.simple.NoirCorneDefend;
import arknights.cards.simple.RangersStrike;
import arknights.cards.simple.YatoStrike;
import arknights.manager.MoreGameActionManager;
import arknights.relics.BattleRecords;
import arknights.relics.HumanResource;
import arknights.relics.UrsusBreadRelic;

/**
 * @author hundun
 * Created on 2021/02/05
 */
public class Doctor extends ArknightsPlayer {
    
    
    public Doctor() {
        super("Doctor", ArknightsPlayer.Enums.ARKNIGHTS_PLAYER_CLASS);
        
    }

 // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");
        String[] operators = new String[]{ FangDeploy.ID, TexasDeploy.ID};
        String[] starter = new String[]{RegainBlockCard.ID, DurinStrike.ID, F12Strike.ID, NoirCorneDefend.ID, RangersStrike.ID, YatoStrike.ID};
        String[] summonPackage = new String[]{SummonFreezingSupportDrone.ID, SummonMiningSupportDrone.ID, SummonMeeboo.ID, OrbOverload.ID};
        String[] defensePackage = new String[]{Cooking.ID, ShellDefense.ID};
        String[] vanguardPackage = new String[]{TexasDeploy.ID, FangDeploy.ID};
        String[] guardPackage = new String[]{WaveStrike.ID, ChargingStrike.ID, VorpalEdge.ID};
        String[] shotPackage = new String[]{W12Bomb.ID, ChargingShot.ID, PrepareShot.ID};
        
        retVal.addAll(Arrays.asList(guardPackage));
        retVal.addAll(Arrays.asList(operators));
        retVal.addAll(Arrays.asList(starter));
        retVal.addAll(Arrays.asList(defensePackage));
        return retVal;
    }

    // Starting Relics  
    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        //retVal.add(PlaceholderRelic.ID);
        //retVal.add(PlaceholderRelic2.ID);
        retVal.add(BattleRecords.ID);
        retVal.add(UrsusBreadRelic.ID);
        retVal.add(HumanResource.ID);
        
        UnlockTracker.markRelicAsSeen(BattleRecords.ID);
        UnlockTracker.markRelicAsSeen(UrsusBreadRelic.ID);
        UnlockTracker.markRelicAsSeen(HumanResource.ID);
        return retVal;
    }
    
    
    @Override
    public AbstractPlayer newInstance() {
        return new Doctor();
    }

    
    @Override
    public void applyEndOfTurnTriggers() {
        MoreGameActionManager.applyEndOfTurnTriggers(this);
        super.applyEndOfTurnTriggers();
        
        
    }
    
    @Override
    public void applyStartOfCombatLogic() {
        MoreGameActionManager.applyStartOfCombatLogic();
        super.applyStartOfCombatLogic();
        
        
    }

}
