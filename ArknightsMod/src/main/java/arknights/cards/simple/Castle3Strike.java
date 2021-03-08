package arknights.cards.simple;

import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;

/**
 * @author hundun
 * Created on 2021/02/22
 */
public class Castle3Strike extends SimpleStrike {
    public static final String ID = ArknightsMod.makeID(Castle3Strike.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    public Castle3Strike() {
        super(ID, IMG, CardRarity.BASIC, 0);
        initBaseFields(new BasicSetting()
                .setDamage(3)
                .setBlock(2)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(2)
                .setPlusBlock(1)
                );
        this.exhaust = true;
    }
}
