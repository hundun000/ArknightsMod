package arknights.cards.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.megacrit.cardcrawl.cards.AbstractCard;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.BaseDeployCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.cards.derivations.ChargeAlpha;

/**
 * @author hundun
 * Created on 2020/11/05
 */
public class FangDeploy extends BaseDeployCard {

    public static final String ID = ArknightsMod.makeID(FangDeploy.class);
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.SPECIAL;     
    
    private static final int COST = 1;  

    public FangDeploy() { 
        super(ID, IMG, COST, RARITY);
        initGiveCardsSetting(Arrays.asList(new ChargeAlpha()), 1, 1);
        
    }

}
