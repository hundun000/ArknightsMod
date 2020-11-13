package arknights.cards.derivations;

import static arknights.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.DefaultMod;
import arknights.cards.AbstractModCard;
import arknights.cards.FiveStarVanguardDeploy;
import arknights.cards.demo.DefaultCommonAttack;
import arknights.cards.AbstractModCard.BasicSetting;
import arknights.cards.AbstractModCard.UpgradeSetting;
import arknights.characters.Doctor;
import basemod.abstracts.CustomCard;

/**
 * @author hundun
 * Created on 2020/11/13
 */
public class SwordRain extends AbstractModCard {
    
    public static final String ID = DefaultMod.makeID(SwordRain.class.getSimpleName()); // DELETE THIS ONE.
    public static final String IMG = makeCardPath(AbstractModCard.class.getSimpleName() + ".png");// "public static final String IMG = makeCardPath("${NAME}.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       
    public static final CardColor COLOR = Doctor.Enums.COLOR_GRAY;
    private static final int COST = 0;
    
    public SwordRain() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        setBasicInfo(new BasicSetting()
                .setDamage(7)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                );
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

}
