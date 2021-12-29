import Entity2.MovieList;
import Entity2.User;
import Entity2.UserList;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DataDeal {
    public static void main(String[] args) {
        MovieList movieList = MovieList.getInstance();
        UserList userList = UserList.getInstance();

        File csv = new File("src/main/resources/movies.csv");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
            String[] headers = br.readLine().split(",");
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] buf = line.split(",");
                if (buf.length != 3) {
                    String[] buf1 = line.split("\"");
                    List<String> tagList = List.of(buf1[buf1.length - 1].substring(1).split("\\|"));
                    if (buf1[buf1.length - 1].substring(1).equals("(no genres listed)"))
                        tagList = Collections.emptyList();
                    movieList.addMoive(Integer.parseInt(buf1[0].substring(0, buf1[0].length() - 1)), buf1[1], tagList);
                } else {
                    List<String> tagList = List.of(buf[2].split("\\|"));
                    if (buf[2].equals("(no genres listed)")) tagList = Collections.emptyList();
                    movieList.addMoive(Integer.parseInt(buf[0]), buf[1], tagList);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        csv = new File("src/main/resources/train_ratings_80.csv");
        br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] buf = line.split(",");
                int userId = Integer.parseInt(buf[0]);
                double rating = Double.parseDouble(buf[2]);

                if (!userList.getUserList().containsKey(userId)) {
                    userList.addUser(userId);
                }
                User user = userList.getUser(userId);
                user.setRatingCount(user.getRatingCount() + 1);
                user.setTotalRating(user.getTotalRating() + rating);
                user.setAverage(user.getTotalRating() / user.getRatingCount());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        csv = new File("src/main/resources/train_ratings_80.csv");
        br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] buf = line.split(",");
                int userId = Integer.parseInt(buf[0]);
                int movieId = Integer.parseInt(buf[1]);
                double rating = Double.parseDouble(buf[2]);

                if (rating >= userList.getUser(userId).getAverage()) {
                    List<String> tagList = movieList.getMovie(movieId).getTagList();
                    Map<String, String> map = userList.getUser(userId).getTagList();
                    for (String tag : tagList) {
                        map.put(tag, tag);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter bw = null;
        csv = new File("src/main/resources/userProfile.csv");
        try {
            bw = new BufferedWriter(new FileWriter(csv));
            for (User user : userList.getUserList().values()) {
                StringBuilder tagList_string = new StringBuilder();
                for (String tag : user.getTagList().values()) {
                    tagList_string.append(tag).append("|");
                }
                tagList_string = new StringBuilder(tagList_string.substring(0, tagList_string.length() - 1));
                System.out.println(user.getId() + "\t" + user.getAverage());
                bw.write(user.getId() + "," + tagList_string.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
