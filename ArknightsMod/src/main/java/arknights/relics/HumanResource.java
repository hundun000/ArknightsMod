package arknights.relics;

import static arknights.ArknightsMod.makeRelicOutlinePath;
import static arknights.ArknightsMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import arknights.ArknightsMod;
import arknights.cards.W12Bomb;
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

    private boolean outsideBattle = false;
    private static final int UPGRADE_COST_STACK = 600;
    
    private static final int GAIN_COUNT_PER_ROOM = 50;
    
    public HumanResource() {
        this(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.SOLID);
    }
    
    public HumanResource(
            String ID, 
            Texture IMG, 
            Texture OUTLINE, 
            RelicTier relicTier, 
            LandingSound landingSound
            ) {
        super(ID, IMG, OUTLINE, relicTier, landingSound);
        this.outsideBattle = true;
        this.counter = 15;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }


    @Override
    public void onRightClick() {
        
        if (!usable()) {
            return;
        }
        
        AbstractDungeonHelper.setSpecifiedCardAsSingleReward(new W12Bomb());

        AbstractDungeon.combatRewardScreen.open(this.DESCRIPTIONS[1]);
        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.0F;

    }
    
    private boolean usable() {
        boolean hasEnoughStack = counter >= UPGRADE_COST_STACK;
        return (isObtained && outsideBattle && hasEnoughStack);
    }
    
    @Override
    public void atPreBattle() {
        outsideBattle = false;
        stopPulse();
    }
    
    @Override
    public void onVictory() {
        outsideBattle = true;
        addCounter(1);
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
        if (usable()) {
            beginPulse();
        } else {
            stopPulse();
        }
    }
    
    @Override
    public void onEnterRoom(AbstractRoom room) {
        flash();
        addCounter(GAIN_COUNT_PER_ROOM);
    }

}
