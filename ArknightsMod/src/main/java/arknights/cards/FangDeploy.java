package arknights.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.BaseOperatorCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.cards.derivations.ChargeAlpha;

/**
 * @author hundun
 * Created on 2020/11/05
 */
public class FangDeploy extends BaseOperatorCard {

    public static final String ID = ArknightsMod.makeID(FangDeploy.class);
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);

    private static final CardRarity RARITY = CardRarity.SPECIAL;     
    
    private static final int COST = 1;  

    public FangDeploy() { 
        super(ID, IMG, COST, RARITY);
        initBaseFields(new BasicSetting()
                .setDamage(3)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(2)
                );
        
    }
    
    @Override
    protected AbstractCard getGiveCard() {
        return new ChargeAlpha();
    }


}
