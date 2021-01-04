package arknights.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.SlowPower;

/**
 * @author hundun
 * Created on 2020/12/24
 */
public class SlowOneTurnPower extends SlowPower {
    public static final String POWER_ID = "SlowOneTurn";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID); 
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public SlowOneTurnPower(AbstractCreature owner, int amount) {
        super(owner, amount);
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
    }
    
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID)); 
    }

}
