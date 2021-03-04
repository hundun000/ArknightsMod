package arknights.relics;

import static arknights.ArknightsMod.makeRelicOutlinePath;
import static arknights.ArknightsMod.makeRelicPath;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import arknights.ArknightsMod;
import arknights.actions.DiscoveryTargetCardsAction;
import arknights.cards.base.BaseDeployCard;
import arknights.rewards.PotentialReward;
import arknights.util.AbstractDungeonHelper;
import arknights.util.TextureLoader;
import basemod.abstracts.CustomRelic;

/**
 * @author hundun
 * Created on 2020/12/24
 */
public class HumanResource extends CustomRelic implements ClickableRelic {
    
    public static final String ID = ArknightsMod.makeID(HumanResource.class);

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(HumanResource.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(CustomRelic.class.getSimpleName() + ".png"));
    
    private static final int UPGRADE_COST_STACK = 600;
    
    private static final int GAIN_COUNT_PER_ROOM = 50;
    
    public HumanResource() {
        this(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.SOLID);
    }
    
    public HumanResource(
            String ID, 
            Texture IMG, 
            Texture OUTLINE, 
            RelicTier relicTier, 
            LandingSound landingSound
            ) {
        super(ID, IMG, OUTLINE, relicTier, landingSound);
        this.counter = 15000;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }


    @Override
    public void onRightClick() {
        
        if (!usable()) {
            return;
        }

        AbstractCard operatorCard = AbstractDungeonHelper.returnOperatorCard(null);

        boolean obtained = false;
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.cardID.equals(operatorCard.cardID)) {
                obtained = true;
                break;
            }
        }

        
        AbstractDungeon.combatRewardScreen.open(this.DESCRIPTIONS[1]);
        AbstractDungeon.combatRewardScreen.rewards.clear();
        
        
        if (obtained) {
            String potentialText = operatorCard.name + this.DESCRIPTIONS[2];
            PotentialReward potentialReward = new PotentialReward(operatorCard.cardID, potentialText);
            AbstractDungeon.combatRewardScreen.rewards.add(potentialReward);
        } else {
            RewardItem cardReward = new RewardItem();
            cardReward.cards.clear();
            cardReward.cards.add(operatorCard);
            AbstractDungeon.combatRewardScreen.rewards.add(cardReward);
        }
        
        
        AbstractDungeon.combatRewardScreen.positionRewards();
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;

        addCounter(-1 * UPGRADE_COST_STACK);
    }

    
    private boolean usable() {
        boolean hasEnoughStack = counter >= UPGRADE_COST_STACK;
        return (isObtained && hasEnoughStack && AbstractDungeon.screen == CurrentScreen.MAP);
    }
    
    
    
    
    
    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    
    
    public void addCounter(int addition) {
        counter = Math.min(counter + addition, 9999);
        flash();
    }
    
    @Override
    public void onEnterRoom(AbstractRoom room) {
        addCounter(GAIN_COUNT_PER_ROOM);
    }


}
