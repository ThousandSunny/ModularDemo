package com.steve.router.base;

/**
 * Created by SteveYan on 2017/12/25.
 */

public class ComponentInfo {

    public static final int TypeActivity = 1;
    public static final int TypeService = 2;


    private int type;
    private String group;
    private String pkg;
    private String name;
    private Class<?> clazz;

    public ComponentInfo(int type, String group, String pkg, String name) {
        this.type = type;
        this.group = group;
        this.pkg = pkg;
        this.name = name;
    }

    public String getPkg() {
        return pkg;
    }

    public int getType() {
        return type;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
