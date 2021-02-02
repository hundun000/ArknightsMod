package arknights.events;

import static arknights.ArknightsMod.makeEventPath;

import java.util.Random;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

import arknights.ArknightsMod;
import arknights.util.LocalizationUtils;

public class OriginiumSlugRaceEvent extends AbstractImageEvent {


    public static final String ID = ArknightsMod.makeID(OriginiumSlugRaceEvent.class);
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("IdentityCrisisEvent.png");
    
    
    private static final int BODY_TEXT_INDEX_MAIN = 0;
    private static final int BODY_TEXT_INDEX_END = 1;
    private static final int BODY_TEXT_INDEX_RACING = 2;
    private static final int BODY_TEXT_INDEX_YOU_WIN = 3;
    private static final int BODY_TEXT_INDEX_YOU_LOSE = 4;
    private static final int BODY_TEXT_INDEX_HAS_NEXT_RACE = 5;
    private static final int BODY_TEXT_INDEX_HAS_NOT_NEXT_RACE = 6;
    
    private static final int SCREEN_INDEX_MAIN = 0;
    private static final int SCREEN_INDEX_RACING = 1;
    private static final int SCREEN_INDEX_END = 2;
    
    private static final int OPTION_TEXT_INDEX_MAIN_PAID = 0;
    private static final int OPTION_TEXT_INDEX_MAIN_LEAVE = 1;
    private static final int OPTION_TEXT_INDEX_RACING = 2;
    private static final int OPTION_TEXT_INDEX_END = 3;
    
    private static final int OPTION_LAYOUT_INDEX_MAIN_PAID = 0;
    private static final int OPTION_LAYOUT_INDEX_MAIN_LEAVE = 1;
    private static final int OPTION_LAYOUT_INDEX_RACING = 0;
    private static final int OPTION_LAYOUT_INDEX_END = 0;
    
    private int wagerPrice; 
    Random random = new Random();
    
    public OriginiumSlugRaceEvent() {
        super(NAME, DESCRIPTIONS[BODY_TEXT_INDEX_MAIN], IMG);

        if (AbstractDungeon.ascensionLevel >= 15) { 
            wagerPrice = 20;
        } else {
            wagerPrice = 30;
        }

        screenNum = SCREEN_INDEX_MAIN;
        imageEventText.setDialogOption(LocalizationUtils.formatDescription(OPTIONS[OPTION_TEXT_INDEX_MAIN_PAID], wagerPrice));
        imageEventText.setDialogOption(OPTIONS[OPTION_TEXT_INDEX_MAIN_LEAVE]); 
        
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case SCREEN_INDEX_MAIN: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case OPTION_LAYOUT_INDEX_MAIN_LEAVE: 
                        this.imageEventText.updateBodyText(DESCRIPTIONS[BODY_TEXT_INDEX_END]); 
                        this.imageEventText.updateDialogOption(OPTION_LAYOUT_INDEX_END, OPTIONS[OPTION_TEXT_INDEX_END]); 
                        this.imageEventText.clearRemainingOptions(); 
                        screenNum = SCREEN_INDEX_END; 

                        break; 
                    case OPTION_LAYOUT_INDEX_MAIN_PAID: 

                        AbstractDungeon.player.loseGold(wagerPrice);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[BODY_TEXT_INDEX_RACING]); 
                        this.imageEventText.updateDialogOption(OPTION_LAYOUT_INDEX_RACING, OPTIONS[OPTION_TEXT_INDEX_RACING]); 
                        this.imageEventText.clearRemainingOptions(); 
                        screenNum = SCREEN_INDEX_RACING;
                        
                        break;
                }
                break;
            case SCREEN_INDEX_RACING:
                switch (i) {
                    case OPTION_LAYOUT_INDEX_RACING: 
                        boolean win = random.nextBoolean();
                        String mainText = "";
                        if (win) {
                            AbstractDungeon.effectList.add(new RainingGoldEffect(wagerPrice * 2));
                            AbstractDungeon.player.gainGold(wagerPrice * 2);
                            mainText += DESCRIPTIONS[BODY_TEXT_INDEX_YOU_WIN];
                        } else {
                            mainText += DESCRIPTIONS[BODY_TEXT_INDEX_YOU_LOSE];
                        }
                        boolean hasNextRace = random.nextBoolean();
                        if (hasNextRace) {
                            mainText += DESCRIPTIONS[BODY_TEXT_INDEX_HAS_NEXT_RACE];
                        } else {
                            mainText += DESCRIPTIONS[BODY_TEXT_INDEX_HAS_NOT_NEXT_RACE];
                        }

                        this.imageEventText.updateBodyText(mainText);
                        if (hasNextRace) {
                            this.imageEventText.updateDialogOption(OPTION_LAYOUT_INDEX_MAIN_LEAVE, OPTIONS[OPTION_TEXT_INDEX_MAIN_LEAVE]);
                            this.imageEventText.updateDialogOption(OPTION_LAYOUT_INDEX_MAIN_PAID, LocalizationUtils.formatDescription(OPTIONS[OPTION_TEXT_INDEX_MAIN_PAID], wagerPrice));
                            screenNum = SCREEN_INDEX_MAIN;
                        } else {
                            this.imageEventText.updateDialogOption(OPTION_LAYOUT_INDEX_END, OPTIONS[OPTION_TEXT_INDEX_END]);
                            screenNum = SCREEN_INDEX_END;
                        }
                        this.imageEventText.clearRemainingOptions();
                        
                        break;
                }
                break;
            case SCREEN_INDEX_END:
                switch (i) {
                    case OPTION_LAYOUT_INDEX_END: 
                        openMap();
                        break;
                }
                break;
        }
    }

}
