package studio.awel.FancyCasinos.config;

import org.bukkit.Material;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.ConfKey;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.util.List;

public interface PrimaryConfig {

    // Main UI

    @ConfComments("The editable attributes for the primary casino UI")
    @ConfKey("gui.name")
    @AnnotationBasedSorter.Order(1)
    @ConfDefault.DefaultString("<#fcba03>ꜰᴀɴᴄʏ ᴄᴀꜱɪɴᴏ")
    String guiName();

    @ConfComments({"The layout of your ui written in this file is how it will show in the menu", "With the \"-\" being your blank object. The \"s\" is for slots, \"c\" for crash", "\"m\" for mines, \"b\" for black jack and, \"x\" for exit."
            , " ", "Ensure that it is formatted correctly, with 9 characters per line and, between 1 and 6 lines."})
    @ConfKey("gui.layout.ui")
    @AnnotationBasedSorter.Order(1)
    @ConfDefault.DefaultString("---------\n-s~c~m~b-\n---------")
    String guiLayout();

    @ConfComments({"The blank object is the default object for your menu \"AIR\" is an example", "Ensure that you use a valid minecraft item name while declaring items", "Failure to correctly define items will result in a loading error."})
    @ConfKey("gui.layout.blank-object")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("GRAY_STAINED_GLASS_PANE")
    Material blankObject();

    // Custom Items
    @ConfComments({
            "Define custom items that can be used in the menu layout.",
            "Format: CHARACTER:MATERIAL:NAME:LORE",
            "Example: v:BOOK:&6&lInstructions:&7- Click to see how to play</nl>&7- Have fun!",
            "LORE can contain </nl> to add new lines"
    })
    @ConfKey("gui.layout.custom-items")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultStrings({
            "~:AIR:&f:No item here",
            "v:KNOWLEDGE_BOOK:<#fcba03>ɪɴꜱᴛʀᴜᴄᴛɪᴏɴꜱ:&f<#fcba03>❙ &fClick to see how to play</nl>&f<#fcba03>❙ &fHave fun and gamble responsibly!"
    })
    List<String> customItemDefinitions();

    // Slots

    @ConfComments("Attributes of the slots item in the main menu")
    @ConfKey("gui.layout.slots.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#ffd700>ꜱʟᴏᴛꜱ")
    String slotsName();

