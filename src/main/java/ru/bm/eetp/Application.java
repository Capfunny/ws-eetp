package ru.bm.eetp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ru.bm.eetp.config.Constants;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class).properties("spring.config.name:ws-eetp");
    }


    public static void main(String[] args) {
        /*if (args.length > 0) {
            Constants.DEBUG = args[0].equals("DEBUG");
        }*/

        SpringApplicationBuilder builder;
        builder = new SpringApplicationBuilder(Application.class);
        builder.properties("spring.config.name:ws-eetp");
        //builder.properties("spring.config.location:file:${CATALINA_HOME}/conf/");
        builder.build().run(args);
    }

}
