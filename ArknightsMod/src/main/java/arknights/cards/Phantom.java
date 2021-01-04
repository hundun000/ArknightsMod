package arknights.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.cards.derivations.PhantomInTheMirror;

/**
 * @author hundun
 * Created on 2020/11/21
 */
public class Phantom extends AbstractModCard {
    
    public static final String ID = ArknightsMod.makeID(Phantom.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON; 
    private static final CardTarget TARGET = CardTarget.NONE;  
    private static final CardType TYPE = CardType.SKILL;       

    private static final int COST = -2;

    
    public Phantom() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setMagicNumber(0)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusMagicNumber(1)
                .setUpgradeCardToPreview(true)
                );
        this.cardsToPreview = new PhantomInTheMirror();
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
        if (magicNumber > 0) {
            addToBot(new DrawCardAction(player, magicNumber));
        }
        AbstractCard gainCard = new PhantomInTheMirror();
        if (upgraded) {
            gainCard.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(gainCard, 1));
    }

}
