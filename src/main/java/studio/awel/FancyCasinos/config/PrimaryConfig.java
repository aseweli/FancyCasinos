package studio.awel.xCasinos.config;

import org.bukkit.Material;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.ConfKey;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.util.List;
import java.awt.*;

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




}
