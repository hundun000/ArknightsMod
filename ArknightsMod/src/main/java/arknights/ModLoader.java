package arknights;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

/**
 * @author hundun
 * Created on 2021/03/09
 */
@SpireInitializer
public class ModLoader {

    public static void initialize() {
        ArknightsMod.logger.info("========================= Initializing Default Mod. Hi. =========================");
        ArknightsMod mod = new ArknightsMod();
        mod.subscribeToMainGame();
        ArknightsMod.logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
}
