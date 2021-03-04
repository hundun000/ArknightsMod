package arknights.cards.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.ArknightsMod;
import arknights.cards.AmiyaChimera;
import arknights.cards.FangStrike;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.BaseDeployCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;

/**
 * @author hundun
 * Created on 2020/11/05
 */
public class FangDeploy extends BaseDeployCard {

    public static final String ID = ArknightsMod.makeID(FangDeploy.class);
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);


    public FangDeploy() { 
        super(ID, IMG);
        initGiveCardsSetting(Arrays.asList(new FangStrike()));
    }

}
