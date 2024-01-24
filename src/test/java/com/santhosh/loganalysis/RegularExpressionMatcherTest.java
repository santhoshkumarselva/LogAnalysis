package com.santhosh.loganalysis;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class RegularExpressionMatcherTest {
    @Test
    void analyseSomeInput() {
        RegularExpressionMatcher reg = new RegularExpressionMatcher();
        try {
            reg.analyse("C:\\Users\\skumars\\Desktop\\wired-connection.log", new File("abcd.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void analyseFilePathStringInput() {
        RegularExpressionMatcher reg = new RegularExpressionMatcher();
        try {
            String misc = reg.analyse("C:\\Users\\skumars\\Downloads\\Working logs new\\Wireless\\new-cp-device-bt-pairing+session_connection.log", new File("C:\\Users\\skumars\\IdeaProjects\\RemoteProjects\\JavaFxLearning\\src\\main\\resources\\wireless_carplay_connection.xml"));
            System.out.println(misc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}