package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.relics.BattleRecords;
import arknights.util.LocalizationUtils;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/12/02
 */
public class PeriodStrike extends AbstractModCard {
    
    public static final String ID = ArknightsMod.makeID(PeriodStrike.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;
    private static final int DAMAGE_UP_MAGIC_INDEX = ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX;
    
    private int useTimes;
    
    public PeriodStrike() {
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
        this.useTimes = 0;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        useTimes = (useTimes + 1) % magicNumber;
        
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));

        this.rawDescription = cardStrings.DESCRIPTION + LocalizationUtils.formatDescription(cardStrings.EXTENDED_DESCRIPTION[0], this.useTimes);
        initializeDescription();
    }
    
    @Override
    public AbstractCard makeCopy() {
        PeriodStrike copy = new PeriodStrike();
        copy.useTimes = this.useTimes;
        return copy;
    }
    
    @Override
    public void applyPowers() {
        int originBaseDamage = this.baseDamage;
        if (useTimes + 1 == magicNumber) {
            this.baseDamage += extraMagicNumbers[DAMAGE_UP_MAGIC_INDEX];
        }
        super.applyPowers();
        if (useTimes + 1 == magicNumber) {
            this.baseDamage = originBaseDamage;
        }
        this.isDamageModified = (this.damage != this.baseDamage);
        this.rawDescription = cardStrings.DESCRIPTION + LocalizationUtils.formatDescription(cardStrings.EXTENDED_DESCRIPTION[0], this.useTimes);
        initializeDescription();
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster arg0) {
        int originBaseDamage = this.baseDamage;
        if (useTimes + 1 == magicNumber) {
            this.baseDamage += extraMagicNumbers[DAMAGE_UP_MAGIC_INDEX];
        }
        super.calculateCardDamage(arg0);
        if (useTimes + 1 == magicNumber) {
            this.baseDamage = originBaseDamage;
        }
        this.isDamageModified = (this.damage != this.baseDamage);
    }
    
    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (useTimes + 1 == magicNumber) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

}