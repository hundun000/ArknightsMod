package arknights.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;

import arknights.ArknightsMod;
import arknights.actions.RangedGuardTwiceDamageAction;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.util.LocalizationUtils;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/12/02
 */
public class CutterCrimsonCrescent extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(CutterCrimsonCrescent.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 2;
    
    public CutterCrimsonCrescent() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(5)
                .setBlock(2)
                .setMagicNumber(5)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                .setPlusBlock(1)
                );
        
        initializeDescription();
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {

        addToBot(new GainBlockAction(player, player, block));
        int finalDamage = this.damage;
        if (monster.hasPower(FlightPower.POWER_ID)) {
            finalDamage *= 2;
        }
        addToBot(new DamageAction(monster, new DamageInfo(player, finalDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
    }


    
    
}
