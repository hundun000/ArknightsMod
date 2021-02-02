package arknights.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;

/**
 * @author hundun
 * Created on 2020/11/21
 */
public class RatSwarm extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(RatSwarm.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON; 
    private static final CardTarget TARGET = CardTarget.NONE;  
    private static final CardType TYPE = CardType.SKILL;       

    private static final int COST = -2;

    
    public RatSwarm() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setBlock(5)
                .setMagicNumber(0)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusBlock(3)
                .setPlusMagicNumber(1)
                );
    }
    
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // do nothing
    }
    
    @Override
    public void triggerOnManualDiscard() {
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new GainBlockAction(player, player, block));
        if (magicNumber > 0) {
            addToBot(new DrawCardAction(player, magicNumber));
        }
    }

}
