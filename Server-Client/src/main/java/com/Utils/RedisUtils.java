package com.Utils;

import com.profile.ProfileGet;
import com.profile.ProfileStore;
import com.service.MovieProfile;
import com.service.UserProfile;

import java.util.Scanner;

public class RedisUtils {

    public static void main(String[] args) {

        ProfileStore profileStore = new ProfileStore();
        // profileStore.storeUserPro();
        profileStore.storeMoviePro();

        ProfileGet profileGet = new ProfileGet();
        int id;
        Scanner scanner = new Scanner(System.in);
        id = scanner.nextInt();
        while (id != -1) {
            MovieProfile movieProfile = profileGet.getMovieProfileById(id);
            System.out.print(movieProfile);
            scanner = new Scanner(System.in);
            id = scanner.nextInt();
        }


//        ProfileGet profileGet = new ProfileGet();
//        int id;
//        Scanner scanner = new Scanner(System.in);
//        id = scanner.nextInt();
//        while (id != -1) {
//            UserProfile userProfile = profileGet.getUserProfileById(id);
//            System.out.print(userProfile);
//            scanner = new Scanner(System.in);
//            id = scanner.nextInt();
//        }
    }
}
