package com.santhosh.loganalysis;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RegularExpressionMatcherTest {
    @Test
    void analyseSomeInput() {
        RegularExpressionMatcher reg = new RegularExpressionMatcher();
        try {
            reg.analyse("wired-connection.log", "wired_carplay_connection.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}