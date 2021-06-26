package com.example.GestionDeRessource;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context contxt;

    private static final String IS_login = "isLoggedIn";

    private static final String UID = "uid";
    private static final String NOM = "nom";
    private static final String PRENOM = "prenom";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String PASSWORD = "password";
    private static final String TYPE ="type" ;

    public SessionManager(Context context)
    {
        contxt = context;
        userSession = context.getSharedPreferences("userLogin",Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String uid,String nom,String prenom,String email,String phone,String password,String type)
    {
        editor.putBoolean(IS_login,true);

        editor.putString(UID,uid);
        editor.putString(NOM,nom);
        editor.putString(PRENOM,prenom);
        editor.putString(EMAIL,email);
        editor.putString(PHONE,phone);
        editor.putString(PASSWORD,password);
        editor.putString(TYPE,type);

        editor.commit();
    }

    public HashMap<String,String> getUsersDetailsFromSession(){

        HashMap<String,String> userData = new HashMap<String, String>();

        userData.put(UID,userSession.getString(UID,null));
        userData.put(NOM,userSession.getString(NOM,null));
        userData.put(PRENOM,userSession.getString(PRENOM,null));
        userData.put(EMAIL,userSession.getString(EMAIL,null));
        userData.put(PHONE,userSession.getString(PHONE,null));
        userData.put(PASSWORD,userSession.getString(PASSWORD,null));
        userData.put(TYPE,userSession.getString(TYPE,null));

        return userData;
    }

    public Boolean checkLogin()
    {
        if(userSession.getBoolean(IS_login,false))
        {
            return true;
        }
        else
            return false;
    }


    public void logoutSession()
    {
        editor.clear();
        editor.commit();
    }
}
