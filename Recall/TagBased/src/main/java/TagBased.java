import Entity1.Movie;
import Entity1.MovieList;
import Entity1.User;
import Entity1.UserList;
import Tools.Alogrithm;

import java.io.*;
import java.util.*;

public class TagBased {

    public static void initData() {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        csv = new File("src/main/resources/userProfile.csv");
        try {
            br = new BufferedReader(new FileReader(csv));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] buf = line.split(",");
                userList.addUser(Integer.parseInt(buf[0]), List.of(buf[1].split("\\|")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateRecommend() {
        File csv = new File("src/main/resources/test.csv");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(csv));
            bw.write("userId,movieIdList");
            bw.newLine();
            for (User user : UserList.getInstance().getUserList()) {
                Map<Movie, Double> similarities = new HashMap<>();
                for (Movie movie : MovieList.getInstance().getMovieList()) {
                    similarities.put(movie, Alogrithm.caculateSimilarity(movie, user));
                }

                //这里将map.entrySet()转换成list
                List<Map.Entry<Movie, Double>> list = new ArrayList<Map.Entry<Movie, Double>>(similarities.entrySet());
                //然后通过比较器来实现排序
                list.sort(new Comparator<Map.Entry<Movie, Double>>() {
                    @Override
                    public int compare(Map.Entry<Movie, Double> o1, Map.Entry<Movie, Double> o2) {
                        return o2.getValue().compareTo(o1.getValue());
                    }
                });
                StringBuilder tagList_string = new StringBuilder();
                int count = 0;
                for (int i = 1; i <= 20; i++) {
                    tagList_string.append(list.get(i).getKey().getId()).append("|");
                    System.out.println(list.get(i).getKey().getTitle() + "\t" + list.get(i).getValue());
                }
                tagList_string = new StringBuilder(tagList_string.substring(0, tagList_string.length() - 1));
                bw.write(user.getId() + "," + tagList_string);
                bw.newLine();
                System.out.println(user.getId());
                break;
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initData();
        generateRecommend();
    }
}

