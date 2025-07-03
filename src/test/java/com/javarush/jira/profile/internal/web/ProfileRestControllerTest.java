package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.model.Profile;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserTestData.jsonWithPassword;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
class ProfileRestControllerTest extends AbstractControllerTest {
    public static final String URL = "/api/profile";
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String PASSWORD = "password";
    public static final long USER_ID = 1;
    public static final long ADMIN_ID = 2;

    @Autowired
    private ProfileMapper mapper;

    @Test
    @DisplayName("Get profile of authorized user")
    @WithUserDetails(value = USER_MAIL)
    void getProfileSuccessUser() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(USER_ID));
    }

    @Test
    @DisplayName("Get profile of authorized admin")
    @WithUserDetails(value = ADMIN_MAIL)
    void getProfileSuccessAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(ADMIN_ID));
    }

    @Test
    @DisplayName("Get profile of unauthorized user")
    void getProfileFail() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Update profile of authorized user")
    @WithUserDetails(value = USER_MAIL)
    void updateProfile() throws Exception {
        Profile updatedProfile = getUpdated(USER_ID);
        ProfileTo updatedProfileTo = mapper.toTo(updatedProfile);

        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updatedProfileTo, PASSWORD)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Update with unauthorized user")
    void updateUserUnauthorized() throws Exception {
        ProfileTo updatedProfileTo = getUpdatedTo();

        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updatedProfileTo, PASSWORD)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Update with invalid data")
    @WithUserDetails(value = USER_MAIL)
    void updateUserInvalidData() throws Exception {
        ProfileTo invalidProfileTo = getInvalidTo();

        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(invalidProfileTo, PASSWORD)))
                .andExpect(status().isUnprocessableEntity());
    }
}