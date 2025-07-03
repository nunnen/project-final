package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;


class ProfileRestControllerTest extends AbstractControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Get profile with authorized user")
    void getProfileSuccess() {
        fail("Not implemented");
    }

    @Test
    @DisplayName("Get profile with unauthorized user")
    void getProfileFail() {
        fail("Not implemented");
    }

    @Test
    void update() {
    }
}