package arknights.characters;

import java.util.ArrayList;
import java.util.Arrays;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import arknights.ArknightsMod;
import arknights.cards.AdnachielStrike;
import arknights.cards.BeagleDefend;
import arknights.cards.CatapultStrike;
import arknights.cards.MelanthaStrike;
import arknights.cards.MidnightStrike;
import arknights.cards.Cooking;
import arknights.cards.KroosStrike;
import arknights.cards.LavaStrike;
import arknights.cards.OrbOverload;
import arknights.cards.PlumeStrike;
import arknights.cards.PopukarStrike;
import arknights.cards.ShellDefense;
import arknights.cards.SpotDefend;
import arknights.cards.SummonFreezingSupportDrone;
import arknights.cards.SummonMeeboo;
import arknights.cards.SummonMiningSupportDrone;
import arknights.cards.ToxicOverload;
import arknights.cards.VorpalEdge;
import arknights.cards.W12Bomb;
import arknights.cards.WaveStrike;
import arknights.cards.operator.AmiyaDeploy;
import arknights.cards.operator.FangDeploy;
import arknights.cards.operator.TexasDeploy;
import arknights.cards.simple.Castle3Strike;
import arknights.cards.simple.DurinStrike;
import arknights.cards.simple.F12Strike;
import arknights.cards.simple.Lancet2Strike;
import arknights.cards.simple.NoirCorneDefend;
import arknights.cards.simple.RangersStrike;
import arknights.cards.simple.SimpleRegainBlock;
import arknights.cards.simple.ThermalExStrike;
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
        String[] starter = new String[]{
                DurinStrike.ID, F12Strike.ID, NoirCorneDefend.ID, RangersStrike.ID, 
                YatoStrike.ID, Castle3Strike.ID, Lancet2Strike.ID, ThermalExStrike.ID,
                };
        String[] threeStars = new String[]{
                PlumeStrike.ID, MelanthaStrike.ID, PopukarStrike.ID, 
                MidnightStrike.ID, AdnachielStrike.ID, KroosStrike.ID, 
                CatapultStrike.ID, SpotDefend.ID, BeagleDefend.ID, 
                LavaStrike.ID
                };
        String[] testing = new String[]{};
        
        retVal.add(AmiyaDeploy.ID);
        retVal.addAll(Arrays.asList(testing));
        //retVal.addAll(Arrays.asList(operators));
        retVal.addAll(Arrays.asList(threeStars));
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
