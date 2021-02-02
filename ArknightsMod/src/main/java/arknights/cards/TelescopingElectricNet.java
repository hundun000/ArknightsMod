package arknights.cards;

import com.megacrit.cardcrawl.actions.unique.GreedAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;

/**
 * @author hundun
 * Created on 2020/11/20
 */
public class TelescopingElectricNet extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(TelescopingElectricNet.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.RARE; 
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 2;

    
    public TelescopingElectricNet() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(10)
                .setMagicNumber(10)
                //.setExtraMagicNumber(ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX, 1)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(5)
                .setPlusMagicNumber(5)
                //.setPlusExtraMagicNumber(ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX, 1)
                );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if ((!mo.isDead) && (!mo.isDying)) {
                addToBot(new GreedAction(mo, new DamageInfo(player, this.damage, this.damageTypeForTurn), this.magicNumber));
            }
        }
        //addToBot(new DrawCardAction(player, this.extraMagicNumbers[ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX]));
    }

}