    @ConfKey("gui.layout.slots.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("GOLD_BLOCK")
    Material slotsItem();

    @ConfKey("gui.layout.slots.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&f<#ffd700>❙ &fTest your <#ffd700>ʟᴜᴄᴋ &fwith our slot machines!</nl>&f<#ffd700>❙ &fMatch symbols to win <#ffd700>ʙɪɢ ᴘʀɪᴢᴇꜱ&f!</nl>&f<#ffd700>❙ &fCurrent Jackpot: <#ffd700>ᴍᴀꜱꜱɪᴠᴇ")
    String slotsDescription();

    // Mines

    @ConfComments("Attributes of the mines item in the main menu")
    @ConfKey("gui.layout.mines.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#ff5555>ᴍɪɴᴇꜱ")
    String minesName();

    @ConfKey("gui.layout.mines.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("TNT")
    Material minesItem();

    @ConfKey("gui.layout.mines.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&f<#ff5555>❙ &fNavigate through a field of <#ff5555>ᴇxᴘʟᴏꜱɪᴠᴇꜱ&f!</nl>&f<#ff5555>❙ &fEvery safe step increases your <#ff5555>ʀᴇᴡᴀʀᴅ&f!</nl>&f<#ff5555>❙ &fHow far will you go?")
    String minesDescription();

    // Crash

    @ConfComments("Attributes of the crash item in the main menu")
    @ConfKey("gui.layout.crash.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#5555ff>ᴄʀᴀꜱʜ")
    String crashName();

    @ConfKey("gui.layout.crash.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("DRAGON_BREATH")
    Material crashItem();

    @ConfKey("gui.layout.crash.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&f<#5555ff>❙ &fWatch the <#5555ff>ᴍᴜʟᴛɪᴘʟɪᴇʀ &frise!</nl>&f<#5555ff>❙ &fCash out before it <#5555ff>ᴄʀᴀꜱʜᴇꜱ&f!</nl>&f<#5555ff>❙ &fQuick reflexes = Big rewards!")
    String crashDescription();

    // Blackjack
    @ConfComments("Attributes of the blackjack item in the main menu")
    @ConfKey("gui.layout.blackjack.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#55ff55>ʙʟᴀᴄᴋᴊᴀᴄᴋ")
    String blackjackName();

    @ConfKey("gui.layout.blackjack.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("EMERALD")
    Material blackjackItem();

    @ConfKey("gui.layout.blackjack.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&f<#55ff55>❙ &fTest your <#55ff55>ᴄᴀʀᴅ ꜱᴋɪʟʟꜱ &fagainst the dealer!</nl>&f<#55ff55>❙ &fHit 21 or get 5 cards to <#55ff55>ᴡɪɴ &finstantly!</nl>&f<#55ff55>❙ &fBlackjack pays 3:2!")
    String blackjackDescription();

    // Exit

    @ConfComments("Attributes of the exit item in the main menu")
    @ConfKey("gui.layout.exit.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#ff5555>ᴇxɪᴛ")
    String exitName();

    @ConfKey("gui.layout.exit.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("BARRIER")
    Material exitItem();

    @ConfKey("gui.layout.exit.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&f<#ff5555>❙ &fClick to leave the casino")
    String exitDescription();

    @ConfComments("Setup for the mines menu")

    @ConfKey("mines.menu.name")
    @AnnotationBasedSorter.Order(10)
    @ConfDefault.DefaultString("<#ff5555>ᴍɪɴᴇꜱ ᴄʜᴀʟʟᴇɴɢᴇ")
    String minesMenuName();

    @ConfKey("mines.menu.select")
    @AnnotationBasedSorter.Order(11)
    @ConfDefault.DefaultString("&fSelect your difficulty")
    String minesMenuSelect();

    // Tracker

    @ConfKey("mines.menu.tracker.name")
    @AnnotationBasedSorter.Order(12)
    @ConfDefault.DefaultString("<#ffd700>ᴄʟᴀɪᴍ ${amount}")
    String TrackerName();

    @ConfKey("mines.menu.tracker.item")
    @AnnotationBasedSorter.Order(13)
    @ConfDefault.DefaultString("ENDER_CHEST")
    Material TrackerItem();

    @ConfKey("mines.menu.tracker.lore")
    @AnnotationBasedSorter.Order(14)
    @ConfDefault.DefaultString("&f<#ffd700>❙ &fClick to claim your <#ffd700>ʜᴀʀᴅ-ᴇᴀʀɴᴇᴅ ᴘʀɪᴢᴇ&f!</nl>&f<#ffd700>❙ &fCash out now or risk it all?")
    String TrackerLore();

    // Information

    @ConfKey("mines.menu.information.name")
    @AnnotationBasedSorter.Order(12)
    @ConfDefault.DefaultString("<#5555ff>ɢᴀᴍᴇ ɪɴꜰᴏʀᴍᴀᴛɪᴏɴ")
    String InformationName();

    @ConfKey("mines.menu.information.item")
    @AnnotationBasedSorter.Order(13)
    @ConfDefault.DefaultString("KNOWLEDGE_BOOK")
    Material InformationItem();

    @ConfKey("mines.menu.information.lore")
    @AnnotationBasedSorter.Order(14)
    @ConfDefault.DefaultString("&f<#5555ff>❙ &fAvoid the bombs and find <#5555ff>ᴛʀᴇᴀꜱᴜʀᴇꜱ&f!</nl>&f<#5555ff>❙ &fEach safe tile increases your <#5555ff>ᴍᴜʟᴛɪᴘʟɪᴇʀ&f!</nl>&f<#5555ff>❙ &fClaim anytime or push your luck!")
    String InformationLore();

    // Counter

    @ConfKey("mines.menu.counter.bombs.name")
    @AnnotationBasedSorter.Order(12)
    @ConfDefault.DefaultString("<#ff5555>☢ ʙᴏᴍʙꜱ: {amount}")
    String BombCounterName();

    @ConfKey("mines.menu.counter.bombs.item")
    @AnnotationBasedSorter.Order(13)
    @ConfDefault.DefaultString("TNT")
    Material BombCounterItem();

    @ConfKey("mines.menu.counter.bombs.lore")
    @AnnotationBasedSorter.Order(14)
    @ConfDefault.DefaultString("&f<#ff5555>❙ &fWatch out for {amount} hidden <#ff5555>ʙᴏᴍʙꜱ&f!</nl>&f<#ff5555>❙ &fOne wrong move and BOOM!")
    String BombCounterLore();

    // Counter

    @ConfKey("mines.menu.counter.safe.name")
    @AnnotationBasedSorter.Order(12)
    @ConfDefault.DefaultString("<#55ff55>✓ ꜱᴀꜰᴇ ᴛɪʟᴇꜱ: {amount}")
    String SafeCounterName();

    @ConfKey("mines.menu.counter.safe.item")
    @AnnotationBasedSorter.Order(13)
    @ConfDefault.DefaultString("LIME_CONCRETE")
    Material SafeCounterItem();

    @ConfKey("mines.menu.counter.safe.lore")
    @AnnotationBasedSorter.Order(14)
    @ConfDefault.DefaultString("&f<#55ff55>❙ &f{amount} <#55ff55>ꜱᴀꜰᴇ ᴛɪʟᴇꜱ &fremaining!</nl>&f<#55ff55>❙ &fFind them all for maximum reward!")
    String SafeCounterLore();


    // Mines 1

    @ConfKey("mines.menu.bombs.one.name")
    @AnnotationBasedSorter.Order(22)
    @ConfDefault.DefaultString("<#55ff55>1 ʙᴏᴍʙ")
    String minesOneName();

    @ConfKey("mines.menu.bombs.one.multiplier")
    @AnnotationBasedSorter.Order(23)
    @ConfDefault.DefaultDouble(1.01)
    double minesOneMultiplier();

    @ConfKey("mines.menu.bombs.one.item")
    @AnnotationBasedSorter.Order(24)
    @ConfDefault.DefaultString("LIME_CONCRETE")
    Material minesOneItem();

    // Mines 2

    @ConfKey("mines.menu.bombs.two.name")
    @AnnotationBasedSorter.Order(22)
    @ConfDefault.DefaultString("<#ffaa00>2 ʙᴏᴍʙꜱ")
    String minesTwoName();

    @ConfKey("mines.menu.bombs.two.multiplier")
    @AnnotationBasedSorter.Order(23)
    @ConfDefault.DefaultDouble(1.02)
    double minesTwoMultiplier();

    @ConfKey("mines.menu.bombs.two.item")
    @AnnotationBasedSorter.Order(24)
    @ConfDefault.DefaultString("YELLOW_CONCRETE")
    Material minesTwoItem();

    // Mines 3

    @ConfKey("mines.menu.bombs.three.name")
    @AnnotationBasedSorter.Order(22)
    @ConfDefault.DefaultString("<#ff7700>3 ʙᴏᴍʙꜱ")
    String minesThreeName();

    @ConfKey("mines.menu.bombs.three.multiplier")
    @AnnotationBasedSorter.Order(23)
    @ConfDefault.DefaultDouble(1.03)
    double minesThreeMultiplier();

    @ConfKey("mines.menu.bombs.three.item")
    @AnnotationBasedSorter.Order(24)
    @ConfDefault.DefaultString("ORANGE_CONCRETE")
    Material minesThreeItem();

    // Mines 4

    @ConfKey("mines.menu.bombs.four.name")
    @AnnotationBasedSorter.Order(22)
    @ConfDefault.DefaultString("<#ff0000>4 ʙᴏᴍʙꜱ")
    String minesFourName();

    @ConfKey("mines.menu.bombs.four.multiplier")
    @AnnotationBasedSorter.Order(23)
    @ConfDefault.DefaultDouble(1.05)
    double minesFourMultiplier();

    @ConfKey("mines.menu.bombs.four.item")
    @AnnotationBasedSorter.Order(24)
    @ConfDefault.DefaultString("RED_CONCRETE")
    Material minesFourItem();

    // Blackjack

    // Blackjack Menu
    @ConfComments("Setup for the blackjack menu")
    @ConfKey("blackjack.menu.name")
    @AnnotationBasedSorter.Order(30)
    @ConfDefault.DefaultString("<#55ff55>ʙʟᴀᴄᴋᴊᴀᴄᴋ &f• Bet: ${bet}")
    String blackjackMenuName();

    // Player info
    @ConfKey("blackjack.menu.player.name")
    @AnnotationBasedSorter.Order(31)
    @ConfDefault.DefaultString("<#55ff55>ʏᴏᴜʀ ʜᴀɴᴅ: {score}")
    String blackjackPlayerName();

    @ConfKey("blackjack.menu.player.item")
    @AnnotationBasedSorter.Order(32)
    @ConfDefault.DefaultString("PLAYER_HEAD")
    Material blackjackPlayerItem();

    @ConfKey("blackjack.menu.player.lore")
    @AnnotationBasedSorter.Order(33)
    @ConfDefault.DefaultString("&f<#55ff55>❙ &fYour bet: ${bet}</nl>&f<#55ff55>❙ &fClick cards to see details</nl>&f<#55ff55>❙ &fHit 21 or get 5 cards to <#55ff55>ᴡɪɴ &finstantly!")
    String blackjackPlayerLore();

    // Dealer info
    @ConfKey("blackjack.menu.dealer.name")
    @AnnotationBasedSorter.Order(34)
    @ConfDefault.DefaultString("<#ff5555>ᴅᴇᴀʟᴇʀ'ꜱ ʜᴀɴᴅ: {score}")
    String blackjackDealerName();

    @ConfKey("blackjack.menu.dealer.item")
    @AnnotationBasedSorter.Order(35)
    @ConfDefault.DefaultString("PIGLIN_HEAD")
    Material blackjackDealerItem();

    @ConfKey("blackjack.menu.dealer.lore")
    @AnnotationBasedSorter.Order(36)
    @ConfDefault.DefaultString("&f<#ff5555>❙ &fDealer must <#ff5555>ꜱᴛᴀɴᴅ &fon 17+</nl>&f<#ff5555>❙ &fDealer must <#ff5555>ᴅʀᴀᴡ &fon 16 or less")
    String blackjackDealerLore();

    // Card display
    @ConfKey("blackjack.menu.card.name")
    @AnnotationBasedSorter.Order(37)
    @ConfDefault.DefaultString("&f{rank} of <#5555ff>{suit}")
    String blackjackCardName();

    @ConfKey("blackjack.menu.card.lore")
    @AnnotationBasedSorter.Order(38)
    @ConfDefault.DefaultString("&f<#5555ff>❙ &fCard Value: {value}")
    String blackjackCardLore();

    @ConfKey("blackjack.menu.card.facedown.name")
    @AnnotationBasedSorter.Order(39)
    @ConfDefault.DefaultString("&8? ᴍʏꜱᴛᴇʀʏ ᴄᴀʀᴅ ?")
    String blackjackFacedownName();

    @ConfKey("blackjack.menu.card.facedown.lore")
    @AnnotationBasedSorter.Order(40)
    @ConfDefault.DefaultString("&f<#5555ff>❙ &fThe dealer's hidden card</nl>&f<#5555ff>❙ &fWhat could it be?")
    String blackjackFacedownLore();

    // Button texts
    @ConfKey("blackjack.menu.buttons.hit.name")
    @AnnotationBasedSorter.Order(41)
    @ConfDefault.DefaultString("<#55ff55>ʜɪᴛ")
    String blackjackHitName();

    @ConfKey("blackjack.menu.buttons.hit.item")
    @AnnotationBasedSorter.Order(42)
    @ConfDefault.DefaultString("LIME_CANDLE")
    Material blackjackHitItem();

    @ConfKey("blackjack.menu.buttons.hit.lore")
    @AnnotationBasedSorter.Order(43)
    @ConfDefault.DefaultString("&f<#55ff55>❙ &fDraw another <#55ff55>ᴄᴀʀᴅ&f</nl>&f<#55ff55>❙ &fFeeling lucky?")
    String blackjackHitLore();

    @ConfKey("blackjack.menu.buttons.stand.name")
    @AnnotationBasedSorter.Order(44)
    @ConfDefault.DefaultString("<#ff5555>ꜱᴛᴀɴᴅ")
    String blackjackStandName();

    @ConfKey("blackjack.menu.buttons.stand.item")
    @AnnotationBasedSorter.Order(45)
    @ConfDefault.DefaultString("RED_CANDLE")
    Material blackjackStandItem();

    @ConfKey("blackjack.menu.buttons.stand.lore")
    @AnnotationBasedSorter.Order(46)
    @ConfDefault.DefaultString("&f<#ff5555>❙ &fKeep your current <#ff5555>ʜᴀɴᴅ&f</nl>&f<#ff5555>❙ &fLet the dealer play")
    String blackjackStandLore();

    @ConfKey("blackjack.menu.buttons.info.name")
    @AnnotationBasedSorter.Order(47)
    @ConfDefault.DefaultString("<#5555ff>ɢᴀᴍᴇ ʀᴜʟᴇꜱ")
    String blackjackInfoName();

    @ConfKey("blackjack.menu.buttons.info.item")
    @AnnotationBasedSorter.Order(48)
    @ConfDefault.DefaultString("KNOWLEDGE_BOOK")
    Material blackjackInfoItem();

    @ConfKey("blackjack.menu.buttons.info.lore")
    @AnnotationBasedSorter.Order(49)
    @ConfDefault.DefaultString("&f<#5555ff>❙ &fBlackjack pays <#5555ff>3:2&f</nl>&f<#5555ff>❙ &fDealer stands on <#5555ff>17+&f</nl>&f<#5555ff>❙ &fFive Card Charlie wins <#5555ff>ᴀᴜᴛᴏᴍᴀᴛɪᴄᴀʟʟʏ&f!</nl>&f<#5555ff>❙ &fHit 21 to win instantly!")
    String blackjackInfoLore();

    @ConfKey("blackjack.menu.buttons.playagain.name")
    @AnnotationBasedSorter.Order(50)
    @ConfDefault.DefaultString("<#55ff55>ᴘʟᴀʏ ᴀɢᴀɪɴ")
    String blackjackPlayAgainName();

    @ConfKey("blackjack.menu.buttons.playagain.item")
    @AnnotationBasedSorter.Order(51)
    @ConfDefault.DefaultString("EMERALD_BLOCK")
    Material blackjackPlayAgainItem();

    @ConfKey("blackjack.menu.buttons.playagain.lore")
    @AnnotationBasedSorter.Order(52)
    @ConfDefault.DefaultString("&f<#55ff55>❙ &fStart a <#55ff55>ɴᴇᴡ ʀᴏᴜɴᴅ&f</nl>&f<#55ff55>❙ &fSame bet amount")
    String blackjackPlayAgainLore();

    // Results
    @ConfKey("blackjack.menu.results.blackjack.name")
    @AnnotationBasedSorter.Order(53)
    @ConfDefault.DefaultString("<#ffd700>ʙʟᴀᴄᴋᴊᴀᴄᴋ!")
    String blackjackResultBlackjackName();

    @ConfKey("blackjack.menu.results.blackjack.item")
    @AnnotationBasedSorter.Order(54)
    @ConfDefault.DefaultString("DIAMOND")
    Material blackjackResultBlackjackItem();

    @ConfKey("blackjack.menu.results.blackjack.lore")
    @AnnotationBasedSorter.Order(55)
    @ConfDefault.DefaultString("&f<#ffd700>❙ &fPerfect 21! You win <#ffd700>${amount}&f</nl>&f<#ffd700>❙ &fBlackjack pays 3:2!")
    String blackjackResultBlackjackLore();

    @ConfKey("blackjack.menu.results.player-win.name")
    @AnnotationBasedSorter.Order(56)
    @ConfDefault.DefaultString("<#55ff55>ʏᴏᴜ ᴡɪɴ!")
    String blackjackResultPlayerWinName();

    @ConfKey("blackjack.menu.results.player-win.item")
    @AnnotationBasedSorter.Order(57)
    @ConfDefault.DefaultString("EMERALD")
    Material blackjackResultPlayerWinItem();

    @ConfKey("blackjack.menu.results.player-win.lore")
    @AnnotationBasedSorter.Order(58)
    @ConfDefault.DefaultString("&f<#55ff55>❙ &fCongratulations! You win <#55ff55>${amount}&f</nl>&f<#55ff55>❙ &fBetter hand than the dealer!")
    String blackjackResultPlayerWinLore();

    @ConfKey("blackjack.menu.results.dealer-bust.name")
    @AnnotationBasedSorter.Order(59)
    @ConfDefault.DefaultString("<#55ff55>ᴅᴇᴀʟᴇʀ ʙᴜꜱᴛ!")
    String blackjackResultDealerBustName();

    @ConfKey("blackjack.menu.results.dealer-bust.item")
    @AnnotationBasedSorter.Order(60)
    @ConfDefault.DefaultString("EMERALD")
    Material blackjackResultDealerBustItem();

    @ConfKey("blackjack.menu.results.dealer-bust.lore")
    @AnnotationBasedSorter.Order(61)
    @ConfDefault.DefaultString("&f<#55ff55>❙ &fDealer went over 21! You win <#55ff55>${amount}&f</nl>&f<#55ff55>❙ &fThe house loses this time!")
    String blackjackResultDealerBustLore();

    @ConfKey("blackjack.menu.results.push.name")
    @AnnotationBasedSorter.Order(62)
    @ConfDefault.DefaultString("<#ffaa00>ᴘᴜꜱʜ!")
    String blackjackResultPushName();

    @ConfKey("blackjack.menu.results.push.item")
    @AnnotationBasedSorter.Order(63)
    @ConfDefault.DefaultString("GOLD_INGOT")
    Material blackjackResultPushItem();

    @ConfKey("blackjack.menu.results.push.lore")
    @AnnotationBasedSorter.Order(64)
    @ConfDefault.DefaultString("&f<#ffaa00>❙ &fIt's a tie! Your bet of <#ffaa00>${amount}&f</nl>&f<#ffaa00>❙ &fhas been returned to you")
    String blackjackResultPushLore();

    @ConfKey("blackjack.menu.results.player-bust.name")
    @AnnotationBasedSorter.Order(65)
    @ConfDefault.DefaultString("<#ff5555>ʙᴜꜱᴛ!")
    String blackjackResultPlayerBustName();

    @ConfKey("blackjack.menu.results.player-bust.item")
    @AnnotationBasedSorter.Order(66)
    @ConfDefault.DefaultString("REDSTONE_BLOCK")
    Material blackjackResultPlayerBustItem();

    @ConfKey("blackjack.menu.results.player-bust.lore")
    @AnnotationBasedSorter.Order(67)
    @ConfDefault.DefaultString("&f<#ff5555>❙ &fYou went over 21! You lost <#ff5555>${amount}&f</nl>&f<#ff5555>❙ &fBetter luck next time!")
    String blackjackResultPlayerBustLore();

    @ConfKey("blackjack.menu.results.dealer-win.name")
    @AnnotationBasedSorter.Order(68)
    @ConfDefault.DefaultString("<#ff5555>ᴅᴇᴀʟᴇʀ ᴡɪɴꜱ!")
    String blackjackResultDealerWinName();

    @ConfKey("blackjack.menu.results.dealer-win.item")
    @AnnotationBasedSorter.Order(69)
    @ConfDefault.DefaultString("REDSTONE_BLOCK")
    Material blackjackResultDealerWinItem();

    @ConfKey("blackjack.menu.results.dealer-win.lore")
    @AnnotationBasedSorter.Order(70)
    @ConfDefault.DefaultString("&f<#ff5555>❙ &fDealer has a better hand! You lost <#ff5555>${amount}&f</nl>&f<#ff5555>❙ &fThe house always wins... eventually!")
    String blackjackResultDealerWinLore();

    // Crash Game Settings
    @ConfComments("Core settings for the crash game")
    @ConfKey("crash.settings.betPeriodSeconds")
    @AnnotationBasedSorter.Order(80)
    @ConfDefault.DefaultInteger(15)
    int crashBetPeriodSeconds();

    @ConfKey("crash.settings.minimumPlayers")
    @AnnotationBasedSorter.Order(81)
    @ConfDefault.DefaultInteger(1)
    int crashMinimumPlayers();

    @ConfKey("crash.settings.minBetAmount")
    @AnnotationBasedSorter.Order(82)
    @ConfDefault.DefaultDouble(10.0)
    double crashMinBetAmount();

    // Crash Menu
    @ConfKey("crash.menu.title")
    @AnnotationBasedSorter.Order(83)
    @ConfDefault.DefaultString("<#5555ff>ᴄʀᴀꜱʜ ɢᴀᴍᴇ")
    String crashTitle();

    // Materials
    @ConfKey("crash.items.blankObject")
    @AnnotationBasedSorter.Order(84)
    @ConfDefault.DefaultString("BLACK_STAINED_GLASS_PANE")
    Material crashBlankObject();

    @ConfKey("crash.items.infoItem")
    @AnnotationBasedSorter.Order(85)
    @ConfDefault.DefaultString("COMPASS")
    Material crashInfoItem();

    @ConfKey("crash.items.runningItem")
    @AnnotationBasedSorter.Order(86)
    @ConfDefault.DefaultString("EMERALD")
    Material crashRunningItem();

    @ConfKey("crash.items.crashedItem")
    @AnnotationBasedSorter.Order(87)
    @ConfDefault.DefaultString("REDSTONE")
    Material crashCrashedItem();

    @ConfKey("crash.items.betItem")
    @AnnotationBasedSorter.Order(88)
    @ConfDefault.DefaultString("GOLD_INGOT")
    Material crashBetItem();

    @ConfKey("crash.items.betDisabledItem")
    @AnnotationBasedSorter.Order(89)
    @ConfDefault.DefaultString("IRON_INGOT")
    Material crashBetDisabledItem();

    @ConfKey("crash.items.cashoutItem")
    @AnnotationBasedSorter.Order(90)
    @ConfDefault.DefaultString("DIAMOND")
    Material crashCashoutItem();

    @ConfKey("crash.items.cashoutDisabledItem")
    @AnnotationBasedSorter.Order(91)
    @ConfDefault.DefaultString("COAL")
    Material crashCashoutDisabledItem();

    @ConfKey("crash.items.playerInfoItem")
    @AnnotationBasedSorter.Order(92)
    @ConfDefault.DefaultString("PLAYER_HEAD")
    Material playerInfoItem();

    // Titles
    @ConfKey("crash.titles.statusTitle")
    @AnnotationBasedSorter.Order(93)
    @ConfDefault.DefaultString("<#6666ff>ɢᴀᴍᴇ ꜱᴛᴀᴛᴜꜱ")
    String crashStatusTitle();

    @ConfKey("crash.titles.countdownTitle")
    @AnnotationBasedSorter.Order(94)
    @ConfDefault.DefaultString("<#ffaa00>ꜱᴛᴀʀᴛɪɴɢ ɪɴ...")
    String crashCountdownTitle();

    @ConfKey("crash.titles.gameStartTitle")
    @AnnotationBasedSorter.Order(95)
    @ConfDefault.DefaultString("<#55ff55>ɢᴀᴍᴇ ꜱᴛᴀʀᴛᴇᴅ!")
    String crashGameStartTitle();

    @ConfKey("crash.titles.multiplierTitle")
    @AnnotationBasedSorter.Order(96)
    @ConfDefault.DefaultString("<#55ff55>ᴄᴜʀʀᴇɴᴛ ᴍᴜʟᴛɪᴘʟɪᴇʀ")
    String crashMultiplierTitle();

    @ConfKey("crash.titles.gameEndTitle")
    @AnnotationBasedSorter.Order(97)
    @ConfDefault.DefaultString("<#ff5555>ᴄʀᴀꜱʜᴇᴅ!")
    String crashGameEndTitle();

    @ConfKey("crash.titles.betTitle")
    @AnnotationBasedSorter.Order(98)
    @ConfDefault.DefaultString("<#ffaa00>ᴘʟᴀᴄᴇ ʙᴇᴛ")
    String crashBetTitle();

    @ConfKey("crash.titles.betDisabledTitle")
    @AnnotationBasedSorter.Order(99)
    @ConfDefault.DefaultString("<#888888>ʙᴇᴛᴛɪɴɢ ᴄʟᴏꜱᴇᴅ")
    String crashBetDisabledTitle();

    @ConfKey("crash.titles.cashoutTitle")
    @AnnotationBasedSorter.Order(100)
    @ConfDefault.DefaultString("<#55ff55>ᴄᴀꜱʜ ᴏᴜᴛ")
    String crashCashoutTitle();

    @ConfKey("crash.titles.cashoutDisabledTitle")
    @AnnotationBasedSorter.Order(101)
    @ConfDefault.DefaultString("<#888888>ᴄᴀꜱʜ ᴏᴜᴛ")
    String crashCashoutDisabledTitle();

    @ConfKey("crash.titles.playerInfoTitle")
    @AnnotationBasedSorter.Order(102)
    @ConfDefault.DefaultString("<#5555ff>%player%'ꜱ ɪɴꜰᴏ")
    String playerInfoTitle();

    // Descriptions
    @ConfKey("crash.descriptions.statusDescription")
    @AnnotationBasedSorter.Order(103)
    @ConfDefault.DefaultString("&f<#5555ff>❙ &fCurrent status: <#5555ff>%status%</nl>&f<#5555ff>❙ &fWaiting for players...")
    String crashStatusDescription();

    @ConfKey("crash.descriptions.countdownDescription")
    @AnnotationBasedSorter.Order(104)
    @ConfDefault.DefaultString("&f<#ffaa00>❙ &fGame starts in: <#ffaa00>%seconds%s</nl>&f<#ffaa00>❙ &fPlace your bets now!")
    String crashCountdownDescription();

    @ConfKey("crash.descriptions.gameStartDescription")
    @AnnotationBasedSorter.Order(105)
    @ConfDefault.DefaultString("&f<#55ff55>❙ &fGame is running!</nl>&f<#55ff55>❙ &fCurrent multiplier: <#55ff55>%multiplier%")
    String crashGameStartDescription();

    @ConfKey("crash.descriptions.multiplierDescription")
    @AnnotationBasedSorter.Order(106)
    @ConfDefault.DefaultString("&f<#55ff55>❙ &fCurrent multiplier: <#55ff55>%multiplier%</nl>&f<#55ff55>❙ &fCash out before it crashes!")
    String crashMultiplierDescription();

    @ConfKey("crash.descriptions.gameEndDescription")
    @AnnotationBasedSorter.Order(107)
    @ConfDefault.DefaultString("&f<#ff5555>❙ &fGame crashed at: <#ff5555>%multiplier%</nl>&f<#ff5555>❙ &fBetter luck next time!")
    String crashGameEndDescription();

    @ConfKey("crash.descriptions.betDescription")
    @AnnotationBasedSorter.Order(108)
    @ConfDefault.DefaultString("&f<#ffaa00>❙ &fClick to place your bet</nl>&f<#ffaa00>❙ &fMinimum bet: <#ffaa00>$10")
    String crashBetDescription();

    @ConfKey("crash.descriptions.betDisabledDescription")
    @AnnotationBasedSorter.Order(109)
    @ConfDefault.DefaultString("&f<#888888>❙ &fBetting is closed</nl>&f<#888888>❙ &fWait for next round")
    String crashBetDisabledDescription();

    @ConfKey("crash.descriptions.cashoutDescription")
    @AnnotationBasedSorter.Order(110)
    @ConfDefault.DefaultString("&f<#55ff55>❙ &fCurrent multiplier: <#55ff55>%multiplier%</nl>&f<#55ff55>❙ &fPotential payout: <#55ff55>$%amount%</nl>&f<#55ff55>❙ &fClick to cash out!")
    String crashCashoutDescription();

    @ConfKey("crash.descriptions.cashoutDisabledDescription")
    @AnnotationBasedSorter.Order(111)
    @ConfDefault.DefaultString("&f<#888888>❙ &fYou cannot cash out</nl>&f<#888888>❙ &fNo active bet or already cashed out")
    String crashCashoutDisabledDescription();

    @ConfKey("crash.descriptions.playerInfoDescription")
    @AnnotationBasedSorter.Order(112)
    @ConfDefault.DefaultString("&f<#5555ff>❙ &fBalance: <#5555ff>$%balance%</nl>&f<#5555ff>❙ &fCurrent bet: <#5555ff>$%bet%")
    String playerInfoDescription();

    // Messages
    @ConfKey("crash.messages.betPrompt")
    @AnnotationBasedSorter.Order(113)
    @ConfDefault.DefaultString("&f<#ffaa00>❙ &fEnter your bet amount in chat:")
    String crashBetPrompt();

    @ConfKey("crash.messages.minBetMessage")
    @AnnotationBasedSorter.Order(114)
    @ConfDefault.DefaultString("&f<#ff5555>❙ &fMinimum bet is <#ff5555>$%min%!")
    String minBetMessage();

    @ConfKey("crash.messages.betPlacedMessage")
    @AnnotationBasedSorter.Order(115)
    @ConfDefault.DefaultString("&f<#55ff55>❙ &fBet of <#55ff55>$%amount% &fplaced successfully!")
    String crashBetPlacedMessage();

    @ConfKey("crash.messages.betFailedMessage")
    @AnnotationBasedSorter.Order(116)
    @ConfDefault.DefaultString("&f<#ff5555>❙ &fFailed to place bet. Game might have already started.")
    String crashBetFailedMessage();

    @ConfKey("crash.messages.cashoutMessage")
    @AnnotationBasedSorter.Order(117)
    @ConfDefault.DefaultString("&f<#55ff55>❙ &fYou cashed out <#55ff55>$%amount% &fat <#55ff55>%multiplier%!")
    String crashCashoutMessage();

    // Slots messages

    @ConfComments("Configuration for Slots game")
    @ConfKey("slots.title")
    @AnnotationBasedSorter.Order(200)
    @ConfDefault.DefaultString("&6&lSlot Machine")
    String slotsTitle();

    @ConfKey("slots.instructions.title")
    @AnnotationBasedSorter.Order(201)
    @ConfDefault.DefaultString("&e&lHow To Play")
    String slotsInstructionsTitle();

    @ConfKey("slots.instructions.lore")
    @AnnotationBasedSorter.Order(202)
    @ConfDefault.DefaultString("&f• The middle row is the winning line</nl>&f• Match items for higher multipliers</nl>&f• Different items have different values")
    String slotsInstructionsLore();

    @ConfKey("slots.results.win.title")
    @AnnotationBasedSorter.Order(203)
    @ConfDefault.DefaultString("&a&lYou Won!")
    String slotsWinTitle();

    @ConfKey("slots.results.lose.title")
    @AnnotationBasedSorter.Order(204)
    @ConfDefault.DefaultString("&c&lYou Lost!")
    String slotsLoseTitle();

    @ConfKey("slots.results.win.amount")
    @AnnotationBasedSorter.Order(205)
    @ConfDefault.DefaultString("&a&l+${amount}")
    String slotsWinAmount();

    @ConfKey("slots.results.lose.amount")
    @AnnotationBasedSorter.Order(206)
    @ConfDefault.DefaultString("&c&l-${amount}")
    String slotsLoseAmount();

    @ConfKey("slots.results.win.lore")
    @AnnotationBasedSorter.Order(207)
    @ConfDefault.DefaultString("&7You bet: &a&n${bet}</nl>&7Final amount: &a&n${final}</nl></nl>&aWell Done!")
    String slotsWinLore();

    @ConfKey("slots.results.lose.lore")
    @AnnotationBasedSorter.Order(208)
    @ConfDefault.DefaultString("&7You bet: &a&n${bet}</nl>&7Final amount: &a&n${final}</nl></nl>&cBetter luck next time!")
    String slotsLoseLore();

    @ConfKey("slots.results.multiplier.title")
    @AnnotationBasedSorter.Order(209)
    @ConfDefault.DefaultString("&e&lTotal Multiplier")
    String slotsTotalMultiplierTitle();

    @ConfKey("slots.results.multiplier.value")
    @AnnotationBasedSorter.Order(210)
    @ConfDefault.DefaultString("&fx{multiplier}")
    String slotsTotalMultiplierValue();

    @ConfKey("slots.buttons.play-again")
    @AnnotationBasedSorter.Order(211)
    @ConfDefault.DefaultString("&a&lPLAY AGAIN")
    String slotsPlayAgainTitle();

    @ConfKey("slots.buttons.exit")
    @AnnotationBasedSorter.Order(212)
    @ConfDefault.DefaultString("&c&lEXIT")
    String slotsExitTitle();

    @ConfKey("slots.buttons.spin")
    @AnnotationBasedSorter.Order(213)
    @ConfDefault.DefaultString("&a&lSPIN")
    String slotsSpinTitle();

    @ConfKey("slots.buttons.spin.lore")
    @AnnotationBasedSorter.Order(214)
    @ConfDefault.DefaultString("</nl>&8&l| &aClick here to pay for a spin</nl>&8&l| &aYou are betting &a&n$0&a.")
    String slotsSpinLore();

    @ConfKey("slots.messages.bet-cancel")
    @AnnotationBasedSorter.Order(215)
    @ConfDefault.DefaultString("&cBetting cancelled.")
    String slotsBetCancelMessage();

    @ConfKey("slots.messages.invalid-bet")
    @AnnotationBasedSorter.Order(216)
    @ConfDefault.DefaultString("&cInvalid amount. Please enter a number.")
    String slotsInvalidBetMessage();

    @ConfKey("slots.messages.bet-confirm")
    @AnnotationBasedSorter.Order(217)
    @ConfDefault.DefaultString("&aYou have bet ${amount}.")
    String slotsBetConfirmMessage();

    @ConfKey("slots.messages.min-bet")
    @AnnotationBasedSorter.Order(218)
    @ConfDefault.DefaultString("&cMinimum bet is ${min}!")
    String slotsMinBetMessage();

    // Command Messages
    @ConfComments("Messages related to the /casino command")
    @ConfKey("messages.command.permission-denied")
    @AnnotationBasedSorter.Order(300)
    @ConfDefault.DefaultString("&c&lPermission Denied! &fYou don't have permission to use this command.")
    String permissionDeniedMessage();

    @ConfKey("messages.command.invalid-value")
    @AnnotationBasedSorter.Order(301)
    @ConfDefault.DefaultString("&c&lInvalid Value! </nl>&fThe amount you entered is invalid. Valid ex: 1, 10k, 100m")
    String invalidValueMessage();

    @ConfKey("messages.command.reload-time")
    @AnnotationBasedSorter.Order(303)
    @ConfDefault.DefaultString("&a&lReloaded in &f{ms}ms&a!")
    String reloadTimeMessage();

    @ConfKey("messages.command.help")
    @AnnotationBasedSorter.Order(303)
    @ConfDefault.DefaultString("&6&lFancy Casinos Commands&f:</nl>&f/casino - &7Open the main casino menu</nl>&f/casino reload - &7Reload the plugin configuration</nl>&f/casino help - &7Display this help message")
    String helpMessage();

    @ConfKey("messages.general.bet-timeout")
    @AnnotationBasedSorter.Order(304)
    @ConfDefault.DefaultString("&c&lTimed Out! </nl>&fYour casino session timed out.")
    String betTimeoutMessage();

    @ConfKey("messages.general.insufficient-funds")
    @AnnotationBasedSorter.Order(305)
    @ConfDefault.DefaultString("&c&lInsufficient Funds! </nl>&fYou don't have enough money to place this bet.")
    String insufficientFundsMessage();


}