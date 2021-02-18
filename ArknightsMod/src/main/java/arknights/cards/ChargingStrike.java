package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.util.LocalizationUtils;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/12/02
 */
public class ChargingStrike extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(ChargingStrike.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;
    private static final int DAMAGE_UP_MAGIC_INDEX = ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX;
    
    public ChargingStrike() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(5)
                .setBlock(2)
                .setMagicNumber(3)
                .setExtraMagicNumber(DAMAGE_UP_MAGIC_INDEX, 2)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                .setPlusBlock(1)
                .setPlusExtraMagicNumber(DAMAGE_UP_MAGIC_INDEX, 1)
                );
        setSpThreshold(this.magicNumber);
    }
    
    
    @Override
    public void upgrade() {
       super.upgrade();
       
       setSpThreshold(this.magicNumber);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addSpCount(magicNumber);
        this.rawDescription = cardStrings.DESCRIPTION + LocalizationUtils.formatDescription(cardStrings.EXTENDED_DESCRIPTION[0], this.getSpCount());
        initializeDescription();
        
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));

        
    }
    
    @Override
    public void applyPowers() {
        if (isSpCountReachThreshold()) {
            applyPowersWithTempAddBaseDamage(getExtraMagicNumber(DAMAGE_UP_MAGIC_INDEX));
        } else {
            super.applyPowers();
        }
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster arg0) {
        if (isSpCountReachThreshold()) {
            calculateCardDamageWithTempAddBaseDamage(arg0, getExtraMagicNumber(DAMAGE_UP_MAGIC_INDEX));
            clearSpCount();
        } else {
            super.calculateCardDamage(arg0);
        }
    }
    
    @Override
    protected boolean needSetBorderOnGlow() {
        return isSpCountReachThreshold();
    }

}
