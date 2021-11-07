package com.audriuskumpis.labOneThird.service;

import com.audriuskumpis.labOneThird.LabOneThirdApplication;
import com.audriuskumpis.labOneThird.exception.BadEmailException;
import com.audriuskumpis.labOneThird.exception.BadPasswordException;
import com.audriuskumpis.labOneThird.exception.BadPhoneNumberException;
import com.audriuskumpis.labOneThird.model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LabOneThirdApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testSaveUser_shortPassword_ShouldFail() {
        String password = "fails";
        User user = new User(1, "Name", "Last", "861234567", "email@mail.com", "Address", password);
        assertThrows(BadPasswordException.class, () -> userService.save(user));
    }

    @Test
    void testSaveUser_noSpecialsPassword_ShouldFail() {
        String password = "failsButIsLong";
        User user = new User(1, "Name", "Last", "861234567", "email@mail.com", "Address", password);
        assertThrows(BadPasswordException.class, () -> userService.save(user));
    }

    @Test
    void testSaveUser_noCapitalLettersPassword_ShouldFail() {
        String password = "nocapitallettersumbers!@#";
        User user = new User(1, "Name", "Last", "861234567", "email@mail.com", "Address", password);
        assertThrows(BadPasswordException.class, () -> userService.save(user));
    }

    @Test
    void testSaveUser_goodPassword_ShouldPass() {
        String password = "AwesomePassword123!@$#";
        User user = new User(1, "Name", "Last", "861234567", "email@mail.com", "Address", password);
        User saved = userService.save(user);
        assertEquals(user, saved);
    }

    @Test
    void testSaveUser_shortPhone_ShouldFail() {
        String phoneNumber = "86123456";
        User user = new User(1, "Name", "Last", phoneNumber, "email@mail.com", "Address", "AwesomePassword123!@$#");
        assertThrows(BadPhoneNumberException.class, () -> userService.save(user));
    }

    @Test
    void testSaveUser_longPhone_ShouldFail() {
        String phoneNumber = "8612345678978";
        User user = new User(1, "Name", "Last", phoneNumber, "email@mail.com", "Address", "AwesomePassword123!@$#");
        assertThrows(BadPhoneNumberException.class, () -> userService.save(user));
    }

    @Test
    void testSaveUser_longPhone2_ShouldFail() {
        String phoneNumber = "+370612345678978";
        User user = new User(1, "Name", "Last", phoneNumber, "email@mail.com", "Address", "AwesomePassword123!@$#");
        assertThrows(BadPhoneNumberException.class, () -> userService.save(user));
    }

    @Test
    void testSaveUser_badPrefixPhone_ShouldFail() {
        String phoneNumber = "123456789";
        User user = new User(1, "Name", "Last", phoneNumber, "email@mail.com", "Address", "AwesomePassword123!@$#");
        assertThrows(BadPhoneNumberException.class, () -> userService.save(user));
    }

    @Test
    void testSaveUser_goodNumber_ShouldPass() {
        String phoneNumber = "+37012345678";
        User user = new User(1, "Name", "Last", phoneNumber, "email@mail.com", "Address", "AwesomePassword123!@$#");
        User saved = userService.save(user);
        assertEquals(user, saved);
    }

    @Test
    void testSaveUser_emptyNumber_ShouldFail() {
        String phoneNumber = "";
        User user = new User(1, "Name", "Last", phoneNumber, "email@mail.com", "Address", "AwesomePassword123!@$#");
        assertThrows(BadPhoneNumberException.class, () -> userService.save(user));
    }

    @Test
    void testSaveUser_noEta_ShouldFail() {
        String email = "hello_at_email.com";
        User user = new User(1, "Name", "Last", "+37012345678", email, "Address", "AwesomePassword123!@$#");
        assertThrows(BadEmailException.class, () -> userService.save(user));
    }

    @Test
    @Disabled
    void testSaveUser_noTLD_ShouldFail() {
        String email = "hello@email";
        User user = new User(1, "Name", "Last", "+37012345678", email, "Address", "AwesomePassword123!@$#");
        assertThrows(BadEmailException.class, () -> userService.save(user));
    }

    @Test
    @Disabled
    void testSaveUser_noDomain_ShouldFail() {
        String email = "hello@.com";
        User user = new User(1, "Name", "Last", "+37012345678", email, "Address", "AwesomePassword123!@$#");
        assertThrows(BadEmailException.class, () -> userService.save(user));
    }

    @Test
    @Disabled
    void testSaveUser_noAddress_ShouldFail() {
        String email = "@email.com";
        User user = new User(1, "Name", "Last", "+37012345678", email, "Address", "AwesomePassword123!@$#");
        assertThrows(BadEmailException.class, () -> userService.save(user));
    }

    @Test
    @Disabled
    void testSaveUser_nullAddress_ShouldFail() {
        String email = null;
        User user = new User(1, "Name", "Last", "+37012345678", email, "Address", "AwesomePassword123!@$#");
        assertThrows(BadEmailException.class, () -> userService.save(user));
    }

    @Test
    void testSaveUser_empty_ShouldFail() {
        String email = "";
        User user = new User(1, "Name", "Last", "+37012345678", email, "Address", "AwesomePassword123!@$#");
        assertThrows(BadEmailException.class, () -> userService.save(user));
    }

    @Test
    void testSaveUser_goodEmail_ShouldPass() {
        String email = "good_email@email.com";
        User user = new User(1, "Name", "Last", "+37012345678", email, "Address", "AwesomePassword123!@$#");
        User saved = userService.save(user);
        assertEquals(user, saved);
    }

    @Test
    void testRetrieveUsers_shouldPass() {
        User user = new User(1, "Name", "Last", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        User user2 = new User(2, "Name2", "Last2", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        userService.save(user);
        userService.save(user2);

        List<User> expected = List.of(user, user2);
        List<User> actual = userService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void testDeleteUser_shouldPass() {
        User user = new User(1, "Name", "Last", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        User user2 = new User(2, "Name2", "Last2", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        userService.save(user);
        userService.save(user2);

        userService.deleteById(1);

        List<User> retrieved = userService.findAll();
        assertEquals(List.of(user2), retrieved);
    }

    @Test
    void testFindById_shouldPass() {
        User user = new User(1, "Name", "Last", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        User user2 = new User(2, "Name2", "Last2", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        userService.save(user);
        userService.save(user2);

        User actual = userService.findById(2);

        assertEquals(user2, actual);
    }

    @Test
    void testFindByIdBadId_shouldFail() {
        User user = new User(1, "Name", "Last", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        User user2 = new User(2, "Name2", "Last2", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        userService.save(user);
        userService.save(user2);

        assertThrows(NoSuchElementException.class, () -> userService.findById(3));
    }

    @Test
    void testFindByIdNegativedId_shouldFail() {
        User user = new User(1, "Name", "Last", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        User user2 = new User(2, "Name2", "Last2", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        userService.save(user);
        userService.save(user2);

        assertThrows(IllegalArgumentException.class, () -> userService.findById(-1));
    }

    @Test
    void testUpdateUser_shouldPass() {
        User user = new User(1, "Name", "Last", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        User user2 = new User(1, "Name2", "Last2", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        userService.save(user);

        userService.update(1, user2);
        User retrieved = userService.findById(1);
        assertEquals(user2, retrieved);
    }

    @Test
    void testUpdateUser_badNewUser_shouldFail() {
        User user = new User(1, "Name", "Last", "861234567", "email@mail.com", "Address", "AwesomePassword123!@$#");
        User user2 = new User(1, "Name2", "Last2", "8612345", "email@mail.com", "Address", "AwesomePassword123!@$#");
        userService.save(user);

        assertThrows(BadPhoneNumberException.class, () -> userService.update(1, user2));
    }
}
