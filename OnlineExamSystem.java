//TASK 1::


import java.util.*;

class User {
    String userId;
    String password;
    String name;

    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }
}

class OnlineExam {
    private Scanner sc = new Scanner(System.in);
    private User currentUser;
    private boolean sessionActive = true;

    private Map<String, String> users = new HashMap<>() {{
        put("user1", "pass1");
        put("user2", "pass2");
    }};

    private Map<String, String> userProfiles = new HashMap<>() {{
        put("user1", "John Doe");
        put("user2", "Jane Smith");
    }};

    private Map<Integer, String> questions = new HashMap<>() {{
        put(1, "Java is a: a) Language b) OS c) Hardware d) Browser");
        put(2, "Which keyword is used to inherit a class in Java? a) super b) this c) extends d) implements");
    }};

    private Map<Integer, String> answers = new HashMap<>() {{
        put(1, "a");
        put(2, "c");
    }};

    public void start() {
        login();
        while (sessionActive) {
            showMenu();
        }
    }

    private void login() {
        while (true) {
            System.out.print("Enter User ID: ");
            String userId = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            if (users.containsKey(userId) && users.get(userId).equals(password)) {
                currentUser = new User(userId, password, userProfiles.get(userId));
                System.out.println("Login successful! Welcome, " + currentUser.name);
                break;
            } else {
                System.out.println("Invalid credentials, try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n1. Update Profile/Password");
        System.out.println("2. Start Exam");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> updateProfile();
            case 2 -> startExam();
            case 3 -> logout();
            default -> System.out.println("Invalid choice!");
        }
    }

    private void updateProfile() {
        System.out.print("Enter new name: ");
        String newName = sc.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();

        userProfiles.put(currentUser.userId, newName);
        users.put(currentUser.userId, newPassword);
        currentUser.name = newName;
        currentUser.password = newPassword;

        System.out.println("Profile updated successfully!");
    }

    private void startExam() {
        System.out.println("Starting exam... You have 60 seconds.");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("Time's up! Submitting the exam.");
                submitExam(new HashMap<>());
            }
        }, 60000);

        Map<Integer, String> userAnswers = new HashMap<>();
        for (int q : questions.keySet()) {
            System.out.println(questions.get(q));
            System.out.print("Enter your answer: ");
            String answer = sc.nextLine();
            userAnswers.put(q, answer);
        }
        timer.cancel();
        submitExam(userAnswers);
    }

    private void submitExam(Map<Integer, String> userAnswers) {
        int score = 0;
        for (int q : answers.keySet()) {
            if (answers.get(q).equalsIgnoreCase(userAnswers.getOrDefault(q, ""))) {
                score++;
            }
        }
        System.out.println("Exam submitted. Your score is: " + score + "/" + answers.size());
    }

    private void logout() {
        System.out.println("Logging out...");
        sessionActive = false;
    }
}

public class OnlineExamSystem {
    public static void main(String[] args) {
        OnlineExam exam = new OnlineExam();
        exam.start();
    }
}
