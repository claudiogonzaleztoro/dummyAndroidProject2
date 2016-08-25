package cl.petsos.petsos;

import android.content.Context;

public class PrefUtils {
    private static Boolean existingUser;

    public static void setCurrentUser(User currentUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.putObject("current_user_value", currentUser);
        complexPreferences.commit();
    }

    public static User getCurrentUser(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        User currentUser = complexPreferences.getObject("current_user_value", User.class);
        return currentUser;
    }

    public static void clearCurrentUser( Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }

    public static void setExistingUser(Boolean existingUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.putObject("existing_user_value", existingUser);
        complexPreferences.commit();
    }

    public static Boolean isExistingUser(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        existingUser = complexPreferences.getObject("existing_user_value", Boolean.class);
        return existingUser;
    }
}
