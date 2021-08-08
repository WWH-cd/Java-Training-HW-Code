package user.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class JdbctemplateBatchApp implements CommandLineRunner {
    @Autowired
    private UserInfoDAO userInfoDAO;

    public static void main(String[] args) {
        SpringApplication.run(JdbctemplateBatchApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<UserInfo> userInfos = prepareUserInfos();
        userInfoDAO.batchInsert(userInfos);
    }

    private List<UserInfo> prepareUserInfos() {
        List<UserInfo> userInfos = new ArrayList<>();

        List<String> usernames = generateUsernames();
        int nameSize = usernames.size();
        Random ran = new Random();

        for (int i = 0; i < nameSize; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(usernames.get(i));
            userInfo.setPassword("1q2w3e4r");
            userInfo.setAge(ran.nextInt(100));
            // 0 for success, 1 for failure
            userInfo.setStatus(0);
            // 0 for male, 1 for female
            userInfo.setGender(ran.nextInt(2));
            userInfo.setSsn(String.valueOf(5555555555555555555L+ ran.nextInt(999999999)));
            userInfo.setCreatedAt(System.currentTimeMillis());
            userInfo.setUpdatedAt(System.currentTimeMillis());
        }
        return userInfos;
    }

    private List<String> generateUsernames() {
        List<String> usernames = new ArrayList<>();
        List<String> nameP1 = Arrays.asList("Mary", "Patricia", "Jennifer", "Linda", "Elizabeth", "Barbara", "Susan", "Jessica", "Sarah", "Karen", "Nancy", "Lisa", "Betty", "Margaret", "Sandra", "Ashley", "Kimberly", "Emily", "Donna", "Michelle", "Dorothy", "Carol", "Amanda", "Melissa", "Deborah", "Stephanie", "Rebecca", "Sharon", "Jeffrey", "Ryan", "Jacob Kathleen", "Gary", "Nicholas", "Eric", "Jonathan", "Stephen", "Larry", "Justin", "Scott", "Brandon", "Benjamin", "Samuel", "Gregory", "Frank", "Alexander", "Raymond", "Patrick", "Jack", "Dennis", "Jerry", "Tyler", "Aaron", "Jose", "Adam", "Henry", "Nathan", "Douglas", "Zachary", "Peter", "Kyle", "Walter", "Ethan", "Jeremy", "Harold", "Keith", "Christian", "Roger", "Noah", "Gerald", "Carl", "Terry", "Sean", "Austin", "Arthur", "Lawrence", "Jesse", "Dylan", "Bryan", "Joe", "Jordan", "Billy", "Bruce", "Albert", "Willie", "Gabriel", "Logan", "Alan", "Juan", "Wayne", "Roy", "Ralph", "Randy", "Eugene", "Vincent", "Russell", "Elijah", "Louis", "Bobby", "Philip", "Johnny");
        List<String> nameP2 = Arrays.asList("James229", "Robert696", "John807", "Michael919", "William276", "David053", "Richard407", "Joseph792", "Thomas864", "Charles043", "Christopher798", "Daniel292", "Matthew467", "Anthony030", "Mark519", "Donald753", "Steven598", "Paul700", "Andrew054", "Joshua730", "Kenneth221", "Kevin762", "Brian073", "George235", "Edward619", "Ronald746", "Timothy916", "Jason335", "Laura718", "Cynth705", "686", "A681", "Shirl663", "Angel658", "Helen618", "Anna618", "Brend606", "Pamel592", "Nicol589", "Emma580", "Samantha578", "Katherine571", "Christine561", "Debra548", "Rache546", "Catherine542", "Carol540", "Janet539", "Ruth538", "Maria529", "Heath524", "Diane515", "Virginia515", "Julie506", "Joyce499", "Victoria485", "Olivi481", "Kelly471", "Christina471", "Laure470", "Joan467", "Evely461", "Judit449", "Megan437", "Chery436", "Andre436", "Hanna431", "Marth426", "Jacquelin420", "Franc414", "Glori408", "A407", "Teres404", "Kathr402", "Sara400", "Janic399", "Jean399", "Alice396", "Madis393", "Doris383", "Abiga381", "Julia380", "Judy377", "Grace377", "Denis371", "Amber370", "Maril369", "Bever369", "Danielle368", "There367", "Sophi364", "Marie361", "Diana359", "Brittany358", "Natal356", "Isabella354", "Charlotte347", "Rose344", "Alexi340", "Kayla340");
        int p1Size = nameP1.size();
        int p2Size = nameP2.size();
        int targetSize = 1000*1000;
        Random rand = new Random();
        for (int i = 0; i < targetSize; i++) {
            String name = nameP1.get(rand.nextInt(p1Size)) + nameP2.get(rand.nextInt(p2Size));
            usernames.add(name);
        }
        return usernames;
    }
}
