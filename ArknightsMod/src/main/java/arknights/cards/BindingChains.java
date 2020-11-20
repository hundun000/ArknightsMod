package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.CardTemplant;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;

/**
 * @author hundun
 * Created on 2020/11/20
 */
public class BindingChains extends AbstractModCard {

    public static final String ID = ArknightsMod.makeID(BindingChains.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON; 
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 2;

    
    public BindingChains() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(8)
                .setMagicNumber(2)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(5)
                .setPlusMagicNumber(1)
                );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new DrawCardAction(player, magicNumber));
        if (player.hand.size() > 0) {
            addToBot(new PutOnDeckAction(player, player, 1, false));
        }
    }

}
