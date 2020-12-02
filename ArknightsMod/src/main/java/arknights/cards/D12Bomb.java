package arknights.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.CardTemplant;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.powers.D12BombPower;

/**
 * @author hundun
 * Created on 2020/11/27
 */
public class D12Bomb extends AbstractModCard {
    
    public static final String ID = ArknightsMod.makeID(D12Bomb.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  
    private static final CardType TYPE = CardType.SKILL;       

    private static final int COST = 2;

    
    public D12Bomb() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(15)
                .setMagicNumber(1)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(5)
                .setPlusMagicNumber(1)
                );
    }
    
    

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        List<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
        List<AbstractMonster> topMonsters = new ArrayList<>(monsters);
        Collections.sort(topMonsters, new Comparator<AbstractMonster>() {
            @Override
            public int compare(AbstractMonster o1, AbstractMonster o2) {
                return o2.currentHealth - o1.currentHealth;
            }
        });
        for (int i = 0; i < magicNumber && i < topMonsters.size(); i++) {
            AbstractMonster target = topMonsters.get(i);
            addToBot(new ApplyPowerAction(target, player, new D12BombPower(target, 3, this.damage)));
        }
    }
    
    @Override
    public void initializeDescription() {
        super.initializeDescription();
        StringBuilder builder = new StringBuilder();
        description.forEach(item -> builder.append(item.text).append(","));
        ArknightsMod.logger.info("D12::after initializeDescription, description = {}", builder.toString());
    }

}
