/*
 * |-------------------------------------------------
 * | Copyright © 2016 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.netty.gettingstarted.decompose.server;

public class TimeServerRunner {

    private TimeServerRunner() {}

    public static void main(String[] args) {
        new TimeServer().run();
    }
}
