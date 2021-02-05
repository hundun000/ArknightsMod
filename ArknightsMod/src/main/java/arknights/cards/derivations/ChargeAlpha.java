package arknights.cards.derivations;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.BaseDeployCard;
import arknights.cards.base.IOperatorCreateable;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;

/**
 * @author hundun
 * Created on 2020/11/16
 */
public class ChargeAlpha extends ArknightsModCard {

    public static final String ID = ArknightsMod.makeID(ChargeAlpha.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.SPECIAL; 
    private static final CardTarget TARGET = CardTarget.SELF;  
    private static final CardType TYPE = CardType.SKILL;       
    
    private static final int COST = 0;
    
    private static final int GIVE_ENERGY_NUM = 1;
    
    private static final int PLUS_GIVE_ENERGY_NUM = 1;
    
    
    public ChargeAlpha() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setMagicNumber(GIVE_ENERGY_NUM)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusMagicNumber(PLUS_GIVE_ENERGY_NUM)
                );
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new GainEnergyAction(magicNumber));
    }



}
