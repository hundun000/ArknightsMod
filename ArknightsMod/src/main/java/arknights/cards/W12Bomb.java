package arknights.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.powers.W12BombPower;

/**
 * @author hundun
 * Created on 2020/11/27
 */
public class W12Bomb extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(W12Bomb.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  
    private static final CardType TYPE = CardType.SKILL;       

    private static final int COST = 2;

    
    public W12Bomb() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(15)
                .setMagicNumber(2)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(5)
                .setPlusMagicNumber(1)
                );
    }
    
    

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        List<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
        List<AbstractMonster> tempMonsters = new ArrayList<>(monsters);
        tempMonsters.removeIf(item -> item.isDeadOrEscaped());
        Collections.sort(tempMonsters, new Comparator<AbstractMonster>() {
            @Override
            public int compare(AbstractMonster o1, AbstractMonster o2) {
                return o2.currentHealth - o1.currentHealth;
            }
        });
        for (int i = 0; i < magicNumber && i < tempMonsters.size(); i++) {
            AbstractMonster target = tempMonsters.get(i);
            addToBot(new ApplyPowerAction(target, player, new W12BombPower(target, 3, this.damage)));
        }
    }
    
//    @Override
//    public void initializeDescription() {
//        super.initializeDescription();
//        StringBuilder builder = new StringBuilder();
//        description.forEach(item -> builder.append(item.text).append(","));
//        ArknightsMod.logger.info("D12::after initializeDescription, description = {}", builder.toString());
//    }

}
