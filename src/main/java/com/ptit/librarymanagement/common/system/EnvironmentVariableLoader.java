package com.ptit.librarymanagement.common.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentVariableLoader {
    static {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            props.load(fis);
            for (String key : props.stringPropertyNames()) {
                String value = props.getProperty(key);
                System.setProperty(key, value); // đặt vào System properties
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void loadPropertiesToSystem(String filePath) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
            for (String key : props.stringPropertyNames()) {
                String value = props.getProperty(key);
                System.setProperty(key, value); // đặt vào System properties
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        loadPropertiesToSystem("src/main/resources/application.properties");

        // Test
        System.out.println(System.getProperty("application.database.url"));
        System.out.println(System.getProperty("application.mail.email"));
        System.out.println(System.getProperty("application.mail.email"));
        System.out.println(System.getProperty("application.mail.password"));
    }

}
