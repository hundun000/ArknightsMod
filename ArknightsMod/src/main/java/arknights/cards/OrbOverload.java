package arknights.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import arknights.ArknightsMod;
import arknights.actions.OrbOverloadAction;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.orbs.AbstractModOrb;

/**
 * @author hundun
 * Created on 2020/12/02
 */
public class OrbOverload extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(OrbOverload.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.SELF;  
    private static final CardType TYPE = CardType.SKILL;       

    private static final int COST = 0;

    
    public OrbOverload() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                
                );
        setUpgradeInfo(new UpgradeSetting()
                
                );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (!AbstractDungeon.player.orbs.isEmpty()) {
            AbstractOrb orb = player.orbs.get(0);
            if (orb instanceof AbstractModOrb) {
                addToBot(new OrbOverloadAction((AbstractModOrb)orb));
            }
        }
        
    }

}
