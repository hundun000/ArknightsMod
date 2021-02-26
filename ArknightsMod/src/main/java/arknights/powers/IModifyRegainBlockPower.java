package arknights.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;

/**
 * @author hundun
 * Created on 2021/02/27
 */
public interface IModifyRegainBlockPower {
    float modifyRegainBlock(float amount, AbstractCard card);
}
