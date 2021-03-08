package arknights.cards.simple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsCardTag;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.CardTemplant;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import basemod.AutoAdd;

/**
 * @author hundun
 * Created on 2021/02/06
 */
public class F12Strike extends ArknightsModCard {

    public static final String ID = ArknightsMod.makeID(F12Strike.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    protected static final CardTarget TARGET = CardTarget.ENEMY;  
    protected static final CardType TYPE = CardType.ATTACK;
    
    public F12Strike() {
        super(ID, IMG, 1, TYPE, CardRarity.BASIC, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(6)
                .enableSpellDamageType()
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                );
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
    }

}
