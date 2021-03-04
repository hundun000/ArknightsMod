package arknights;

import basemod.*;
import basemod.interfaces.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.rewards.RewardSave;

import arknights.characters.ArknightsPlayer;
import arknights.characters.Doctor;
import arknights.events.IdentityCrisisEvent;
import arknights.events.OriginiumSlugRaceEvent;
import arknights.monster.Puncturer;
import arknights.potions.PlaceholderPotion;
import arknights.relics.BattleRecords;
import arknights.relics.HumanResource;
import arknights.relics.StereoProjectorRelic;
import arknights.relics.UrsusBreadRelic;
import arknights.rewards.PotentialReward;
import arknights.rewards.PotentialRewardTypePatch;
import arknights.util.AbstractDungeonHelper;
import arknights.util.IDCheckDontTouchPls;
import arknights.util.TextureLoader;
import arknights.variables.SpCountVariable;
import arknights.variables.SpThresholdVariable;
import arknights.variables.MagicDamageVariable;
import arknights.variables.RegainBlockVariable;
import arknights.variables.SecondMagicNumberVariable;
import arknights.variables.ThirdMagicNumberVariable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class ArknightsMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(ArknightsMod.class.getName());
    private static String modID = "arknights";

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Arknights";
    private static final String AUTHOR = "hundun"; // And pretty soon - You!
    private static final String DESCRIPTION = "A base for Slay the Spire to start your own mod from, feat. the Default.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);
    public static final Color STARLIGHT = CardHelper.getColor(0, 10, 125);
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
    
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
  
    // Card backgrounds - The actual rectangular card.
    public static final String RESOURCES_FOLDER = modID + "Resources";
    public static final String IMAGES_FOLDER = RESOURCES_FOLDER + "/images";
    public static final String LOCALIZATION_FOLDER = RESOURCES_FOLDER + "/localization";
    
    private static final String ATTACK_DEFAULT_GRAY = IMAGES_FOLDER + "/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = IMAGES_FOLDER + "/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = IMAGES_FOLDER + "/512/bg_power_default_gray.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = IMAGES_FOLDER + "/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = IMAGES_FOLDER + "/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = IMAGES_FOLDER + "/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = IMAGES_FOLDER + "/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = IMAGES_FOLDER + "/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = IMAGES_FOLDER + "/1024/card_default_gray_orb.png";
    
    // Character assets
    private static final String THE_DEFAULT_BUTTON = IMAGES_FOLDER + "/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = IMAGES_FOLDER + "/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = IMAGES_FOLDER + "/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = IMAGES_FOLDER + "/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = IMAGES_FOLDER + "/char/defaultCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = IMAGES_FOLDER + "/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = IMAGES_FOLDER + "/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = IMAGES_FOLDER + "/char/defaultCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================

    
    public static String makeCardPath(String resourcePath) {
        return IMAGES_FOLDER + "/cards/" + resourcePath;
    }
    
    public static String makeCardPngPath(Class<?> clazz) {
        return makeCardPath(clazz.getSimpleName() + ".png");
    }
    
    public static String makeRelicPath(String resourcePath) {
        return IMAGES_FOLDER + "/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return IMAGES_FOLDER+ "/relics/outline/" + resourcePath;
    }
    //images/monsters/theBottom/cultist/skeleton.atlas
    public static String makeMonsterSkeletonAtlasPath(Class<?> monsterClazz) {
        return IMAGES_FOLDER+ "/monsters/" + monsterClazz.getSimpleName() + "/skeleton.atlas";
    }
    
    public static String makeMonsterSkeletonJsonPath(Class<?> monsterClazz) {
        return IMAGES_FOLDER+ "/monsters/" + monsterClazz.getSimpleName() + "/skeleton.json";
    }
    
    public static String makeOrbPath(String resourcePath) {
        return IMAGES_FOLDER + "/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return IMAGES_FOLDER + "/powers/" + resourcePath;
    }
    
    public static String makeRewardPngPath(Class<?> clazz) {
        return IMAGES_FOLDER + "/rewards/" + clazz.getSimpleName() + ".png";
    }
    
    public static String makePowerPngPath(Class<?> clazz, int iconSize) {
        return makeCardPath(clazz.getSimpleName() + iconSize +".png");
    }
    
    public static String makeEventPath(String resourcePath) {
        return IMAGES_FOLDER + "/events/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public ArknightsMod() {
        
    }
    public void subscribeToMainGame() {
    
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
        // reload
        setModID(modID);
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:
        
        // 1. Go to your resources folder in the project panel, and refactor> rename theDefaultResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of theDefault with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        
        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theDefault. They get loaded before getID is a thing.
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + ArknightsPlayer.Enums.ARKNIGHTS_CARD_COLOR.toString());
        BaseMod.addColor(ArknightsPlayer.Enums.ARKNIGHTS_CARD_COLOR, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Creating the color " + ArknightsPlayer.Enums.ARKNIGHTS_CARD_COLOR.toString());
        BaseMod.addColor(ArknightsPlayer.Enums.ARKNIGHTS_OPERATOR_CARD_COLOR, STARLIGHT, STARLIGHT, STARLIGHT,
                STARLIGHT, STARLIGHT, STARLIGHT, STARLIGHT,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig(MODNAME, MODNAME + "Config", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = ArknightsMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = ArknightsMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = ArknightsMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(RESOURCES_FOLDER); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        ArknightsMod mod = new ArknightsMod();
        mod.subscribeToMainGame();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + ArknightsPlayer.Enums.ARKNIGHTS_PLAYER_CLASS.toString());
        
        BaseMod.addCharacter(new Doctor(),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, ArknightsPlayer.Enums.ARKNIGHTS_PLAYER_CLASS);
        
        receiveEditPotions();
        logger.info("Added " + ArknightsPlayer.Enums.ARKNIGHTS_PLAYER_CLASS.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

     // =============== EVENTS =================
        
        BaseMod.registerCustomReward(
                PotentialRewardTypePatch.POTENTIAL,
                (rewardSave) -> { // this handles what to do when this quest type is loaded.
                    return new PotentialReward(rewardSave.id, "from save");
                }, 
                (potentialReward) -> { // this handles what to do when this quest type is saved.
                    return new RewardSave(PotentialRewardTypePatch.POTENTIAL.name(), ((PotentialReward)potentialReward).operatorCardId);
                });
        
        // =============== EVENTS =================
        
        BaseMod.addEvent(OriginiumSlugRaceEvent.ID, OriginiumSlugRaceEvent.class, Exordium.ID);
        BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        
        // =============== MONSTERS =================
        BaseMod.addMonster(Puncturer.ID + "x2", () -> new MonsterGroup(new AbstractMonster[] {
                new Puncturer(-230.0F, 15.0F),
                new Puncturer(100.0F, 25.0F)
        }));
        BaseMod.addMonster(Puncturer.ID + "x1", () -> new Puncturer(-230.0F, 15.0F));
        
        BaseMod.addMonsterEncounter(Exordium.ID, new MonsterInfo(Puncturer.ID + "x1", 10.0F));
        BaseMod.addMonsterEncounter(Exordium.ID, new MonsterInfo(Puncturer.ID + "x2", 10.0F));
        
        AbstractDungeonHelper.addOperatorCards();
        
        logger.info("Done loading badge Image and mod options");
    }

    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, ArknightsPlayer.Enums.ARKNIGHTS_PLAYER_CLASS);
        
        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // Take a look at https://github.com/daviscook477/BaseMod/wiki/AutoAdd
        // as well as
        // https://github.com/kiooeht/Bard/blob/e023c4089cc347c60331c78c6415f489d19b6eb9/src/main/java/com/evacipated/cardcrawl/mod/bard/BardMod.java#L319
        // for reference as to how to turn this into an "Auto-Add" rather than having to list every relic individually.
        // Of note is that the bard mod uses it's own custom relic class (not dissimilar to our AbstractDefaultCard class for cards) that adds the 'color' field,
        // in order to automatically differentiate which pool to add the relic too.

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
         BaseMod.addRelicToCustomPool(new StereoProjectorRelic(), ArknightsPlayer.Enums.ARKNIGHTS_CARD_COLOR);
        BaseMod.addRelicToCustomPool(new UrsusBreadRelic(), ArknightsPlayer.Enums.ARKNIGHTS_CARD_COLOR);
        BaseMod.addRelicToCustomPool(new BattleRecords(), ArknightsPlayer.Enums.ARKNIGHTS_CARD_COLOR);
        BaseMod.addRelicToCustomPool(new HumanResource(), ArknightsPlayer.Enums.ARKNIGHTS_CARD_COLOR);
        
        // This adds a relic to the Shared pool. Every character can find this relic.

        
        // Mark relics as seen (the others are all starters so they're marked as seen in the character file

        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        //BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new SecondMagicNumberVariable());
        BaseMod.addDynamicVariable(new ThirdMagicNumberVariable());
        BaseMod.addDynamicVariable(new MagicDamageVariable());
        BaseMod.addDynamicVariable(new SpCountVariable());
        BaseMod.addDynamicVariable(new SpThresholdVariable());
        BaseMod.addDynamicVariable(new RegainBlockVariable());
        
        logger.info("Adding cards");
        // Add the cards
        // Don't delete these default cards yet. You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        // This method automatically adds any cards inside the cards package, found under yourModName.cards.
        // For more specific info, including how to exclude classes from being added:
        // https://github.com/daviscook477/BaseMod/wiki/AutoAdd

        // The ID for this function isn't actually your modid as used for prefixes/by the getModID() method.
        // It's the mod id you give MTS in ModTheSpire.json - by default your artifact ID in your pom.xml

        new AutoAdd("hundun:ArknightsMod")
            .packageFilter("arknights.cards")
            .setDefaultSeen(true)
            .cards();
        
        // .setDefaultSeen(true) unlocks the cards
        // This is so that they are all "seen" in the library,
        // for people who like to look at the card list before playing your mod

        logger.info("Done adding cards!");
    }
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    private String getLanguageFolder() {
        String languageFolder;
        switch (Settings.language) {
            case KOR:
                languageFolder = "/kor";
                break;
            case ZHS:
                languageFolder = "/zhs";
                break;
            case ZHT:
                languageFolder = "/zht";
                break;
            case FRA:
                languageFolder = "/fra";
                break;
            case JPN:
                languageFolder = "/jpn";
                break;
            default:
                languageFolder = "/zhs";
        }
        return languageFolder;
    }
    
    
    @Override
    public void receiveEditStrings() {
        
        String languageFolder = getLanguageFolder();
        
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        BaseMod.loadCustomStringsFile(UIStrings.class,
                LOCALIZATION_FOLDER + languageFolder + "/UI-Strings.json");
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                LOCALIZATION_FOLDER + languageFolder + "/Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                LOCALIZATION_FOLDER + languageFolder + "/Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                LOCALIZATION_FOLDER + languageFolder + "/Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                LOCALIZATION_FOLDER + languageFolder + "/Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                LOCALIZATION_FOLDER + languageFolder + "/Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                LOCALIZATION_FOLDER + languageFolder + "/Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                LOCALIZATION_FOLDER + languageFolder + "/Orb-Strings.json");
        
        // Monster
        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                LOCALIZATION_FOLDER + languageFolder + "/Monster-Strings.json");
        
        logger.info("Done edittting strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        String languageFolder = getLanguageFolder();
        Gson gson = new Gson();
        String json = Gdx.files.internal(LOCALIZATION_FOLDER + languageFolder + "/Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
            logger.info("load {} keywords.", keywords.length);
        } else {
            logger.warn("not load keywords!");
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
    
    public static String makeID(Class<?> clazz) {
        return makeID(clazz.getSimpleName());
    }

    
}
