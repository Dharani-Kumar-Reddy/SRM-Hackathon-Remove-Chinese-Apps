package com.example.removechineseapps;

public class App {
    private String app_logo;
    private String app_name;
    private String app_package;

    public App(String app_logo, String app_name, String app_package) {
        this.app_logo = app_logo;
        this.app_name = app_name;
        this.app_package = app_package;
    }


public App()
{

}
    public String getApp_logo() {
        return app_logo;
    }

    public void setApp_logo(String app_logo) {
        this.app_logo = app_logo;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public void setApp_package(String app_package) {
        this.app_package = app_package;
    }

    public String getApp_name() {
        return app_name;
    }

    public String getApp_package() {
        return app_package;
    }
}
