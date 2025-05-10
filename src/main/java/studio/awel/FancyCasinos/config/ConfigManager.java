package studio.awel.FancyCasinos.config;

import space.arim.dazzleconf.ConfigurationFactory;
import space.arim.dazzleconf.ConfigurationOptions;
import space.arim.dazzleconf.error.ConfigFormatSyntaxException;
import space.arim.dazzleconf.error.InvalidConfigException;
import space.arim.dazzleconf.ext.snakeyaml.CommentMode;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions;
import space.arim.dazzleconf.helper.ConfigurationHelper;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ConfigManager {

    /**
     * !! Not my code !!
     * Copied from https://github.com/Summiner/TabCompleted/blob/main/src/main/java/rs/jamie/tabcompleted/config/ConfigManager.java#L22 since I didn't want to read all the docs
     * for the configs!
     */

    PrimaryConfig config;
    private final static Logger logger = Logger.getLogger("XCasino Config Manager");
    ConfigurationHelper<PrimaryConfig> configHelper;

    public ConfigManager(File pluginFolder) {
        configHelper = createHelper(PrimaryConfig.class, new File(pluginFolder , "config.yml"));
        reload();
    }

    private static <T> ConfigurationHelper<T> createHelper(Class<T> configClass, File file) {
        SnakeYamlOptions yamlOptions = new SnakeYamlOptions.Builder().commentMode(CommentMode.fullComments()).build();
        ConfigurationOptions.Builder optionBuilder = new ConfigurationOptions.Builder();
        optionBuilder.sorter(new AnnotationBasedSorter());
        ConfigurationFactory<T> configFactory = SnakeYamlConfigurationFactory.create(configClass, optionBuilder.build(), yamlOptions);
        return new ConfigurationHelper<>(file.getParentFile().toPath(), file.getName(), configFactory);
    }

    public void reload() {
        try {
            config = configHelper.reloadConfigData();
        } catch (IOException e) {
            logger.severe("Couldn't open config file!");
            e.printStackTrace();
        } catch (ConfigFormatSyntaxException e) {
            logger.severe("Invalid config syntax!");
            e.printStackTrace();
        } catch (InvalidConfigException e) {
            logger.severe("Invalid config value!");
            e.printStackTrace();
        }
    }

    public PrimaryConfig getConfig() {
        return config;
    }
}
