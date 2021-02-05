package arknights.relics;

import basemod.abstracts.CustomRelic;

import static arknights.ArknightsMod.makeRelicOutlinePath;
import static arknights.ArknightsMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import arknights.ArknightsMod;
import arknights.util.TextureLoader;

public class BattleRecords extends CustomRelic implements ClickableRelic { // You must implement things you want to use from StSlib

    public static final String ID = ArknightsMod.makeID(BattleRecords.class);

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(BattleRecords.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(BattleRecords.class.getSimpleName() + ".png"));

    private boolean outsideBattle = false;
    private static final int UPGRADE_COST_STACK = 10;
    private boolean cardsSelected;
    private static final int MAX_SELECT_NUM = 1;
    
    public BattleRecords() {
        this(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.SOLID);
    }
    
    public BattleRecords(
            String ID, 
            Texture IMG, 
            Texture OUTLINE, 
            RelicTier relicTier, 
            LandingSound landingSound
            ) {
        super(ID, IMG, OUTLINE, relicTier, landingSound);
        this.outsideBattle = true;
        this.counter = 150;
        this.cardsSelected = true;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }


    @Override
    public void onRightClick() {// On right click
        
        if (!usable()) {
            return;
        }
        

        this.cardsSelected = false;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : (AbstractDungeon.player.masterDeck.getUpgradableCards()).group) {
            tmp.addToTop(card);
        }
        if (tmp.group.isEmpty()) {
            this.cardsSelected = true; 
            return;
        } 
        if (tmp.group.size() <= MAX_SELECT_NUM) {
            this.cardsSelected = true;
            selectDone(tmp.group.get(0));
        } else if (!AbstractDungeon.isScreenUp) {
            AbstractDungeon.gridSelectScreen.open(tmp, MAX_SELECT_NUM, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
        } else {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
            AbstractDungeon.gridSelectScreen.open(tmp, MAX_SELECT_NUM, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
        } 

        

    }
    
    @Override
    public void update() {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == MAX_SELECT_NUM) {
            this.cardsSelected = true;
            selectDone(AbstractDungeon.gridSelectScreen.selectedCards.get(0));
        }
    }
    
    private void selectDone(AbstractCard selectedCard) {
        selectedCard.upgrade();
        AbstractDungeon.player.bottledCardUpgradeCheck(selectedCard);
        AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(selectedCard.makeStatEquivalentCopy()));
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        
        addCounter(-1 * UPGRADE_COST_STACK);
    }


    private boolean usable() {
        boolean hasEnoughStack = counter >= UPGRADE_COST_STACK;
        return (isObtained && outsideBattle && hasEnoughStack && this.cardsSelected);
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
        counter = Math.min(counter + addition, 999);
        flash();
        if (usable()) {
            beginPulse();
        } else {
            stopPulse();
        }
    }
}
