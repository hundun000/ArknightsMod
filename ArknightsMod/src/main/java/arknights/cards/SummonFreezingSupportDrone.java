package arknights.cards;

import com.megacrit.cardcrawl.orbs.AbstractOrb;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.BaseSummon;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.orbs.FreezingSupportDrone;

/**
 * @author hundun
 * Created on 2020/11/10
 */
public class SummonFreezingSupportDrone extends BaseSummon {

    public static final String ID = ArknightsMod.makeID(SummonFreezingSupportDrone.class);
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    
    private static final int COST = 1;  
    
    // special const
    private static final int SUMMON_NUM = 1;  
    private static final int UPGRADED_SUMMON_NUM = 1;  
    
    public SummonFreezingSupportDrone() { 
        super(ID, IMG, COST, RARITY);
        initBaseFields(new BasicSetting()
                .setMagicNumber(SUMMON_NUM)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusMagicNumber(UPGRADED_SUMMON_NUM)
                );
    }

    @Override
    protected AbstractOrb getSummonOrb() {
        return new FreezingSupportDrone();
    }

}
