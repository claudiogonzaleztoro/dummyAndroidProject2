package cl.petsos.petsos;

import org.junit.Test;

import cl.petsos.petsos.utils.PetSOSUtility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by root on 11-07-16.
 */
public class PetSOSUtilityTest {
    @Test
    public void shouldReturnFalseIfUserRegisterIsNotComplete() throws Exception {
        User userTest = new User();
        userTest.setName("Maria");
        userTest.setEmail("abc.@gmail.com");
        userTest.setGender("2");
        userTest.setPassword(null);

        assertFalse(PetSOSUtility.isUserRegisterComplete(userTest));
    }



}
