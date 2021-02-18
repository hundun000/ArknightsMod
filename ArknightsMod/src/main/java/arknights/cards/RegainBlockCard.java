package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.CardTemplant;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.manager.MoreGameActionManager;
import arknights.util.LocalizationUtils;

/**
 * @author hundun
 * Created on 2021/02/06
 */
public class RegainBlockCard extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(RegainBlockCard.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.NONE;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 0;

    private int currentRegainAmountLimit = -1;
    
    public RegainBlockCard() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setMagicNumber(8)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusMagicNumber(3)
                );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int currentRegainAmount = Math.min(currentRegainAmountLimit, this.magicNumber);
        MoreGameActionManager.countRegainBlock(currentRegainAmount);
        addToBot(new GainBlockAction(player, player, currentRegainAmount));
    }
    
    @Override
    public void applyPowers() {
        super.applyPowers();
        
        // update currentRegainAmount
        currentRegainAmountLimit = MoreGameActionManager.getCurrentRegainAmountLimit();
        
        
        this.rawDescription = cardStrings.DESCRIPTION + LocalizationUtils.formatDescription(cardStrings.EXTENDED_DESCRIPTION[0], currentRegainAmountLimit);
        initializeDescription();
    }
    
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

}
