package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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
 * Created on 2020/12/21
 */
public class PrepareShot extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(PrepareShot.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;

    private static final int DAMAGE_UP_MAGIC_INDEX = ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX;
    
    public PrepareShot() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(7)
                .setMagicNumber(3)
                .setExtraMagicNumber(DAMAGE_UP_MAGIC_INDEX, 3)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(-1)
                .setPlusExtraMagicNumber(DAMAGE_UP_MAGIC_INDEX, 2)
                );
        this.selfRetain = true;
    }
    
    
    
    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        clearPrepareCount();
    }
    
    @Override
    public void onRetained() {
        super.onRetained();
        addPrepareCount(1);
        this.rawDescription = cardStrings.DESCRIPTION + LocalizationUtils.formatDescription(cardStrings.EXTENDED_DESCRIPTION[0], getPrepareCount());
        initializeDescription();
    }

    
    @Override
    public void applyPowers() {
        boolean reach = getPrepareCount() >= this.magicNumber;
        if (reach) {
            applyPowersWithTempAddBaseDamage(getExtraMagicNumber(DAMAGE_UP_MAGIC_INDEX));
        } else {
            super.applyPowers();
        }
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster arg0) {
        boolean reach = getPrepareCount() >= this.magicNumber;
        if (reach) {
            calculateCardDamageWithTempAddBaseDamage(arg0, getExtraMagicNumber(DAMAGE_UP_MAGIC_INDEX));
        } else {
            super.calculateCardDamage(arg0);
        }
    }
    
    

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        
        clearPrepareCount();
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

}
