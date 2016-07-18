package cl.petsos.petsos.utils;

import cl.petsos.petsos.User;

/**
 * Created by root on 11-07-16.
 */
public class PetSOSUtility {
    private static PetSOSUtility petUtility;

    public  static PetSOSUtility getPetSOSUtility() {
        if (petUtility==null) {
            petUtility=new PetSOSUtility();
        }
        return petUtility;
    }

    public static boolean isUserRegisterComplete(User user) {
        if(user.getName() != null && !user.getName().trim().equals("")
                && user.getEmail() != null && !user.getEmail().trim().equals("")
                && user.getGender() != null && !user.getGender().trim().equals("")
                && user.getPassword() != null && !user.getPassword().trim().equals("")){
            return true;
        }
        return false;
    }

}
