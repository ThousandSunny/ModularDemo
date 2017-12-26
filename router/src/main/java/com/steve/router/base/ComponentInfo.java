package com.steve.router.base;

/**
 * Created by SteveYan on 2017/12/25.
 */

public class ComponentInfo {

    private int type;
    private String group;
    private String name;
    private Class<?> clazz;

    public ComponentInfo(int type, String group, String name, Class<?> clazz) {
        this.type = type;
        this.group = group;
        this.name = name;
        this.clazz = clazz;
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
}
