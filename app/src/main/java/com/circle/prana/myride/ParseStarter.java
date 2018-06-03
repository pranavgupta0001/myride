package com.circle.prana.myride;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class ParseStarter extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //GjrkADSBKXt1
        //user

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

//    databaseURI: "mongodb://root:GjrkADSBKXt1@127.0.0.1:27017/bitnami_parse",
//            cloud: "./node_modules/parse-server/lib/cloud-code/Parse.Cloud.js",
//            appId: "39b85eacba94c12bd28792452f1397c62ad7706f",
//            masterKey: "d436d6957133f1f0b77de88f4756f41db527052e",
//            fileKey: "fbefce837e897a95690f216858a726b5cd975708",
//            serverURL: "http://54.227.165.3:80/parse"
//        classpath 'com.google.gms:google-services:4.0.0'
//    apply plugin: 'com.google.gms.google-services'


        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("39b85eacba94c12bd28792452f1397c62ad7706f")
                .clientKey("d436d6957133f1f0b77de88f4756f41db527052e")
                .server("http://54.227.165.3:80/parse/")
                .build()
        );

//        ParseObject object = new ParseObject("ExampleObject");
//        object.put("myNumber", "1236");
//        object.put("myString", "robb");

//        object.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException ex) {
//                if (ex == null) {
//                    Log.i("Parse Result", "Successful!");
//                } else {
//                    Log.i("Parse Result", "Failed" + ex.toString());
//                }
//            }
//        });


        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}