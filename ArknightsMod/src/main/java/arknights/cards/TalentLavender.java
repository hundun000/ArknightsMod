package arknights.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;

/**
 * @author hundun
 * Created on 2020/11/17
 */
public class TalentLavender extends AbstractModCard {

    public static final String ID = ArknightsMod.makeID(TalentLavender.class.getSimpleName()); // DELETE THIS ONE.
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON; 
    private static final CardTarget TARGET = CardTarget.SELF;  
    private static final CardType TYPE = CardType.POWER;    
    
    private static final int COST = 1;  
    private static final int STACK = 3;
    private static final int UPGRADE_PLUS_STACK = 2;
    
    public TalentLavender() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setMagicNumber(STACK)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusMagicNumber(UPGRADE_PLUS_STACK)
                );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                    player,
                    player,
                    new PlatedArmorPower(player, this.magicNumber),
                    this.magicNumber
                )
            );
    }

}
