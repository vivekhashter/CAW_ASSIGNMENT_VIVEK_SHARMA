package config;

public class ConfigFactory {
	
	private ConfigFactory(){
    }

    public static FrameworkConfig getConfig(){
        return org.aeonbits.owner.ConfigFactory.create(FrameworkConfig.class);
    }
}
//config factory is a class used  to create instance of config interface
