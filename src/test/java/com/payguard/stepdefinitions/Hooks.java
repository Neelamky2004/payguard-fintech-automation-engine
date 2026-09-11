package com.payguard.stepdefinitions;

import com.payguard.base.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends BaseTest {
    @Before
    public void initScenario() {
        setup();
    }

    @After
    public void closeScenario() {
        tearDown();
    }
}
