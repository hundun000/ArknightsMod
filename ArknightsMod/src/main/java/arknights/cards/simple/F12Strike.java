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
public class F12Strike extends SimpleStrike {

    public static final String ID = ArknightsMod.makeID(F12Strike.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    
    public F12Strike() {
        super(ID, IMG, CardRarity.BASIC, 1);
        initBaseFields(new BasicSetting()
                .setDamage(6)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                );
    }

}
