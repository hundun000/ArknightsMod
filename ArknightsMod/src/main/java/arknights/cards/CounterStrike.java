package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.cards.base.component.UseCounter;
import arknights.cards.derivations.SwordRain;
import arknights.util.LocalizationUtils;
import arknights.variables.ExtraVariable;
import basemod.AutoAdd;

/**
 * @author hundun
 * Created on 2020/11/18
 */
@AutoAdd.Ignore
@Deprecated
public class CounterStrike extends AbstractModCard {
    
    public static final String ID = ArknightsMod.makeID(CounterStrike.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;
    
    private static final int COUNTER_THRESHOLD = 3;
    
    private static final int COUNTER_REACH_PLUS_DAMAGE = 3;
    private static final int UPGRADE_COUNTER_REACH_PLUS_DAMAGE = 6;

    
    protected UseCounter useCounter = new UseCounter();
    
    public CounterStrike() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(5)
                .setMagicNumber(COUNTER_THRESHOLD)
                .setExtraMagicNumber(ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX, COUNTER_REACH_PLUS_DAMAGE)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                .setPlusExtraMagicNumber(ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX, UPGRADE_COUNTER_REACH_PLUS_DAMAGE)
                );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        useCounter.add();
        int newDamage;
        if (useCounter.getCount() == magicNumber) {
            newDamage = this.damage + this.extraMagicNumbers[ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX];
            useCounter.reset();
        } else {
            newDamage = this.damage;
        }
        addToBot(new DamageAction(monster, new DamageInfo(player, newDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
    
    @Override
    public void onMoveToDiscard() {
        if (!upgraded) {
            this.rawDescription = cardStrings.DESCRIPTION
                    + LocalizationUtils.formatDescription(cardStrings.EXTENDED_DESCRIPTION[0], useCounter.getCount());
        } else {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION 
                    + LocalizationUtils.formatDescription(cardStrings.EXTENDED_DESCRIPTION[0], useCounter.getCount());
        }
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster arg0) {
        super.calculateCardDamage(arg0);
        if (!upgraded) {
            this.rawDescription = cardStrings.DESCRIPTION
                    + LocalizationUtils.formatDescription(cardStrings.EXTENDED_DESCRIPTION[0], useCounter.getCount());
        } else {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION 
                    + LocalizationUtils.formatDescription(cardStrings.EXTENDED_DESCRIPTION[0], useCounter.getCount());
        }
        initializeDescription();
    }
}
