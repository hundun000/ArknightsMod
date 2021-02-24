package arknights.cards.operator;

import java.util.Arrays;

import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.ArknightsMod;
import arknights.cards.FangStrike;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.BaseDeployCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;


/**
 * @author hundun
 * Created on 2020/11/05
 */
public class TexasDeploy extends BaseDeployCard {

    public static final String ID = ArknightsMod.makeID(TexasDeploy.class); // DELETE THIS ONE.
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

  
    public TexasDeploy() { 
        super(ID, IMG);
        initGiveCardsSetting(Arrays.asList(new FangStrike()));
        
    }

}
