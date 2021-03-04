package arknights.cards.operator;

import java.util.Arrays;

import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import arknights.ArknightsMod;
import arknights.cards.BeehunterFlexibility;
import arknights.cards.BeehunterSoaringFists;
import arknights.cards.FangStrike;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.BaseDeployCard;

/**
 * @author hundun
 * Created on 2021/03/01
 */
public class BeehunterDeploy extends BaseDeployCard {

    public static final String ID = ArknightsMod.makeID(BeehunterDeploy.class);
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);


    public BeehunterDeploy() { 
        super(ID, IMG);
        initGiveCardsSetting(Arrays.asList(new BeehunterFlexibility(), new BeehunterSoaringFists()));
    }

}
