package arknights.cards.simple;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.CardTemplant;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import basemod.AutoAdd;

/**
 * @author hundun
 * Created on 2021/02/06
 */
public class NoirCorneDefend extends SimpleDefend {

    public static final String ID = ArknightsMod.makeID(NoirCorneDefend.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    
    public NoirCorneDefend() {
        super(ID, IMG, CardRarity.BASIC, 1);
        initBaseFields(new BasicSetting()
                .setBlock(5)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusBlock(3)
                );
    }

}
