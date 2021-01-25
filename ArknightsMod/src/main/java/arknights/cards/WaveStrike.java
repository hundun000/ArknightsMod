package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.util.LocalizationUtils;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/11/26
 */
public class WaveStrike extends AbstractModCard {
    
    public static final String ID = ArknightsMod.makeID(WaveStrike.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;
    private static final int DAMAGE_DOWN_MAGIC_INDEX = ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX;
    
    private int useTimes;
    
    public WaveStrike() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(8)
                .setBlock(3)
                .setMagicNumber(2)
                .setExtraMagicNumber(DAMAGE_DOWN_MAGIC_INDEX, 2)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(4)
                .setPlusBlock(2)
                .setPlusMagicNumber(1)
                );
        this.useTimes = 0;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        useTimes++;
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new GainBlockAction(player, player, block));
        if (useTimes == magicNumber) {
            addToBot(new ModifyDamageAction(this.uuid, - getExtraMagicNumber(DAMAGE_DOWN_MAGIC_INDEX)));
        }
        this.rawDescription = cardStrings.DESCRIPTION + LocalizationUtils.formatDescription(cardStrings.EXTENDED_DESCRIPTION[0], this.useTimes);
        initializeDescription();
    }
    
    @Override
    public AbstractCard makeCopy() {
        WaveStrike copy = new WaveStrike();
        copy.useTimes = this.useTimes;
        return copy;
    }
    
//    @Override
//    public void applyPowers() {
//        super.applyPowers();
//        this.rawDescription = cardStrings.DESCRIPTION + LocalizationUtils.formatDescription(cardStrings.EXTENDED_DESCRIPTION[0], this.useTimes);
//        initializeDescription();
//    }
    
    

}
