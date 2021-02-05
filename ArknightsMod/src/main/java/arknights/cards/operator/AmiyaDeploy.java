package arknights.cards.operator;

import java.util.Arrays;

import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.BaseDeployCard;
import arknights.cards.derivations.ChargeAlpha;

/**
 * @author hundun
 * Created on 2021/02/06
 */
public class AmiyaDeploy extends BaseDeployCard {

    public static final String ID = ArknightsMod.makeID(AmiyaDeploy.class);
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);


    public AmiyaDeploy() { 
        super(ID, IMG);
        initGiveCardsSetting(Arrays.asList(new ChargeAlpha()));
        
    }
}
