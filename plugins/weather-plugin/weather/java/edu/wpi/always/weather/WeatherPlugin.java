package edu.wpi.always.weather;

import org.joda.time.DateTime;
import edu.wpi.always.*;
import edu.wpi.always.cm.CollaborationManager;
import edu.wpi.always.user.UserModel;
import edu.wpi.always.weather.wunderground.WundergroundWeatherProvider;

// TODO Make this work with live daily data

public class WeatherPlugin extends Plugin {
   
   public WeatherPlugin (UserModel userModel, CollaborationManager cm) {
      super("Weather", userModel, cm);
      addActivity("DiscussWeather", 0, 0, 0, 0, WeatherSchema.class); 
   }
   
   // Property names must be constants and start with plugin name
   // This is just an example for testing
   public static final String 
         FAVORITE = "WeatherFavorite",
         FLAG = "WeatherFlag",
         RANK = "WeatherRank",
         TIME = "WeatherTime";
         
   /**
    * For testing Weather by itself
    */
   public static void main (String[] args) {
      Always always = Plugin.main(args, WeatherPlugin.class, "DiscussWeather");
      // code below is temporary to demonstrate how to define and use a new user 
      // property extensions (see Weather.owl)
      // see WeatherSchema console window for printout
      Plugin plugin = always.getContainer().getComponent(WeatherPlugin.class);
      plugin.setProperty(FAVORITE, "hot and humid");
      plugin.setProperty(RANK, 2);
      plugin.setProperty(FLAG, true);
      plugin.setProperty(TIME, System.currentTimeMillis());
      if ( plugin.isProperty(FLAG) ) 
         System.out.println("At "+new DateTime(plugin.getLongProperty(TIME))
               +" my #"+plugin.getIntProperty(RANK)
               +" favorite weather is "+plugin.getProperty(FAVORITE));
   }
  

  
}
