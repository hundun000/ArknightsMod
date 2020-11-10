package arknights.cards.base;

import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.cards.AbstractModCard;

/**
 * @author hundun
 * Created on 2020/11/10
 */
public abstract class BaseSummonCard extends AbstractModCard {

    public BaseSummonCard(String id, String img, int cost, CardRarity rarity) {
        super(id, img, cost, CardType.SKILL, rarity, CardTarget.SELF);
        
    }

}
