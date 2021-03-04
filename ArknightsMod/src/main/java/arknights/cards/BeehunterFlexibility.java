package arknights.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

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
public class BeehunterFlexibility extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(BeehunterFlexibility.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;
    
    public BeehunterFlexibility() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(5)
                .setBlock(2)
                .setMagicNumber(4)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                .setPlusBlock(1)
                );
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot((AbstractGameAction)new ApplyPowerAction(player, player, new DexterityPower(player, this.magicNumber)));
        addToBot((AbstractGameAction)new ApplyPowerAction(player, player, new LoseDexterityPower(player, this.magicNumber)));
    }


}
