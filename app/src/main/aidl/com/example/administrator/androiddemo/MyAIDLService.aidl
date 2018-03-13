// MyAIDLService.aidl
package com.example.administrator.androiddemo;

// Declare any non-default types here with import statements

/**
 * AndroidStudio的aidl文件默认放在src/main/aidl目录下，aidl目录和java目录同级别,
 * 在java目录上右键，创建一个aidl文件，此文件会默认生成到aidl目录下,
 * 同时必须要指明包名，包名必须和java目录下的包名一致
 *
 * 别忘了Make 一下
 */

interface MyAIDLService {

    int plus(int a, int b);
    String toUpperCase(String str);

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

}
