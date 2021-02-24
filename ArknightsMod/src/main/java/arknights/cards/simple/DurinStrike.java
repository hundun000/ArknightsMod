package arknights.cards.simple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
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
public class DurinStrike extends ArknightsModCard {

    public static final String ID = ArknightsMod.makeID(DurinStrike.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    protected static final CardTarget TARGET = CardTarget.ENEMY;  
    protected static final CardType TYPE = CardType.ATTACK;
    
    public DurinStrike() {
        super(ID, IMG, 1, TYPE, CardRarity.BASIC, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(6)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                );
        this.tags.add(ArknightsCardTag.SPELL_DAMAGE);
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SMASH));
    }

}