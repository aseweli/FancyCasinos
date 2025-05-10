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
    @ConfDefault.DefaultString("<#fcba03>✦ <#fcd303>Fancy <#fcee03>Casino <#fcba03>✦")
    String guiName();

    @ConfComments({"The layout of your ui written in this file is how it will show in the menu", "With the \"-\" being your blank object. The \"s\" is for slots, \"c\" for crash", "\"m\" for mines, \"b\" for black jack and, \"x\" for exit."
            , " ", "Ensure that it is formatted correctly, with 9 characters per line and, between 1 and 6 lines."})
    @ConfKey("gui.layout.ui")
    @AnnotationBasedSorter.Order(1)
    @ConfDefault.DefaultString("---------\n-s-c-m-b-\n---------")
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
            "v:KNOWLEDGE_BOOK:<#fcba03>✦ <#fcd303>Instructions <#fcba03>✦:&7» Click to see how to play</nl>&7» Have fun and gamble responsibly!"
    })
    List<String> customItemDefinitions();

    // Slots

    @ConfComments("Attributes of the slots item in the main menu")
    @ConfKey("gui.layout.slots.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#ffd700>✧ <#ffe84d>SLOTS <#ffd700>✧")
    String slotsName();

    @ConfKey("gui.layout.slots.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("GOLD_BLOCK")
    Material slotsItem();

    @ConfKey("gui.layout.slots.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&7» <#ffd700>Test your luck with our slot machines!</nl>&7» <#ffe84d>Match symbols to win big prizes!</nl>&7» <#ffd700>Current Jackpot: &f✦ MASSIVE ✦")
    String slotsDescription();

    // Mines

    @ConfComments("Attributes of the mines item in the main menu")
    @ConfKey("gui.layout.mines.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#ff5555>✦ <#ff7777>MINES <#ff5555>✦")
    String minesName();

    @ConfKey("gui.layout.mines.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("TNT")
    Material minesItem();

    @ConfKey("gui.layout.mines.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&7» <#ff5555>Navigate through a field of explosives!</nl>&7» <#ff7777>Every safe step increases your reward!</nl>&7» <#ff5555>How far will you go?")
    String minesDescription();

    // Crash

    @ConfComments("Attributes of the crash item in the main menu")
    @ConfKey("gui.layout.crash.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#5555ff>⚡ <#7777ff>CRASH <#5555ff>⚡")
    String crashName();

    @ConfKey("gui.layout.crash.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("DRAGON_BREATH")
    Material crashItem();

    @ConfKey("gui.layout.crash.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&7» <#5555ff>Watch the multiplier rise!</nl>&7» <#7777ff>Cash out before it crashes!</nl>&7» <#5555ff>Quick reflexes = Big rewards!")
    String crashDescription();

    // Blackjack
    @ConfComments("Attributes of the blackjack item in the main menu")
    @ConfKey("gui.layout.blackjack.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#55ff55>♠ <#77ff77>BLACKJACK <#55ff55>♠")
    String blackjackName();

    @ConfKey("gui.layout.blackjack.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("EMERALD")
    Material blackjackItem();

    @ConfKey("gui.layout.blackjack.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&7» <#55ff55>Test your card skills against the dealer!</nl>&7» <#77ff77>Hit 21 or get 5 cards to win instantly!</nl>&7» <#55ff55>Blackjack pays 3:2!")
    String blackjackDescription();

    // Exit

    @ConfComments("Attributes of the exit item in the main menu")
    @ConfKey("gui.layout.exit.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#ff5555>✘ <#ff7777>EXIT <#ff5555>✘")
    String exitName();

    @ConfKey("gui.layout.exit.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("BARRIER")
    Material exitItem();

    @ConfKey("gui.layout.exit.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&7» Click to leave the casino")
    String exitDescription();

    @ConfComments("Setup for the mines menu")

    @ConfKey("mines.menu.name")
    @AnnotationBasedSorter.Order(10)
    @ConfDefault.DefaultString("<#ff5555>✦ <#ff7777>Mines Challenge <#ff5555>✦")
    String minesMenuName();

    @ConfKey("mines.menu.select")
    @AnnotationBasedSorter.Order(11)
    @ConfDefault.DefaultString("<#ff7777>Select your difficulty")
    String minesMenuSelect();

    // Tracker

    @ConfKey("mines.menu.tracker.name")
    @AnnotationBasedSorter.Order(12)
    @ConfDefault.DefaultString("<#ffd700>✧ <#ffe84d>Claim ${amount} <#ffd700>✧")
    String TrackerName();

    @ConfKey("mines.menu.tracker.item")
    @AnnotationBasedSorter.Order(13)
    @ConfDefault.DefaultString("ENDER_CHEST")
    Material TrackerItem();

    @ConfKey("mines.menu.tracker.lore")
    @AnnotationBasedSorter.Order(14)
    @ConfDefault.DefaultString("&7» <#ffd700>Click to claim your hard-earned prize!</nl>&7» <#ffe84d>Cash out now or risk it all?")
    String TrackerLore();

    // Information

    @ConfKey("mines.menu.information.name")
    @AnnotationBasedSorter.Order(12)
    @ConfDefault.DefaultString("<#5555ff>ℹ <#7777ff>Game Information <#5555ff>ℹ")
    String InformationName();

    @ConfKey("mines.menu.information.item")
    @AnnotationBasedSorter.Order(13)
    @ConfDefault.DefaultString("KNOWLEDGE_BOOK")
    Material InformationItem();

    @ConfKey("mines.menu.information.lore")
    @AnnotationBasedSorter.Order(14)
    @ConfDefault.DefaultString("&7» <#5555ff>Avoid the bombs and find treasures!</nl>&7» <#7777ff>Each safe tile increases your multiplier!</nl>&7» <#5555ff>Claim anytime or push your luck!")
    String InformationLore();

    // Counter

    @ConfKey("mines.menu.counter.bombs.name")
    @AnnotationBasedSorter.Order(12)
    @ConfDefault.DefaultString("<#ff5555>⚠ <#ff7777>Bombs: {amount} <#ff5555>⚠")
    String BombCounterName();

    @ConfKey("mines.menu.counter.bombs.item")
    @AnnotationBasedSorter.Order(13)
    @ConfDefault.DefaultString("TNT")
    Material BombCounterItem();

    @ConfKey("mines.menu.counter.bombs.lore")
    @AnnotationBasedSorter.Order(14)
    @ConfDefault.DefaultString("&7» <#ff5555>Watch out for {amount} hidden bombs!</nl>&7» <#ff7777>One wrong move and BOOM!")
    String BombCounterLore();

    // Counter

    @ConfKey("mines.menu.counter.safe.name")
    @AnnotationBasedSorter.Order(12)
    @ConfDefault.DefaultString("<#55ff55>✓ <#77ff77>Safe Tiles: {amount} <#55ff55>✓")
    String SafeCounterName();

    @ConfKey("mines.menu.counter.safe.item")
    @AnnotationBasedSorter.Order(13)
    @ConfDefault.DefaultString("LIME_CONCRETE")
    Material SafeCounterItem();

    @ConfKey("mines.menu.counter.safe.lore")
    @AnnotationBasedSorter.Order(14)
    @ConfDefault.DefaultString("&7» <#55ff55>{amount} safe tiles remaining!</nl>&7» <#77ff77>Find them all for maximum reward!")
    String SafeCounterLore();


    // Mines 1

    @ConfKey("mines.menu.bombs.one.name")
    @AnnotationBasedSorter.Order(22)
    @ConfDefault.DefaultString("<#55ff55>✧ <#77ff77>1 Bomb <#55ff55>✧")
    String minesOneName();

    @ConfKey("mines.menu.bombs.one.multiplier")
    @AnnotationBasedSorter.Order(23)
    @ConfDefault.DefaultDouble(1.05)
    double minesOneMultiplier();

    @ConfKey("mines.menu.bombs.one.item")
    @AnnotationBasedSorter.Order(24)
    @ConfDefault.DefaultString("LIME_CONCRETE")
    Material minesOneItem();

    // Mines 2

    @ConfKey("mines.menu.bombs.two.name")
    @AnnotationBasedSorter.Order(22)
    @ConfDefault.DefaultString("<#ffaa00>✧ <#ffcc00>2 Bombs <#ffaa00>✧")
    String minesTwoName();

    @ConfKey("mines.menu.bombs.two.multiplier")
    @AnnotationBasedSorter.Order(23)
    @ConfDefault.DefaultDouble(1.007)
    double minesTwoMultiplier();

    @ConfKey("mines.menu.bombs.two.item")
    @AnnotationBasedSorter.Order(24)
    @ConfDefault.DefaultString("YELLOW_CONCRETE")
    Material minesTwoItem();

    // Mines 3

    @ConfKey("mines.menu.bombs.three.name")
    @AnnotationBasedSorter.Order(22)
    @ConfDefault.DefaultString("<#ff7700>✧ <#ff9900>3 Bombs <#ff7700>✧")
    String minesThreeName();

    @ConfKey("mines.menu.bombs.three.multiplier")
    @AnnotationBasedSorter.Order(23)
    @ConfDefault.DefaultDouble(1.01)
    double minesThreeMultiplier();

    @ConfKey("mines.menu.bombs.three.item")
    @AnnotationBasedSorter.Order(24)
    @ConfDefault.DefaultString("ORANGE_CONCRETE")
    Material minesThreeItem();

    // Mines 4

    @ConfKey("mines.menu.bombs.four.name")
    @AnnotationBasedSorter.Order(22)
    @ConfDefault.DefaultString("<#ff0000>✧ <#ff5555>4 Bombs <#ff0000>✧")
    String minesFourName();

    @ConfKey("mines.menu.bombs.four.multiplier")
    @AnnotationBasedSorter.Order(23)
    @ConfDefault.DefaultDouble(1.013)
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
    @ConfDefault.DefaultString("<#55ff55>♠ <#77ff77>Blackjack <#55ff55>♠ <#ffffff>Bet: ${bet}")
    String blackjackMenuName();

    // Player info
    @ConfKey("blackjack.menu.player.name")
    @AnnotationBasedSorter.Order(31)
    @ConfDefault.DefaultString("<#55ff55>♣ <#77ff77>Your Hand: {score} <#55ff55>♣")
    String blackjackPlayerName();

    @ConfKey("blackjack.menu.player.item")
    @AnnotationBasedSorter.Order(32)
    @ConfDefault.DefaultString("PLAYER_HEAD")
    Material blackjackPlayerItem();

    @ConfKey("blackjack.menu.player.lore")
    @AnnotationBasedSorter.Order(33)
    @ConfDefault.DefaultString("&7» <#ffd700>Your bet: ${bet}</nl>&7» <#ffffff>Click cards to see details</nl>&7» <#77ff77>Hit 21 or get 5 cards to win instantly!")
    String blackjackPlayerLore();

    // Dealer info
    @ConfKey("blackjack.menu.dealer.name")
    @AnnotationBasedSorter.Order(34)
    @ConfDefault.DefaultString("<#ff5555>♦ <#ff7777>Dealer's Hand: {score} <#ff5555>♦")
    String blackjackDealerName();

    @ConfKey("blackjack.menu.dealer.item")
    @AnnotationBasedSorter.Order(35)
    @ConfDefault.DefaultString("PIGLIN_HEAD")
    Material blackjackDealerItem();

    @ConfKey("blackjack.menu.dealer.lore")
    @AnnotationBasedSorter.Order(36)
    @ConfDefault.DefaultString("&7» <#ff7777>Dealer must stand on 17+</nl>&7» <#ff5555>Dealer must draw on 16 or less")
    String blackjackDealerLore();

    // Card display
    @ConfKey("blackjack.menu.card.name")
    @AnnotationBasedSorter.Order(37)
    @ConfDefault.DefaultString("<#ffffff>{rank} of <#55aaff>{suit}")
    String blackjackCardName();

    @ConfKey("blackjack.menu.card.lore")
    @AnnotationBasedSorter.Order(38)
    @ConfDefault.DefaultString("&7» Card Value: <#ffffff>{value}")
    String blackjackCardLore();

    @ConfKey("blackjack.menu.card.facedown.name")
    @AnnotationBasedSorter.Order(39)
    @ConfDefault.DefaultString("<#888888>? Mystery Card ?")
    String blackjackFacedownName();

    @ConfKey("blackjack.menu.card.facedown.lore")
    @AnnotationBasedSorter.Order(40)
    @ConfDefault.DefaultString("&7» <#888888>The dealer's hidden card</nl>&7» <#aaaaaa>What could it be?")
    String blackjackFacedownLore();

    // Button texts
    @ConfKey("blackjack.menu.buttons.hit.name")
    @AnnotationBasedSorter.Order(41)
    @ConfDefault.DefaultString("<#55ff55>✦ <#77ff77>HIT <#55ff55>✦")
    String blackjackHitName();

    @ConfKey("blackjack.menu.buttons.hit.item")
    @AnnotationBasedSorter.Order(42)
    @ConfDefault.DefaultString("LIME_CONCRETE")
    Material blackjackHitItem();

    @ConfKey("blackjack.menu.buttons.hit.lore")
    @AnnotationBasedSorter.Order(43)
    @ConfDefault.DefaultString("&7» <#55ff55>Draw another card</nl>&7» <#77ff77>Feeling lucky?")
    String blackjackHitLore();

    @ConfKey("blackjack.menu.buttons.stand.name")
    @AnnotationBasedSorter.Order(44)
    @ConfDefault.DefaultString("<#ff5555>✦ <#ff7777>STAND <#ff5555>✦")
    String blackjackStandName();

    @ConfKey("blackjack.menu.buttons.stand.item")
    @AnnotationBasedSorter.Order(45)
    @ConfDefault.DefaultString("RED_CONCRETE")
    Material blackjackStandItem();

    @ConfKey("blackjack.menu.buttons.stand.lore")
    @AnnotationBasedSorter.Order(46)
    @ConfDefault.DefaultString("&7» <#ff5555>Keep your current hand</nl>&7» <#ff7777>Let the dealer play")
    String blackjackStandLore();

    @ConfKey("blackjack.menu.buttons.info.name")
    @AnnotationBasedSorter.Order(47)
    @ConfDefault.DefaultString("<#5555ff>ℹ <#7777ff>GAME RULES <#5555ff>ℹ")
    String blackjackInfoName();

    @ConfKey("blackjack.menu.buttons.info.item")
    @AnnotationBasedSorter.Order(48)
    @ConfDefault.DefaultString("KNOWLEDGE_BOOK")
    Material blackjackInfoItem();

    @ConfKey("blackjack.menu.buttons.info.lore")
    @AnnotationBasedSorter.Order(49)
    @ConfDefault.DefaultString("&7» <#5555ff>Blackjack pays 3:2</nl>&7» <#7777ff>Dealer stands on 17+</nl>&7» <#5555ff>Five Card Charlie wins automatically!</nl>&7» <#7777ff>Hit 21 to win instantly!")
    String blackjackInfoLore();

    @ConfKey("blackjack.menu.buttons.playagain.name")
    @AnnotationBasedSorter.Order(50)
    @ConfDefault.DefaultString("<#55ff55>⟳ <#77ff77>PLAY AGAIN <#55ff55>⟳")
    String blackjackPlayAgainName();

    @ConfKey("blackjack.menu.buttons.playagain.item")
    @AnnotationBasedSorter.Order(51)
    @ConfDefault.DefaultString("EMERALD_BLOCK")
    Material blackjackPlayAgainItem();

    @ConfKey("blackjack.menu.buttons.playagain.lore")
    @AnnotationBasedSorter.Order(52)
    @ConfDefault.DefaultString("&7» <#55ff55>Start a new round</nl>&7» <#77ff77>Same bet amount")
    String blackjackPlayAgainLore();

    // Results
    @ConfKey("blackjack.menu.results.blackjack.name")
    @AnnotationBasedSorter.Order(53)
    @ConfDefault.DefaultString("<#ffd700>✦ <#ffe84d>BLACKJACK! <#ffd700>✦")
    String blackjackResultBlackjackName();

    @ConfKey("blackjack.menu.results.blackjack.item")
    @AnnotationBasedSorter.Order(54)
    @ConfDefault.DefaultString("DIAMOND")
    Material blackjackResultBlackjackItem();

    @ConfKey("blackjack.menu.results.blackjack.lore")
    @AnnotationBasedSorter.Order(55)
    @ConfDefault.DefaultString("&7» <#ffd700>Perfect 21! You win <#ffffff>${amount}</nl>&7» <#ffe84d>Blackjack pays 3:2!")
    String blackjackResultBlackjackLore();

    @ConfKey("blackjack.menu.results.player-win.name")
    @AnnotationBasedSorter.Order(56)
    @ConfDefault.DefaultString("<#55ff55>✓ <#77ff77>YOU WIN! <#55ff55>✓")
    String blackjackResultPlayerWinName();

    @ConfKey("blackjack.menu.results.player-win.item")
    @AnnotationBasedSorter.Order(57)
    @ConfDefault.DefaultString("EMERALD")
    Material blackjackResultPlayerWinItem();

    @ConfKey("blackjack.menu.results.player-win.lore")
    @AnnotationBasedSorter.Order(58)
    @ConfDefault.DefaultString("&7» <#55ff55>Congratulations! You win <#ffffff>${amount}</nl>&7» <#77ff77>Better hand than the dealer!")
    String blackjackResultPlayerWinLore();

    @ConfKey("blackjack.menu.results.dealer-bust.name")
    @AnnotationBasedSorter.Order(59)
    @ConfDefault.DefaultString("<#55ff55>✓ <#77ff77>DEALER BUST! <#55ff55>✓")
    String blackjackResultDealerBustName();

    @ConfKey("blackjack.menu.results.dealer-bust.item")
    @AnnotationBasedSorter.Order(60)
    @ConfDefault.DefaultString("EMERALD")
    Material blackjackResultDealerBustItem();

    @ConfKey("blackjack.menu.results.dealer-bust.lore")
    @AnnotationBasedSorter.Order(61)
    @ConfDefault.DefaultString("&7» <#55ff55>Dealer went over 21! You win <#ffffff>${amount}</nl>&7» <#77ff77>The house loses this time!")
    String blackjackResultDealerBustLore();

    @ConfKey("blackjack.menu.results.push.name")
    @AnnotationBasedSorter.Order(62)
    @ConfDefault.DefaultString("<#ffaa00>⟳ <#ffcc00>PUSH! <#ffaa00>⟳")
    String blackjackResultPushName();

    @ConfKey("blackjack.menu.results.push.item")
    @AnnotationBasedSorter.Order(63)
    @ConfDefault.DefaultString("GOLD_INGOT")
    Material blackjackResultPushItem();

    @ConfKey("blackjack.menu.results.push.lore")
    @AnnotationBasedSorter.Order(64)
    @ConfDefault.DefaultString("&7» <#ffaa00>It's a tie! Your bet of <#ffffff>${amount}</nl>&7» <#ffcc00>has been returned to you")
    String blackjackResultPushLore();

    @ConfKey("blackjack.menu.results.player-bust.name")
    @AnnotationBasedSorter.Order(65)
    @ConfDefault.DefaultString("<#ff5555>✗ <#ff7777>BUST! <#ff5555>✗")
    String blackjackResultPlayerBustName();

    @ConfKey("blackjack.menu.results.player-bust.item")
    @AnnotationBasedSorter.Order(66)
    @ConfDefault.DefaultString("REDSTONE_BLOCK")
    Material blackjackResultPlayerBustItem();

    @ConfKey("blackjack.menu.results.player-bust.lore")
    @AnnotationBasedSorter.Order(67)
    @ConfDefault.DefaultString("&7» <#ff5555>You went over 21! You lost <#ffffff>${amount}</nl>&7» <#ff7777>Better luck next time!")
    String blackjackResultPlayerBustLore();

    @ConfKey("blackjack.menu.results.dealer-win.name")
    @AnnotationBasedSorter.Order(68)
    @ConfDefault.DefaultString("<#ff5555>✗ <#ff7777>DEALER WINS! <#ff5555>✗")
    String blackjackResultDealerWinName();

    @ConfKey("blackjack.menu.results.dealer-win.item")
    @AnnotationBasedSorter.Order(69)
    @ConfDefault.DefaultString("REDSTONE_BLOCK")
    Material blackjackResultDealerWinItem();

    @ConfKey("blackjack.menu.results.dealer-win.lore")
    @AnnotationBasedSorter.Order(70)
    @ConfDefault.DefaultString("&7» <#ff5555>Dealer has a better hand! You lost <#ffffff>${amount}</nl>&7» <#ff7777>The house always wins... eventually!")
    String blackjackResultDealerWinLore();
}