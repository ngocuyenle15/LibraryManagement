package com.ptit.librarymanagement;

import com.formdev.flatlaf.FlatLightLaf;
import com.ptit.librarymanagement.context.BaseApplicationContext;

public class MainApplication {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        BaseApplicationContext baseApplicationContext = new BaseApplicationContext();
        baseApplicationContext.runApplication();
    }





}
