package app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Config {
//    @Value("${flashcards.language}")
    public static String LANGUAGE;

    @Value("${flashcards.language}")
    public void setLANGUAGE(String LANGUAGE) {
        Config.LANGUAGE = LANGUAGE;
    }
}
