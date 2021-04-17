package arknights.characters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import arknights.ArknightsMod;
import arknights.actions.DiscoveryTargetCardsAction;
import arknights.cards.AdnachielStrike;
import arknights.cards.AnselStrike;
import arknights.cards.AreneSmallJoke;
import arknights.cards.BeagleDefend;
import arknights.cards.BeanstalkPinpointCommand;
import arknights.cards.BeehunterFlexibility;
import arknights.cards.BeehunterSoaringFists;
import arknights.cards.CatapultStrike;
import arknights.cards.ConvictionGenesis;
import arknights.cards.MelanthaStrike;
import arknights.cards.MidnightStrike;
import arknights.cards.MousseScratch;
import arknights.cards.MyrtleHealingWings;
import arknights.cards.CourierCommandDefense;
import arknights.cards.CutterCrimsonCrescent;
import arknights.cards.DobermannStarterInstructor;
import arknights.cards.FangStrike;
import arknights.cards.FrostleafFrostTomahawk;
import arknights.cards.JackieFocus;
import arknights.cards.KroosStrike;
import arknights.cards.LavaStrike;
import arknights.cards.OrbOverload;
import arknights.cards.OrchidStrike;
import arknights.cards.PlumeStrike;
import arknights.cards.PopukarStrike;
import arknights.cards.ScavengerCommandAttack;
import arknights.cards.SpotDefend;
import arknights.cards.StewardStrike;
import arknights.cards.SummonFreezingSupportDrone;
import arknights.cards.SummonMeeboo;
import arknights.cards.SummonMiningSupportDrone;
import arknights.cards.UtageDescendingStrikeEarthSplitter;
import arknights.cards.VignaHammerOn;
import arknights.cards.base.BaseDeployCard;
import arknights.cards.operator.AmiyaDeploy;
import arknights.cards.operator.BeehunterDeploy;
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
import basemod.BaseMod;
import basemod.interfaces.OnStartBattleSubscriber;

/**
 * @author hundun
 * Created on 2021/02/05
 */
public class Doctor extends ArknightsPlayer implements OnStartBattleSubscriber {
    
    
    public Doctor() {
        super("Doctor", ArknightsPlayer.Enums.ARKNIGHTS_PLAYER_CLASS);
        BaseMod.subscribe(this);
    }

 // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");
        String[] operators = new String[]{ FangDeploy.ID, TexasDeploy.ID, BeehunterDeploy.ID};
        String[] starter = new String[]{
                DurinStrike.ID, F12Strike.ID, NoirCorneDefend.ID, RangersStrike.ID, 
                YatoStrike.ID, Castle3Strike.ID, Lancet2Strike.ID, ThermalExStrike.ID,
                };
        String[] threeStars = new String[]{
                FangStrike.ID, PlumeStrike.ID, MelanthaStrike.ID, PopukarStrike.ID, 
                MidnightStrike.ID, AdnachielStrike.ID, KroosStrike.ID, 
                CatapultStrike.ID, SpotDefend.ID, BeagleDefend.ID, 
                AnselStrike.ID, OrchidStrike.ID, StewardStrike.ID,
                LavaStrike.ID
                };
        String[] fourStars = new String[]{
                BeanstalkPinpointCommand.ID, VignaHammerOn.ID, ScavengerCommandAttack.ID,
                MyrtleHealingWings.ID, CourierCommandDefense.ID, DobermannStarterInstructor.ID,
                ConvictionGenesis.ID, AreneSmallJoke.ID, JackieFocus.ID, CutterCrimsonCrescent.ID,
                BeehunterSoaringFists.ID, BeehunterFlexibility.ID, MousseScratch.ID, FrostleafFrostTomahawk.ID,
                UtageDescendingStrikeEarthSplitter.ID
        };
        String[] testing = new String[]{DobermannStarterInstructor.ID, KroosStrike.ID, AdnachielStrike.ID};
        
        retVal.add(AmiyaDeploy.ID);
        retVal.addAll(Arrays.asList(starter));
        //retVal.addAll(Arrays.asList(operators));
        //retVal.addAll(Arrays.asList(threeStars));
        //retVal.addAll(Arrays.asList(fourStars));
        retVal.addAll(Arrays.asList(testing));
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
        askPlayerchoosOperaterCard();
        super.applyStartOfCombatLogic();
        
        
    }

    @Override
    public void applyStartOfTurnPreDrawCards() {
        super.applyStartOfTurnPreDrawCards();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom arg0) {
        //askPlayerchoosOperaterCard();
    }
    
    
    private void askPlayerchoosOperaterCard() {
        List<BaseDeployCard> deployCards = new ArrayList<>();
        
        ArknightsMod.logger.info("before askPlayerchoosOperaterCard, drawPile.size = {}", AbstractDungeon.player.drawPile.group.size());
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            if (card instanceof BaseDeployCard) {
                BaseDeployCard deployCard = (BaseDeployCard)card;
                deployCards.add(deployCard);
            }
        }
        ArknightsMod.logger.info("askPlayerchoosOperaterCard found {} deployCards", deployCards.size());
        for (BaseDeployCard deployCard : deployCards) {
            List<AbstractCard> giveCards = deployCard.getCurrentGiveCardsCopy();
            if (giveCards != null && !giveCards.isEmpty()) {
                ArknightsMod.logger.info("askPlayerchoosOperaterCard {} giveCards.size = {}", deployCard.toIdString(), giveCards.size());
                if (giveCards.size() > 1) {
                    AbstractDungeon.actionManager.addToTop(new DiscoveryTargetCardsAction(AbstractDungeon.player, giveCards, 1));
                } else if (giveCards.size() == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(giveCards.get(0), Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 10.0F * Settings.scale, Settings.HEIGHT / 2.0F, true, false));
                }
            }
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(deployCard, AbstractDungeon.player.drawPile));
        }
    }
}


