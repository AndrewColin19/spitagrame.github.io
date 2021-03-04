package com.spitagram.Controller;

import android.app.Activity;

import com.spitagram.Modele.InstagramApi.InstagramApp;
import com.spitagram.Modele.InstagramApi.Users.CurrentUser;

public class ApiController {

    private static final String TAG = "ApiController";
    private InstagramApp instagramApp;
    private Activity activity;
    public static CurrentUser currentUser;

    public ApiController(Activity activity){
        this.activity = activity;
        getInstenceInstagramApi();
    }
    public InstagramApp getInstenceInstagramApi(){
        if (instagramApp == null){
            instagramApp = new InstagramApp(activity);
        }
        return instagramApp;
    }
    public CurrentUser getInstenceCurrentUser(){
        if (currentUser == null) {
            return new CurrentUser();
        }
        return currentUser;
    }
    public void init(){
        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                instagramApp.getFollowers(currentUser);
                int i = instagramApp.getFollow(currentUser);
                System.out.println("nb following : " + i);
            }
        });
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                currentUser = getInstenceInstagramApi().setUser();
                t2.start();
            }
        });
        t1.start();
    }
}