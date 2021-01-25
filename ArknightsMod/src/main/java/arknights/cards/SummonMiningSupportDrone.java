package arknights.cards;

import com.megacrit.cardcrawl.orbs.AbstractOrb;
import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.BaseSummon;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.orbs.MiningSupportDrone;

/**
 * @author hundun
 * Created on 2020/11/10
 */
public class SummonMiningSupportDrone extends BaseSummon {

    public static final String ID = ArknightsMod.makeID(SummonMiningSupportDrone.class);
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    
    private static final int COST = 1;  
    
    // special const
    private static final int SUMMON_NUM = 1;  
    private static final int UPGRADED_SUMMON_NUM = 1;  
    
    public SummonMiningSupportDrone() { 
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
        return new MiningSupportDrone();
    }

}
