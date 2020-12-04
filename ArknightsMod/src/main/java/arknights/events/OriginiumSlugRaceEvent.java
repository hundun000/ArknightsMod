package arknights.events;

import static arknights.ArknightsMod.makeEventPath;

import java.util.Random;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import arknights.ArknightsMod;
import arknights.util.LocalizationUtils;

public class OriginiumSlugRaceEvent extends AbstractImageEvent {


    public static final String ID = ArknightsMod.makeID(OriginiumSlugRaceEvent.class);
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("IdentityCrisisEvent.png");
    

    private int wagerPrice; 
    Random random = new Random();
    
    public OriginiumSlugRaceEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        if (AbstractDungeon.ascensionLevel >= 15) { 
            wagerPrice = 20;
        } else {
            wagerPrice = 30;
        }

        imageEventText.setDialogOption(OPTIONS[0]); 
        imageEventText.setDialogOption(LocalizationUtils.formatDescription(OPTIONS[1], wagerPrice));
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); 
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]); 
                        this.imageEventText.clearRemainingOptions(); 
                        screenNum = 1; 

                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Deinal

                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        CardCrawlGame.sound.play("BLUNT_FAST");  // Play a hit sound
                        
                        boolean win = random.nextBoolean();
                        String mainText = "";
                        if (win) {
                            AbstractDungeon.player.gainGold(wagerPrice * 2);
                            mainText += DESCRIPTIONS[2];
                        } else {
                            mainText += DESCRIPTIONS[3];
                        }
                        boolean hasNextRace = random.nextBoolean();
                        if (hasNextRace) {
                            mainText += DESCRIPTIONS[4];
                        } else {
                            mainText += DESCRIPTIONS[5];
                        }

                        this.imageEventText.updateBodyText(mainText);
                        
                        if (hasNextRace) {
                            this.imageEventText.updateDialogOption(0, OPTIONS[0]);
                            this.imageEventText.updateDialogOption(1, LocalizationUtils.formatDescription(OPTIONS[1], wagerPrice));
                            screenNum = 0;
                        } else {
                            this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                            screenNum = 1;
                        }
                        this.imageEventText.clearRemainingOptions();
                        

                        break;
                }
                break;
            case 1:
                switch (i) {
                    case 0: 
                        openMap();
                        break;
                }
                break;
        }
    }

}
