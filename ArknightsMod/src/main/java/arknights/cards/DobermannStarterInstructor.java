package arknights.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.powers.CommonRarityCardStrengthPower;

/**
 * @author hundun
 * Created on 2020/11/17
 */
public class DobermannStarterInstructor extends ArknightsModCard {

    public static final String ID = ArknightsMod.makeID(DobermannStarterInstructor.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON; 
    private static final CardTarget TARGET = CardTarget.SELF;  
    private static final CardType TYPE = CardType.POWER;    
    
    private static final int COST = 1;  

    public DobermannStarterInstructor() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setMagicNumber(3)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusMagicNumber(2)
                );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player,
                    new CommonRarityCardStrengthPower(player, this.magicNumber)));
    }

}
