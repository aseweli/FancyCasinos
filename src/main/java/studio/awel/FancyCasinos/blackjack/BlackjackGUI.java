package studio.awel.FancyCasinos.blackjack;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import studio.awel.FancyCasinos.config.ConfigManager;
import studio.awel.FancyCasinos.utilities.ColorFormater;
import studio.awel.FancyCasinos.utilities.Gambling;
import studio.awel.FancyCasinos.utilities.awel.PlaySounds;
import studio.awel.FancyCasinos.utilities.awel.bjMath;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static studio.awel.FancyCasinos.FancyCasinos.spiGUI;

public class BlackjackGUI {
    private BlackjackGame game;
    private SGMenu menu;
    private final Player player;
    private final double bet;
    private final ConfigManager config;
    private final Material CARD_MATERIAL = Material.PAPER;

    public BlackjackGUI(Player player, double bet, ConfigManager config) {
        this.player = player;
        this.bet = bet;
        this.config = config;
        this.game = new BlackjackGame();
        this.menu = spiGUI.create(ColorFormater.c(config.getConfig().blackjackMenuName().replace("{bet}", formatAmount(bet)) + ColorFormater.addIdentifier("b")), 5);
        setupGUI();
    }

    public void openGUI() {
        game.startGame(bet);
        updateUI();
        player.openInventory(menu.getInventory());
        Gambling.indentPlayerGame(player, "[b]");
        if (game.getPlayerHand().getValue() == 21) {
            forcePlayerWin();
            updateUI();
            endGame();
        }
    }

    private void setupGUI() {
        setupBorder();
        updatePlayerInfo();
        updateDealerInfo();
        setupActionButtons();
    }

    private void setupBorder() {
        int[] borderSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
        setSlot(borderSlots, menu, Material.BLACK_STAINED_GLASS_PANE, " ", "", event -> {});
    }

    private void updateUI() {
        updatePlayerInfo();
        updateDealerInfo();
        updateCards();
        menu.refreshInventory(player);
    }

    private void updateCards() {
        List<Card> dealerCards = game.getDealerHand().getCards();
        for (int i = 0; i < dealerCards.size() && i < 7; i++) {
            if (i == 1 && !game.isGameEnded()) {
                setCardItem(i + 10, null, true);
            } else {
                setCardItem(i + 10, dealerCards.get(i), false);
            }
        }

        List<Card> playerCards = game.getPlayerHand().getCards();
        for (int i = 0; i < playerCards.size() && i < 7; i++) {
            setCardItem(i + 28, playerCards.get(i), false);
        }
    }

    private void updatePlayerInfo() {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwningPlayer(player);
        skullMeta.setDisplayName(ColorFormater.c(config.getConfig().blackjackPlayerName()
                .replace("{score}", String.valueOf(game.getPlayerHand().getValue()))));

        String lore = config.getConfig().blackjackPlayerLore()
                .replace("{bet}", formatAmount(bet));

        String[] loreArray = ColorFormater.c(lore).split("</nl>");
        skullMeta.setLore(Arrays.asList(loreArray));
        playerHead.setItemMeta(skullMeta);

        menu.setButton(18, new SGButton(playerHead));
    }

    private void updateDealerInfo() {
        String dealerScore;
        if (game.isGameEnded()) {
            dealerScore = String.valueOf(game.getDealerHand().getValue());
        } else {
            Card visibleCard = game.getVisibleDealerCard();
            int visibleValue = visibleCard != null ? visibleCard.getValue() : 0;
            dealerScore = visibleValue + " + ?";
        }

        ItemStack dealerHead = new ItemStack(config.getConfig().blackjackDealerItem());
        ItemMeta meta = dealerHead.getItemMeta();
        meta.setDisplayName(ColorFormater.c(config.getConfig().blackjackDealerName()
                .replace("{score}", dealerScore)));
        String lore = config.getConfig().blackjackDealerLore()
                .replace("{score}", dealerScore);
        String[] loreArray = ColorFormater.c(lore).split("</nl>");
        meta.setLore(Arrays.asList(loreArray));
        dealerHead.setItemMeta(meta);

        menu.setButton(26, new SGButton(dealerHead));
    }

