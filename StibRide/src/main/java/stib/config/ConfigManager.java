package stib.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private final Properties prop;
    private final String url;
    private static final String FILE = "./config/config.properties";

    private ConfigManager(){
        prop = new Properties();
        url = getClass().getClassLoader().getResource(FILE).getFile();
    }

    public void load() throws IOException {
        try (InputStream input = new FileInputStream(url)) {
            prop.load(input);
        } catch (IOException ex) {
            throw new IOException("Chargement configuration impossible ",ex);
        }
    }

    public String getProperties(String name){
        return prop.getProperty(name);
    }

    public static ConfigManager getInstance(){
        return ConfigHolder.INSTANCE;
    }

    private static class ConfigHolder{
        private static final ConfigManager INSTANCE = new ConfigManager();
    }
}
