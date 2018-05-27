package com.projectx.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.projectx.main.modelservice.User.UserFirebase;
import com.projectx.main.modelservice.User.UserMobile;
import com.projectx.main.utils.Tools;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PhoneActivation extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {

            // already signed in

           updateStatus(true);

        } else {

            // not signed in
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(
                                    Arrays.asList(
                                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
                                    ))
                            .build(),
                    RC_SIGN_IN);

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);




            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                insertDataUser(response);
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Log.e("Login","Login canceled by User");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Log.e("Login","No Internet Connection");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Log.e("Login","Unknown Error");
                    return;
                }
            }

            Log.e("Login","Unknown sign in response");
        }
    }

    UserFirebase user;
    @SuppressLint("RestrictedApi")
    public void insertDataUser(final IdpResponse response){

     user = new UserFirebase(true,"", Tools.getDateNow(),"","",response.getPhoneNumber(),"","",true,"","android",
            response.getIdpSecret());

        db.collection("mobileUsers")
                .document(response.getUser().getPhoneNumber())
                .set(user, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                     UserMobile   user = new UserMobile(true,"", Tools.getDateNow(),"","",response.getPhoneNumber(),"","",true,"","android",
                                response.getIdpSecret());
                       user.save();
                        updateStatus(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }});



    }


    public void updateStatus(boolean online){

        String nophone = "";
        List<UserMobile> lstUser =  (ArrayList) SQLite.select().from(UserMobile.class)
                .queryList();



        if (lstUser.size()>0){
            nophone = lstUser.get(0).getId();
        }

        DocumentReference updateStatus = db.collection("mobileUsers").document(nophone);


        updateStatus
                .update("online", online)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(PhoneActivation.this,MainMenu.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Update status failed",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PhoneActivation.this,MainMenu.class));
                        finish();
                    }
                });
    }

}