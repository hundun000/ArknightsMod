package arknights.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EntanglePower;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;

/**
 * @author hundun
 * Created on 2020/11/26
 */
public class Cooking extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(Cooking.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.NONE;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 2;

    
    public Cooking() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setBlock(15)
                .setMagicNumber(2)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusBlock(5)
                .setPlusMagicNumber(1)
                );
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new HealAction(player, player, this.magicNumber));
        addToBot(new ApplyPowerAction(player, player, new EntanglePower(player)));
        addToBot(new GainBlockAction(player, player, block));
    }

}