    private void setupActionButtons() {
        // Hit button
        createActionButton(20,
                config.getConfig().blackjackHitItem(),
                config.getConfig().blackjackHitName(),
                config.getConfig().blackjackHitLore(),
                event -> {
                    PlaySounds.sound(player, "click");
                    if (!game.isGameEnded()) {
                        Card card = game.playerHit();
                        if (card != null) {
                            updateUI();
                            if ((game.getPlayerHand().getCards().size() >= 5 && game.getPlayerHand().getValue() <= 21)) {
                                forcePlayerWin();
                                updateUI();
                                endGame();
                            } else if (game.isGameEnded()) {
                                endGame();
                            }
                        }
                    } else {
                        player.closeInventory();
                    }
                });

        // Stand button
        createActionButton(21,
                config.getConfig().blackjackStandItem(),
                config.getConfig().blackjackStandName(),
                config.getConfig().blackjackStandLore(),
                event -> {
                    PlaySounds.sound(player, "click");
                    if (!game.isGameEnded()) {
                        game.playerStand();
                        updateUI();
                        endGame();
                    } else {
                        player.closeInventory();
                    }
                });

        // Info button
        createActionButton(24,
                config.getConfig().blackjackInfoItem(),
                config.getConfig().blackjackInfoName(),
                config.getConfig().blackjackInfoLore(),
                event -> PlaySounds.sound(player, "click"));
    }

    private void createActionButton(int slot, Material material, String name, String lore, Consumer<InventoryClickEvent> listener) {
        String[] loreArray = ColorFormater.c(lore).split("</nl>");
        ItemBuilder item = new ItemBuilder(material)
                .name(ColorFormater.c(name))
                .lore(loreArray)
                .amount(1)
                .flag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);

