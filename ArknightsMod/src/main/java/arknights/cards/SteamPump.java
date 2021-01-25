package arknights.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;

/**
 * @author hundun
 * Created on 2020/11/19
 */
public class SteamPump extends AbstractModCard {

    public static final String ID = ArknightsMod.makeID(SteamPump.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);
    
    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.SELF;  
    private static final CardType TYPE = CardType.SKILL;       
    private static final int COST = 1;
    
    
    public SteamPump() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setBlock(8)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusBlock(3)
                );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new SelectCardsInHandAction(1, "选择", list -> {
            if (!list.isEmpty()) {
                AbstractCard card = list.get(0);
                if (card instanceof Burn) {
                    addToBot(new ExhaustSpecificCardAction(card, player.hand, true));
                } else {
                    addToBot(new DiscardSpecificCardAction(card, player.hand));
                }
            }
        }));
                
        //addToBot(new DiscardWantTargetCardAction(player, 1, Burn.class));
        
    }

}
