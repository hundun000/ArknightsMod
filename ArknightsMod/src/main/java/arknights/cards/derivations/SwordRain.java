package arknights.cards.derivations;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.util.LocalizationUtils;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/11/13
 */
public class SwordRain extends AbstractModCard {
    
    public static final String ID = ArknightsMod.makeID(SwordRain.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 0;
    
    private static final int GIVE_ENERGY_NUM = 1;
    
    private static final int PLUS_GIVE_ENERGY_NUM = 1;
    
    private static final int PREPARE_COUNT_THRESHOLD_FOR_USE = 3;
    private static final int WEAK_STACK_NUM = 1;
    private static final int PLUS_WEAK_STACK_NUM = 1;
    private static final int WEAK_MAGIC_INDEX = ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX;
    
    public SwordRain() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(7)
                .setMagicNumber(GIVE_ENERGY_NUM)
                .setExtraMagicNumber(WEAK_MAGIC_INDEX, WEAK_STACK_NUM)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                .setPlusMagicNumber(PLUS_GIVE_ENERGY_NUM)
                .setPlusExtraMagicNumber(WEAK_MAGIC_INDEX, PLUS_WEAK_STACK_NUM)
                );
        this.selfRetain = true;
        this.isMultiDamage = true;
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new GainEnergyAction(magicNumber));
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if ((!mo.isDead) && (!mo.isDying)) {
                addToBot(new StunMonsterAction(mo, player, getExtraMagicNumber(WEAK_MAGIC_INDEX)));
            }
        }
        
        clearPrepareCount();
        ArknightsMod.logger.info("SwordRain use(). getPrepareCount = " + getPrepareCount());
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }
    
    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        clearPrepareCount();
        ArknightsMod.logger.info("SwordRain onMoveToDiscard(). getPrepareCount = " + getPrepareCount());
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }
    
    @Override
    public void onRetained() {
        super.onRetained();
        addPrepareCount(1);
        ArknightsMod.logger.info("SwordRain onRetained(). getPrepareCount = " + getPrepareCount());
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }
    
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return getPrepareCount() >= PREPARE_COUNT_THRESHOLD_FOR_USE;
    }

}
