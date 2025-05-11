# FancyCasinos

A premium casino plugin for Minecraft servers featuring beautifully designed gambling games with customizable UI and configurations.

![Casino Preview](https://i.imgur.com/placeholder.png)

## ✨ Features

- **Multiple Casino Games**:
  - 🎭 **Blackjack** - Classic card game with dealers and betting
  - 💣 **Mines** - Risk-based game where players avoid mines for rewards
  - 🎰 **Slots** - Traditional slot machine experience
  - 📈 **Crash** - Multiplier-based game with risk/reward mechanics

- **Advanced UI System**:
  - Fully customizable menu layouts
  - Interactive buttons with custom actions
  - Animated result displays
  - Material and name customization

- **Economy Integration**:
  - Vault support for economy transactions
  - Customizable bet limits and payouts
  - Win/loss tracking

- **Configuration**:
  - Extensive customization options
  - Custom item support
  - Multiplier controls for each game
  - Locale customization

## 📥 Installation

1. Download the latest release from [Spigot](https://www.spigotmc.org/resources/) or the [releases page](https://github.com/awelpy/FancyCasinos/releases)
2. Place the JAR file in your server's `plugins` folder
3. Restart your server
4. Edit the configuration files as needed
5. Use `/casino reload` to apply changes

### Dependencies
- [Vault](https://www.spigotmc.org/resources/vault.34315/) - For economy support
- [SpiGUI](https://github.com/SamJakob/SpiGUI) - For GUI handling (included)

## ⚙️ Configuration

FancyCasinos offers extensive configuration options through the `config.yml` file. Here's an example of key settings:

```yaml
# Main GUI settings
gui:
  name: "&6&lFancy Casinos"
  layout: |
    ---------
    --b-m-s--
    ---------
    --c---x--
    ---------

# Game multipliers
blackjack:
  enabled: true
  payout: 1.5
  naturalBlackjack: 2.5

mines:
  enabled: true
  oneMultiplier: 1.01
  twoMultiplier: 1.05
  threeMultiplier: 1.08
  fourMultiplier: 1.12

# Custom items
customItems:
  - "1:DIAMOND:&bDiamond Game:Special diamond game</nl>Bet to win big!"
  - "2:EMERALD:&aEmerald Game:Another custom game</nl>Try your luck!"
```

## 🎮 Usage

### Commands
- `/casino` - Opens the main casino GUI
- `/casino reload` - Reloads the configuration
- `/casino help` - Shows help information

### Permissions
- `fancycasinos.use` - Access to use the casino
- `fancycasinos.admin` - Access to admin commands
- `fancycasinos.game.blackjack` - Permission to play blackjack
- `fancycasinos.game.mines` - Permission to play mines
- `fancycasinos.game.slots` - Permission to play slots
- `fancycasinos.game.crash` - Permission to play crash

## 🎲 Game Descriptions

### Blackjack
A classic card game where players compete against the dealer. The goal is to get a hand value as close to 21 as possible without going over. Natural blackjacks (21 with first two cards) pay at a higher rate.

### Mines
A risk-based game where players select tiles, hoping to avoid mines. Each safe tile increases the multiplier. Players can cash out at any time or risk continuing for higher rewards.

- **Difficulty Options**:
  - 1 Mine: Lower risk, smaller rewards
  - 2 Mines: Balanced risk/reward
  - 3 Mines: Higher risk, better rewards
  - 4 Mines: Highest risk, maximum rewards

## 🔧 Technical Details

FancyCasinos is built using:
- Java/Spigot API
- SpiGUI for menu handling
- Vault API for economy integration
- Custom event system for game management

The plugin utilizes efficient code practices:
- Caching of frequently accessed data
- Optimized item creation
- Minimal inventory refreshes
- Clean architecture with separation of concerns

## 📝 License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.

## 🤝 Support & Contributing

For support, please join our [Discord server](https://discord.gg/example) or open an issue on GitHub.

Contributions are welcome! Please feel free to submit a Pull Request.

---

Made with ❤️ by [AwelPy](https://github.com/awelpy)
