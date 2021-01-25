package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/12/19
 */
public class ChargingShot extends AbstractModCard {
    
    public static final String ID = ArknightsMod.makeID(ChargingShot.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 0;
    private static final int DAMAGE_UP_MAGIC_INDEX = ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX;
    private static final int DAMAGE_TIMES_INDEX = ExtraVariable.GENERAL_3rd_MAGIC_NUMBER_INDEX;
    
    public ChargingShot() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(5)
                .setBlock(2)
                .setMagicNumber(3)
                .setExtraMagicNumber(DAMAGE_UP_MAGIC_INDEX, 2)
                .setExtraMagicNumber(DAMAGE_TIMES_INDEX, 2)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                .setPlusBlock(1)
                .setPlusExtraMagicNumber(DAMAGE_UP_MAGIC_INDEX, 1)
                .setPlusExtraMagicNumber(DAMAGE_TIMES_INDEX, 1)
                );
        super.useMagicNumberAsUseTimeCountThreshold = true;
    }
    
    @Override
    public void applyPowers() {
        if (isNextUseTimeReachThreshold()) {
            applyPowersWithTempAddBaseDamage(getExtraMagicNumber(DAMAGE_UP_MAGIC_INDEX));
        } else {
            super.applyPowers();
        }
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster arg0) {
        if (isNextUseTimeReachThreshold()) {
            calculateCardDamageWithTempAddBaseDamage(arg0, getExtraMagicNumber(DAMAGE_UP_MAGIC_INDEX));
        } else {
            super.calculateCardDamage(arg0);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addUseCount();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
        
        for (int i = 0; i < getExtraMagicNumber(DAMAGE_TIMES_INDEX); i++) {
            addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        }
    }
    
    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (isNextUseTimeReachThreshold()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }
}
