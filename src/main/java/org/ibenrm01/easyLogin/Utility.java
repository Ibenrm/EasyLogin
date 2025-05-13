package org.ibenrm01.easyLogin;

import java.util.Map;

public class Utility {


    private final static Utility instance = new Utility();

    private Utility() {
    }

    public static String replace(String message, Map<String, String> keys) {
        for (Map.Entry<String, String> entry : keys.entrySet()) {
            message = message.replace("{" + entry.getKey().toLowerCase() + "}", entry.getValue());
        }
        return message;
    }

    public static Utility getInstance() {
        return instance;
    }
}
