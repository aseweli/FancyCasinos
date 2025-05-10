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
    @ConfDefault.DefaultString("Casino")
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
    @ConfDefault.DefaultString("BLACK_STAINED_GLASS_PANE")
    Material blankObject();

    // Custom Items

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
            "v:BOOK:&6&lInstructions:&7- Click to see how to play</nl>&7- Have fun!"
    })
    List<String> customItemDefinitions();

    // Slots

    @ConfComments("Attributes of the slots item in the main menu")
    @ConfKey("gui.layout.slots.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#f0ed35>SLOTS")
    String slotsName();

    @ConfKey("gui.layout.slots.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("GOLD_NUGGET")
    Material slotsItem();

    @ConfKey("gui.layout.slots.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&7- <#f0ed35>Gamble away all your robux with all new slots! </nl>&7- &fHey!")
    String slotsDescription();

    // Mines

    @ConfComments("Attributes of the mines item in the main menu")
    @ConfKey("gui.layout.mines.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#d5d6f5>MINES")
    String minesName();

    @ConfKey("gui.layout.mines.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("IRON_NUGGET")
    Material minesItem();

    @ConfKey("gui.layout.mines.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&7- <#d5d6f5>ROBLOX GO BRRRRRRRR!")
    String minesDescription();

    // Crash

    @ConfComments("Attributes of the crash item in the main menu")
    @ConfKey("gui.layout.crash.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("<#c975c9>CRASH")
    String crashName();

    @ConfKey("gui.layout.crash.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("GHAST_TEAR")
    Material crashItem();

    @ConfKey("gui.layout.crash.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&7- <#c975c9>ROBLOX GO BRRRRRRRR!")
    String crashDescription();

    // Crash
    @ConfComments("Attributes of the crash item in the main menu")
    @ConfKey("gui.layout.blackjack.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("&3Black Jack!")
    String blackjackName();

    @ConfKey("gui.layout.blackjack.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("FLINT")
    Material blackjackItem();

    @ConfKey("gui.layout.blackjack.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&7- <#c975c9>time to gamble it all!")
    String blackjackDescription();

    // Exit

    @ConfComments("Attributes of the exit item in the main menu")
    @ConfKey("gui.layout.exit.name")
    @AnnotationBasedSorter.Order(2)
    @ConfDefault.DefaultString("&c&lEXIT")
    String exitName();

    @ConfKey("gui.layout.exit.item")
    @AnnotationBasedSorter.Order(3)
    @ConfDefault.DefaultString("BARRIER")
    Material exitItem();

    @ConfKey("gui.layout.exit.description")
    @AnnotationBasedSorter.Order(4)
    @ConfDefault.DefaultString("&7- Leave the UI")
    String exitDescription();

    @ConfComments("Setup for the mines menu")

    @ConfKey("mines.menu.name")
    @AnnotationBasedSorter.Order(10)
    @ConfDefault.DefaultString("Mines")
    String minesMenuName();

    @ConfKey("mines.menu.select")
    @AnnotationBasedSorter.Order(11)
    @ConfDefault.DefaultString("Select your difficulty")
    String minesMenuSelect();

    // Tracker

    @ConfKey("mines.menu.tracker.name")
    @AnnotationBasedSorter.Order(12)
    @ConfDefault.DefaultString("&aClaim ${amount}")
    String TrackerName();

    @ConfKey("mines.menu.tracker.item")
    @AnnotationBasedSorter.Order(13)
    @ConfDefault.DefaultString("CHEST")
    Material TrackerItem();

    @ConfKey("mines.menu.tracker.lore")
    @AnnotationBasedSorter.Order(14)
    @ConfDefault.DefaultString("&7 • &fClick here to claim your prize!")
    String TrackerLore();

    // Information

    @ConfKey("mines.menu.information.name")
    @AnnotationBasedSorter.Order(12)
    @ConfDefault.DefaultString("&aInformation about the game!")
    String InformationName();

    @ConfKey("mines.menu.information.item")
    @AnnotationBasedSorter.Order(13)
    @ConfDefault.DefaultString("ANVIL")
    Material InformationItem();

    @ConfKey("mines.menu.information.lore")
    @AnnotationBasedSorter.Order(14)
    @ConfDefault.DefaultString("&7 • &fClick here to claim your prize!")
    String InformationLore();

    // Counter

    @ConfKey("mines.menu.counter.bombs.name")
    @AnnotationBasedSorter.Order(12)
    @ConfDefault.DefaultString("&aNumber of bombs!")
    String BombCounterName();

    @ConfKey("mines.menu.counter.bombs.item")
    @AnnotationBasedSorter.Order(13)
    @ConfDefault.DefaultString("PAPER")
    Material BombCounterItem();

    @ConfKey("mines.menu.counter.bombs.lore")
    @AnnotationBasedSorter.Order(14)
    @ConfDefault.DefaultString("&7 • &fCurrently {amount} bombs watchout!!")
    String BombCounterLore();

    // Counter

    @ConfKey("mines.menu.counter.safe.name")
    @AnnotationBasedSorter.Order(12)
    @ConfDefault.DefaultString("&aNumber of safe slots!")
    String SafeCounterName();

    @ConfKey("mines.menu.counter.safe.item")
    @AnnotationBasedSorter.Order(13)
    @ConfDefault.DefaultString("OAK_SIGN")
    Material SafeCounterItem();

    @ConfKey("mines.menu.counter.safe.lore")
    @AnnotationBasedSorter.Order(14)
    @ConfDefault.DefaultString("&7 • &fCurrently {amount} safe places left!")
    String SafeCounterLore();


    // Mines 1

    @ConfKey("mines.menu.bombs.one.name")
    @AnnotationBasedSorter.Order(22)
    @ConfDefault.DefaultString("&e1 Bomb")
    String minesOneName();

    @ConfKey("mines.menu.bombs.one.multiplier")
    @AnnotationBasedSorter.Order(23)
    @ConfDefault.DefaultDouble(1.05)
    double minesOneMultiplier();

    @ConfKey("mines.menu.bombs.one.item")
    @AnnotationBasedSorter.Order(24)
    @ConfDefault.DefaultString("PINK_DYE")
    Material minesOneItem();

    // Mines 2

    @ConfKey("mines.menu.bombs.two.name")
    @AnnotationBasedSorter.Order(22)
    @ConfDefault.DefaultString("&e2 Bombs")
    String minesTwoName();

    @ConfKey("mines.menu.bombs.two.multiplier")
    @AnnotationBasedSorter.Order(23)
    @ConfDefault.DefaultDouble(1.007)
    double minesTwoMultiplier();

    @ConfKey("mines.menu.bombs.two.item")
    @AnnotationBasedSorter.Order(24)
    @ConfDefault.DefaultString("MAGENTA_DYE")
    Material minesTwoItem();

    // Mines 3

    @ConfKey("mines.menu.bombs.three.name")
    @AnnotationBasedSorter.Order(22)
    @ConfDefault.DefaultString("&e3 Bombs")
    String minesThreeName();

    @ConfKey("mines.menu.bombs.three.multiplier")
    @AnnotationBasedSorter.Order(23)
    @ConfDefault.DefaultDouble(1.01)
    double minesThreeMultiplier();

    @ConfKey("mines.menu.bombs.three.item")
    @AnnotationBasedSorter.Order(24)
    @ConfDefault.DefaultString("PURPLE_DYE")
    Material minesThreeItem();

    // Mines 4

    @ConfKey("mines.menu.bombs.four.name")
    @AnnotationBasedSorter.Order(22)
    @ConfDefault.DefaultString("&e4 Bombs")
    String minesFourName();

    @ConfKey("mines.menu.bombs.four.multiplier")
    @AnnotationBasedSorter.Order(23)
    @ConfDefault.DefaultDouble(1.013)
    double minesFourMultiplier();

    @ConfKey("mines.menu.bombs.four.item")
    @AnnotationBasedSorter.Order(24)
    @ConfDefault.DefaultString("RED_DYE")
    Material minesFourItem();

    // Blackjack

    // Blackjack Menu
    @ConfComments("Setup for the blackjack menu")
    @ConfKey("blackjack.menu.name")
    @AnnotationBasedSorter.Order(30)
    @ConfDefault.DefaultString("Blackjack - Bet: {bet}")
    String blackjackMenuName();

    // Player info
    @ConfKey("blackjack.menu.player.name")
    @AnnotationBasedSorter.Order(31)
    @ConfDefault.DefaultString("&a&lYour Hand: {score}")
    String blackjackPlayerName();

    @ConfKey("blackjack.menu.player.item")
    @AnnotationBasedSorter.Order(32)
    @ConfDefault.DefaultString("PLAYER_HEAD")
    Material blackjackPlayerItem();

    @ConfKey("blackjack.menu.player.lore")
    @AnnotationBasedSorter.Order(33)
    @ConfDefault.DefaultString("&7Your current bet: ${bet}</nl>&7Click cards to see details")
    String blackjackPlayerLore();

    // Dealer info
    @ConfKey("blackjack.menu.dealer.name")
    @AnnotationBasedSorter.Order(34)
    @ConfDefault.DefaultString("&c&lDealer's Hand: {score}")
    String blackjackDealerName();

    @ConfKey("blackjack.menu.dealer.item")
    @AnnotationBasedSorter.Order(35)
    @ConfDefault.DefaultString("PIGLIN_HEAD")
    Material blackjackDealerItem();

    @ConfKey("blackjack.menu.dealer.lore")
    @AnnotationBasedSorter.Order(36)
    @ConfDefault.DefaultString("&7Dealer must stand on 17 or higher</nl>&7and draw on 16 or lower")
    String blackjackDealerLore();

    // Card display
    @ConfKey("blackjack.menu.card.name")
    @AnnotationBasedSorter.Order(37)
    @ConfDefault.DefaultString("&f{rank} of {suit}")
    String blackjackCardName();

    @ConfKey("blackjack.menu.card.lore")
    @AnnotationBasedSorter.Order(38)
    @ConfDefault.DefaultString("&7Value: {value}")
    String blackjackCardLore();

    @ConfKey("blackjack.menu.card.facedown.name")
    @AnnotationBasedSorter.Order(39)
    @ConfDefault.DefaultString("&8Card Face Down")
    String blackjackFacedownName();

    @ConfKey("blackjack.menu.card.facedown.lore")
    @AnnotationBasedSorter.Order(40)
    @ConfDefault.DefaultString("&7The dealer's hole card")
    String blackjackFacedownLore();

    // Button texts
    @ConfKey("blackjack.menu.buttons.hit.name")
    @AnnotationBasedSorter.Order(41)
    @ConfDefault.DefaultString("&a&lHIT")
    String blackjackHitName();

    @ConfKey("blackjack.menu.buttons.hit.item")
    @AnnotationBasedSorter.Order(42)
    @ConfDefault.DefaultString("GREEN_CANDLE")
    Material blackjackHitItem();

    @ConfKey("blackjack.menu.buttons.hit.lore")
    @AnnotationBasedSorter.Order(43)
    @ConfDefault.DefaultString("&7Click to draw another card")
    String blackjackHitLore();

    @ConfKey("blackjack.menu.buttons.stand.name")
    @AnnotationBasedSorter.Order(44)
    @ConfDefault.DefaultString("&c&lSTAND")
    String blackjackStandName();

    @ConfKey("blackjack.menu.buttons.stand.item")
    @AnnotationBasedSorter.Order(45)
    @ConfDefault.DefaultString("RED_CANDLE")
    Material blackjackStandItem();

    @ConfKey("blackjack.menu.buttons.stand.lore")
    @AnnotationBasedSorter.Order(46)
    @ConfDefault.DefaultString("&7Click to stand with your current hand")
    String blackjackStandLore();

    @ConfKey("blackjack.menu.buttons.info.name")
    @AnnotationBasedSorter.Order(47)
    @ConfDefault.DefaultString("&b&lINFO")
    String blackjackInfoName();

    @ConfKey("blackjack.menu.buttons.info.item")
    @AnnotationBasedSorter.Order(48)
    @ConfDefault.DefaultString("OAK_SIGN")
    Material blackjackInfoItem();

    @ConfKey("blackjack.menu.buttons.info.lore")
    @AnnotationBasedSorter.Order(49)
    @ConfDefault.DefaultString("&7Blackjack pays 3:2</nl>&7Dealer stands on 17")
    String blackjackInfoLore();

    @ConfKey("blackjack.menu.buttons.playagain.name")
    @AnnotationBasedSorter.Order(50)
    @ConfDefault.DefaultString("&a&lPLAY AGAIN")
    String blackjackPlayAgainName();

    @ConfKey("blackjack.menu.buttons.playagain.item")
    @AnnotationBasedSorter.Order(51)
    @ConfDefault.DefaultString("EMERALD_BLOCK")
    Material blackjackPlayAgainItem();

    @ConfKey("blackjack.menu.buttons.playagain.lore")
    @AnnotationBasedSorter.Order(52)
    @ConfDefault.DefaultString("&7Click to play another round")
    String blackjackPlayAgainLore();

    // Results
    @ConfKey("blackjack.menu.results.blackjack.name")
    @AnnotationBasedSorter.Order(53)
    @ConfDefault.DefaultString("&a&lBLACKJACK!")
    String blackjackResultBlackjackName();

    @ConfKey("blackjack.menu.results.blackjack.item")
    @AnnotationBasedSorter.Order(54)
    @ConfDefault.DefaultString("DIAMOND")
    Material blackjackResultBlackjackItem();

    @ConfKey("blackjack.menu.results.blackjack.lore")
    @AnnotationBasedSorter.Order(55)
    @ConfDefault.DefaultString("&aYou win ${amount}")
    String blackjackResultBlackjackLore();

    @ConfKey("blackjack.menu.results.player-win.name")
    @AnnotationBasedSorter.Order(56)
    @ConfDefault.DefaultString("&a&lYOU WIN!")
    String blackjackResultPlayerWinName();

    @ConfKey("blackjack.menu.results.player-win.item")
    @AnnotationBasedSorter.Order(57)
    @ConfDefault.DefaultString("EMERALD")
    Material blackjackResultPlayerWinItem();

    @ConfKey("blackjack.menu.results.player-win.lore")
    @AnnotationBasedSorter.Order(58)
    @ConfDefault.DefaultString("&aYou win ${amount}")
    String blackjackResultPlayerWinLore();

    @ConfKey("blackjack.menu.results.dealer-bust.name")
    @AnnotationBasedSorter.Order(59)
    @ConfDefault.DefaultString("&a&lDEALER BUST!")
    String blackjackResultDealerBustName();

    @ConfKey("blackjack.menu.results.dealer-bust.item")
    @AnnotationBasedSorter.Order(60)
    @ConfDefault.DefaultString("EMERALD")
    Material blackjackResultDealerBustItem();

    @ConfKey("blackjack.menu.results.dealer-bust.lore")
    @AnnotationBasedSorter.Order(61)
    @ConfDefault.DefaultString("&aYou win ${amount}")
    String blackjackResultDealerBustLore();

    @ConfKey("blackjack.menu.results.push.name")
    @AnnotationBasedSorter.Order(62)
    @ConfDefault.DefaultString("&e&lPUSH!")
    String blackjackResultPushName();

    @ConfKey("blackjack.menu.results.push.item")
    @AnnotationBasedSorter.Order(63)
    @ConfDefault.DefaultString("GOLD_INGOT")
    Material blackjackResultPushItem();

    @ConfKey("blackjack.menu.results.push.lore")
    @AnnotationBasedSorter.Order(64)
    @ConfDefault.DefaultString("&eYour bet of ${amount} has been returned")
    String blackjackResultPushLore();

    @ConfKey("blackjack.menu.results.player-bust.name")
    @AnnotationBasedSorter.Order(65)
    @ConfDefault.DefaultString("&c&lBUST!")
    String blackjackResultPlayerBustName();

    @ConfKey("blackjack.menu.results.player-bust.item")
    @AnnotationBasedSorter.Order(66)
    @ConfDefault.DefaultString("REDSTONE")
    Material blackjackResultPlayerBustItem();

    @ConfKey("blackjack.menu.results.player-bust.lore")
    @AnnotationBasedSorter.Order(67)
    @ConfDefault.DefaultString("&cYou lost ${amount}")
    String blackjackResultPlayerBustLore();

    @ConfKey("blackjack.menu.results.dealer-win.name")
    @AnnotationBasedSorter.Order(68)
    @ConfDefault.DefaultString("&c&lDEALER WINS!")
    String blackjackResultDealerWinName();

    @ConfKey("blackjack.menu.results.dealer-win.item")
    @AnnotationBasedSorter.Order(69)
    @ConfDefault.DefaultString("REDSTONE")
    Material blackjackResultDealerWinItem();

    @ConfKey("blackjack.menu.results.dealer-win.lore")
    @AnnotationBasedSorter.Order(70)
    @ConfDefault.DefaultString("&cYou lost ${amount}")
    String blackjackResultDealerWinLore();



}
