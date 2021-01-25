package arknights.cards.derivations;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
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
        this.exhaust = true;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(player, player, new EnergizedPower(player, magicNumber), magicNumber));
        
        
        
        //addToBot(new VFXAction(player, new IntimidateEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 1.0F));
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if ((!mo.isDead) && (!mo.isDying)) {
                addToBot(new StunMonsterAction(mo, player, getExtraMagicNumber(WEAK_MAGIC_INDEX)));
            }
        }
    }

}
