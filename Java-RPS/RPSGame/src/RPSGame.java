import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class RPSGame {
    static Connection conn = null;

    public static void main(String[] args) {
        connect();
        int mode = 0, p1 = 0, p2 = 0, comp = 0;
        String name1 = "", name2 = "";
        char choice, ch;
        System.out.println("\t    *****************\t\t     WELCOME\t\t\t*****************");
        Scanner ob = new Scanner(System.in);
        do {
            do {
                System.out.println("\t    *****************\t\tROCK PAPER SCISSORS\t\t*****************");
                System.out.println("Rules:-");
                System.out.println("Rock Defeats Scissors");
                System.out.println("Paper Defeats Rock");
                System.out.println("Scissor Defeats Paper\n");
                System.out.println("Select the mode");
                System.out.println("1)Computer");
                System.out.println("2)2 Players");
                System.out.println("3)Exit the Game");
                System.out.println("4)Show Game Data History");
                mode = ob.nextInt();
            } while (invalid(mode));
            clear();
            if (mode == 1) {
                p1 = rp();
                ch(p1);
                Random random = new Random();
                comp = random.nextInt(3) + 1;
                if (comp == 1) {
                    System.out.println("Computer chose Rock");
                } else if (comp == 2) {
                    System.out.println("Computer chose Paper");
                } else if (comp == 3) {
                    System.out.println("Computer chose Scissors");
                }
                if (comp == p1) {
                    System.out.println("DRAW");
                } else {
                    if (comp == 1 && p1 == 2 || comp == 2 && p1 == 3 || comp == 3 && p1 == 1) {
                        System.out.println("You Win");
                    } else {
                        System.out.println("Computer Wins");
                    }
                }
            } else if (mode == 2) {
                System.out.println("Enter the name of Player 1");
                name1 = ob.next();
                System.out.printf("%s's Turn\n", name1);
                p1 = rp();
                clear();
                System.out.println("Enter the name of Player 2");
                name2 = ob.next();
                System.out.printf("%s's Turn\n", name2);
                p2 = rp();
                clear();
                ch(name1, p1);
                ch(name2, p2);
                if (p2 == p1) {
                    System.out.println("DRAW");
                } else {
                    if (p1 == 2 && p2 == 1 || p1 == 3 && p2 == 2 || p1 == 1 && p2 == 3) {
                        System.out.printf("%s Wins\n", name1);
                    } else {
                        System.out.printf("%s Wins\n", name2);
                    }
                }
            } else if (mode == 3) {
                System.out.println("\t    *****************\t\tThanks for Playing\t\t*****************");
                exit();
            } else if (mode == 4) {
                showData();
                System.out.println("Enter 'OK' to go back to the main menu.");
                while (!ob.next().equalsIgnoreCase("OK")) {
                    System.out.println("Please enter 'OK' to continue.");
                }
            }
            if (mode != 4) {
                System.out.println("Store Game Record? Y/N");
                ch = ob.next().charAt(0);
                records(ch, mode, name1, name2, p1, p2, comp);
                System.out.println("Play Again? Y/N");
                choice = ob.next().charAt(0);
                clear();
            } else {
                choice = 'Y'; // to continue the loop
            }
        } while (choice == 'Y' || choice == 'y');
        System.out.println("\t    *****************\t\t    GAME OVER\t\t\t*****************");
        System.out.println("\t    *****************\t\tThanks for Playing\t\t*****************");
        ob.close();
        exit();
    }

    static int rp() {
        int p;
        do {
            System.out.println("1.Rock");
            System.out.println("2.Paper");
            System.out.println("3.Scissors");
            System.out.println("Enter your choice");
            Scanner ob = new Scanner(System.in);
            p = ob.nextInt();
        } while (invalid(p));
        return p;
    }

    static void ch(int p) {
        if (p == 1) {
            System.out.println("You chose Rock");
        } else if (p == 2) {
            System.out.println("You chose Paper");
        } else if (p == 3) {
            System.out.println("You chose Scissors");
        }
    }

    static void ch(String name, int p) {
        if (p == 1) {
            System.out.printf("%s chose Rock\n", name);
        } else if (p == 2) {
            System.out.printf("%s chose Paper\n", name);
        } else if (p == 3) {
            System.out.printf("%s chose Scissors\n", name);
        }
    }

    static boolean invalid(int p) {
        if (p < 1 || p > 4) {  // Adjusted to include 4 as a valid option
            System.out.println("Enter a Valid Choice");
            return true;
        }
        return false;
    }

    static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RPSgame", "root", "Green@5supr");
            //System.out.println("Connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                //System.out.println("Disconnected from the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void insertRecord(String mode, String player1, String player2, String p1Choice, String p2Choice, String result) {
        String sql = "INSERT INTO game_records (mode, player1_name, player2_name, player1_choice, player2_choice, result, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mode);
            pstmt.setString(2, player1);
            pstmt.setString(3, player2);
            pstmt.setString(4, p1Choice);
            pstmt.setString(5, p2Choice);
            pstmt.setString(6, result);
            pstmt.setTimestamp(7, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
            System.out.println("Record inserted into database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void createFile() {
        File myFile = new File("RPSgame.txt");
        try {
            if (!myFile.exists()) {
                myFile.createNewFile();
                System.out.println("File created: " + myFile.getName());
                FileWriter fileWriter = new FileWriter("RPSgame.txt", true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write("\t    *****************          ROCK PAPER SCISSORS              *****************\n");
                bw.write("\t    *****************             GAME RECORDS                  *****************\n\n");
                bw.close();
            } else {
                System.out.println("File" + myFile.getName() + " is updated");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void file(String name1, String name2, int p1, int p2) {
        String ch1 = choiceToString(p1);
        String ch2 = choiceToString(p2);
        try {
            FileWriter fileWriter = new FileWriter("RPSgame.txt", true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write("2 Player Mode\n");
            bw.write("\n" + name1 + " VS " + name2 + "\n");
            bw.write("\n" + name1 + " Chose " + ch1 + "\n" + name2 + " Chose " + ch2 + "\n");
            if (p2 == p1) {
                bw.write("DRAW\n");
            } else {
                if (p1 == 2 && p2 == 1 || p1 == 3 && p2 == 2 || p1 == 1 && p2 == 3) {
                    bw.write(name1 + " Wins\n");
                } else {
                    bw.write(name2 + " Wins\n");
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        time();
    }

    static void file(int p1, int comp) {
        String ch1 = choiceToString(p1);
        String ch2 = choiceToString(comp);
        try {
            FileWriter fileWriter = new FileWriter("RPSgame.txt", true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write("Computer Mode\n");
            bw.write("\nYou Chose " + ch1 + "\n" + "Computer Chose " + ch2 + "\n");
            if (comp == p1) {
                bw.write("DRAW\n");
            } else {
                if (comp == 1 && p1 == 2 || comp == 2 && p1 == 3 || comp == 3 && p1 == 1) {
                    bw.write("You Win\n");
                } else {
                    bw.write("Computer Wins\n");
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        time();
    }

    static void time() {
        try {
            FileWriter fileWriter = new FileWriter("RPSgame.txt", true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            bw.write("Played on: " + dtf.format(now) + "\n\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String choiceToString(int choice) {
        switch (choice) {
            case 1:
                return "Rock";
            case 2:
                return "Paper";
            case 3:
                return "Scissors";
            default:
                return "Invalid";
        }
    }

    static void records(char choice, int mode, String name1, String name2, int p1, int p2, int comp) {
        createFile();
        if (choice == 'Y' || choice == 'y') {
            if (mode == 1) {
                file(p1, comp);
                String result = (comp == p1) ? "DRAW" : (p1 == 2 && comp == 1 || p1 == 3 && comp == 2 || p1 == 1 && comp == 3) ? "You Win" : "Computer Wins";
                insertRecord("Computer", "You", "Computer", choiceToString(p1), choiceToString(comp), result);
            } else if (mode == 2) {
                file(name1, name2, p1, p2);
                String result = (p2 == p1) ? "DRAW" : (p1 == 2 && p2 == 1 || p1 == 3 && p2 == 2 || p1 == 1 && p2 == 3) ? name1 + " Wins" : name2 + " Wins";
                insertRecord("2 Player", name1, name2, choiceToString(p1), choiceToString(p2), result);
            }

        }
    }

    static void showData() {
        String sql = "SELECT * FROM game_records";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Game Records from Database:");
            while (rs.next()) {
                System.out.println("Mode: " + rs.getString("mode"));
                System.out.println("Player 1: " + rs.getString("player1_name"));
                System.out.println("Player 2: " + rs.getString("player2_name"));
                System.out.println("Player 1 Choice: " + rs.getString("player1_choice"));
                System.out.println("Player 2 Choice: " + rs.getString("player2_choice"));
                System.out.println("Result: " + rs.getString("result"));
                System.out.println("Timestamp: " + rs.getTimestamp("timestamp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void exit() {
        disconnect();
        System.exit(0);
    }
}
