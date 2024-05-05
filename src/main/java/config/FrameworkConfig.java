package config;

import org.aeonbits.owner.Config;

@Config.Sources(value = "file:${user.dir}/src/test/resources/config/config.properties")
public interface FrameworkConfig extends Config {

	String url();

	String Email();

	String Password();
}
//to provide method to access properties and annotations are used to define config properties 
//config is an interface
