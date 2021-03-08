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
public class Lancet2Strike extends SimpleRegainBlock {
    public static final String ID = ArknightsMod.makeID(Lancet2Strike.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    public Lancet2Strike() {
        super(ID, IMG, CardRarity.BASIC, 0);
        initBaseFields(new BasicSetting()
                .setRegainBlock(8)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusRegainBlock(4)
                );
        this.exhaust = true;
    }
}