        SGButton button = new SGButton(item.build());
        button.withListener(event -> listener.accept(event));
        menu.setButton(slot, button);
    }

    private void setCardItem(int slot, Card card, boolean faceDown) {
        Material material;
        String name;
        String lore;

        if (faceDown) {
            material = Material.BOOK;
            name = config.getConfig().blackjackFacedownName();
            lore = config.getConfig().blackjackFacedownLore();
        } else {
            material = CARD_MATERIAL;
            name = config.getConfig().blackjackCardName()
                    .replace("{rank}", card.getRank())
                    .replace("{suit}", card.getSuit());
            if (card.getRank().equals("Ace")) {
                lore = config.getConfig().blackjackCardLore()
                        .replace("{value}", "1 or 11");
            } else {
                lore = config.getConfig().blackjackCardLore()
                        .replace("{value}", String.valueOf(card.getValue()));
            }
        }

        setSlot(new int[]{slot}, menu, material, name, lore, event -> {});
    }

    public void setSlot(int[] slots, SGMenu menu, Material material, String name, String lore, Consumer<InventoryClickEvent> listener) {
        String[] result = ColorFormater.c(lore).split("</nl>");
        for (int slot : slots) {
            ItemBuilder item = new ItemBuilder(material)
                    .name(ColorFormater.c(name))
                    .lore(result)
                    .amount(1)
                    .flag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
            SGButton button = new SGButton(item.build());
            button.withListener(event -> listener.accept(event));
            menu.setButton(slot, button);
        }
    }

    private void forcePlayerWin() {
        game = new ForceWinBlackjackGame(game);
    }

    private void endGame() {
        GameResult result = game.getGameResult();
        if (result != null) {
            Material resultMaterial;
            String resultName;
            String resultLore;

            switch (result.getResult()) {
                case PLAYER_BLACKJACK:
                    resultMaterial = config.getConfig().blackjackResultBlackjackItem();
                    resultName = config.getConfig().blackjackResultBlackjackName();
                    resultLore = config.getConfig().blackjackResultBlackjackLore()
                            .replace("{amount}", formatAmount(result.getWinAmount()));
                    PlaySounds.sound(player, "win");
                    break;
                case PLAYER_WIN:
                    resultMaterial = config.getConfig().blackjackResultPlayerWinItem();
                    resultName = config.getConfig().blackjackResultPlayerWinName();
                    resultLore = config.getConfig().blackjackResultPlayerWinLore()
                            .replace("{amount}", formatAmount(result.getWinAmount()));
                    PlaySounds.sound(player, "win");
                    break;
                case DEALER_BUST:
                    resultMaterial = config.getConfig().blackjackResultDealerBustItem();
                    resultName = config.getConfig().blackjackResultDealerBustName();
                    resultLore = config.getConfig().blackjackResultDealerBustLore()
                            .replace("{amount}", formatAmount(result.getWinAmount()));
                    PlaySounds.sound(player, "win");
                    break;
                case PUSH:
                    resultMaterial = config.getConfig().blackjackResultPushItem();
                    resultName = config.getConfig().blackjackResultPushName();
                    resultLore = config.getConfig().blackjackResultPushLore()
                            .replace("{amount}", formatAmount(result.getWinAmount()));
                    PlaySounds.sound(player, "tie");
                    break;
                case PLAYER_BUST:
                    resultMaterial = config.getConfig().blackjackResultPlayerBustItem();
                    resultName = config.getConfig().blackjackResultPlayerBustName();
                    resultLore = config.getConfig().blackjackResultPlayerBustLore()
                            .replace("{amount}", formatAmount(bet));
                    PlaySounds.sound(player, "lose");
                    break;
                case DEALER_WIN:
                    resultMaterial = config.getConfig().blackjackResultDealerWinItem();
                    resultName = config.getConfig().blackjackResultDealerWinName();
                    resultLore = config.getConfig().blackjackResultDealerWinLore()
                            .replace("{amount}", formatAmount(bet));
                    PlaySounds.sound(player, "lose");
                    break;
                default:
                    resultMaterial = Material.PAPER;
                    resultName = "&f&lGAME OVER";
                    resultLore = "&7Result: Unknown";
            }

            Gambling.endPlayerGame(player);

            ItemStack resultItem = new ItemStack(resultMaterial);
            ItemMeta resultMeta = resultItem.getItemMeta();
            resultMeta.setDisplayName(ColorFormater.c(resultName));
            String[] loreArray = ColorFormater.c(resultLore).split("</nl>");
            resultMeta.setLore(Arrays.asList(loreArray));
            resultItem.setItemMeta(resultMeta);

            SGButton resultButton = new SGButton(resultItem);
            resultButton.withListener(event -> {
                PlaySounds.sound(player, "click");
                player.closeInventory();
            });

            SGButton air = new SGButton(new ItemStack(Material.AIR));
            menu.setButton(22, resultButton);
            menu.setButton(20, air);
            menu.setButton(21, air);
            menu.refreshInventory(player);
        }
    }

    public static String formatAmount(double amount) {
        String[] suffix = {"", "k", "m", "b", "t"};
        int index = 0;
        double value = amount;

        while (value >= 1000 && index < suffix.length - 1) {
            value /= 1000;
            index++;
        }

        DecimalFormat df = new DecimalFormat("#,##0.00");
        if (value == (long) value) {
            df = new DecimalFormat("#,##0");
        }

        return df.format(value) + suffix[index];
    }

    private static class ForceWinBlackjackGame extends BlackjackGame {
        private final BlackjackGame originalGame;

        public ForceWinBlackjackGame(BlackjackGame originalGame) {
            this.originalGame = originalGame;
        }

        @Override
        public GameResult getGameResult() {
            if (originalGame.getPlayerHand().getValue() == 21 &&
                    originalGame.getPlayerHand().getCards().size() == 2) {
                return new GameResult(bjMath.ResultType.PLAYER_BLACKJACK, originalGame.getBet() * 1.5);
            } else {
                return new GameResult(bjMath.ResultType.PLAYER_WIN, originalGame.getBet());
            }
        }

        @Override
        public Hand getPlayerHand() {
            return originalGame.getPlayerHand();
        }

        @Override
        public Hand getDealerHand() {
            return originalGame.getDealerHand();
        }

        @Override
        public Card getVisibleDealerCard() {
            return originalGame.getVisibleDealerCard();
        }

        @Override
        public double getBet() {
            return originalGame.getBet();
        }

        @Override
        public boolean isGameEnded() {
            return true;
        }
    }
}