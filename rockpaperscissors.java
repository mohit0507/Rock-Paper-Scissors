import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class rockpaperscissors {
    static int rp(){
        int p;
        do{
        System.out.println("1.Rock");
        System.out.println("2.Paper");
        System.out.println("3.Scissors");
        System.out.println("Enter your choice");
        Scanner ob=new Scanner(System.in);
        p=ob.nextInt();
        //ob.close();
        }while(invalid(p)==true);
    return p;}
    static void ch(int p){if (p==1){
        System.out.println("You chose Rock");
    }
    else if (p==2){
        System.out.println("You chose Paper");
    }
    else if(p==3){
        System.out.println("You chose Scissors");
    }
}
    static void ch(String name,int p){
        if (p==1){
            System.out.printf("%s chose Rock\n",name);
        }
        else if (p==2){
            System.out.printf("%s chose Paper\n",name);
        }
        else if(p==3){
            System.out.printf("%s chose Scissors\n",name);
        }
    }
    static boolean invalid(int p){
        if(p<1 || p>3){
        System.out.println("Enter a Valid Choice");
        //clear();
        return true;
    }
        return false;
    }
 static void time(){
    try{
   FileWriter fileWriter = new FileWriter("RPSgame.txt",true);
   BufferedWriter bw = new BufferedWriter(fileWriter);
   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
   LocalDateTime now = LocalDateTime.now();  
   bw.write("Played on "+dtf.format(now)+"\n\n");
   bw.close();
    }catch (IOException e) {
        e.printStackTrace();
    }
}
    static void exit(){
     System.out.println("\t    *****************\t\tThanks for Playing\t\t*****************");
     System.exit(0);
    }
    static void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    static void createfile(){
        File myFile = new File("RPSgame.txt");
        try {
            if (!myFile.exists()) {
                myFile.createNewFile();
                System.out.println("File created: "+ myFile.getName());
                FileWriter fileWriter = new FileWriter("RPSgame.txt",true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                // bw.write("\t    *****************\t\tROCK PAPER SCISSORS\t\t\t*****************\n");
                // bw.write("\t    *****************          GAME RECORDS\t\t\t\t*****************\n\n");
                bw.write("\t    *****************          ROCK PAPER SCISSORS              *****************\n");
                bw.write("\t    *****************             GAME RECORDS                  *****************\n\n");
                bw.close();
            }
            else {
                System.out.println("File already exists as "+myFile.getName()+" and is updated");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void file(String name1,String name2,int p1,int p2){
        String ch1="",ch2="";
        if(p1==1)
        ch1="Rock";
        else if(p1==2)
        ch1="Paper";
        else if(p1==3)
        ch1="Scissors";
        if(p2==1)
        ch2="Rock";
        else if(p2==2)
        ch2="Paper";
        else if(p2==3)
        ch2="Scissors";
        try{ 
            FileWriter fileWriter = new FileWriter("RPSgame.txt",true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write("2 Player Mode\n");
                bw.write("\n"+name1+" VS "+name2+"\n");
                bw.write("\n"+name1+" Chose "+ch1+"\n"+name2+" Chose "+ch2+"\n");
                if(p2 == p1){
                bw.write("DRAW\n");
            } else{
                if(p1 == 2 && p2 == 1 || p1 == 3 && p2 == 2 || p1 == 1 && p2 == 3){
                        bw.write(name1+" Wins\n");
                }else{
                    bw.write(name2+" Wins\n");
                }
        }
        bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        time();
    }
    static void file(int p1,int comp){
        String ch1="",ch2="";
        if(p1==1)
        ch1="Rock";
        else if(p1==2)
        ch1="Paper";
        else if(p1==3)
        ch1="Scissors";
        if(comp==1)
        ch2="Rock";
        else if(comp==2)
        ch2="Paper";
        else if(comp==3)
        ch2="Scissors";
        try{ 
            FileWriter fileWriter = new FileWriter("RPSgame.txt",true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write("Computer Mode\n");
                bw.write("\nYou"+" Chose "+ch1+"\n"+"Computer"+" Chose "+ch2+"\n");
                if(comp == p1){
                bw.write("DRAW\n");
            } else{
                if(p1 == 2 && comp == 1 || p1 == 3 && comp == 2 || p1 == 1 && comp == 3){
                        bw.write("You Win\n");
                }else{
                    bw.write("Computer Wins\n");
                }
        }
        bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        time();
    }
static void records(char ch,int mode,String name1,String name2,int p1,int p2,int comp){
if(ch=='Y' || ch=='y'){
createfile();
if(mode==1){
    file(p1,comp);
}
else if(mode==2){
    file(name1,name2,p1,p2);
}
}
    }
    //static void rules(){
    //}
    public static void main(String[] args) {
        int mode=0,p1=0,p2=0,comp=0;
        String name1="",name2="";
        char choice,ch;
        System.out.println("\t    *****************\t\t     WELCOME\t\t\t*****************");
        Scanner ob=new Scanner(System.in);
        do{
        do{
        System.out.println("\t    *****************\t\tROCK PAPER SCISSORS\t\t*****************");
        System.out.println("Rules:-");
        System.out.println("Rock Defeats Scissors");
        System.out.println("Paper Defeats Rock");
        System.out.println("Scissor Defeats Paper\n");
        //rules();
        System.out.println("Select the mode");
        System.out.println("1)Computer");
        System.out.println("2)2 Players");
        System.out.println("3)Exit the Game");
        mode=ob.nextInt();
    }while(invalid(mode)==true);
    clear();
        if(mode==1){
            p1=rp();
            ch(p1);
        Random random = new Random();
        comp=random.nextInt(3)+1;
        if (comp==1){
            System.out.println("Computer chose Rock");
        }
        else if (comp==2){
            System.out.println("Computer chose Paper");
        }
        else if(comp==3){
            System.out.println("Computer chose Scissors");
        }
        if(comp == p1){
            System.out.println("DRAW");
        } else{
            if(comp == 1 && p1 == 2 || comp == 2 && p1 == 3 || comp == 3 && p1 == 1){
                    System.out.println("You Win");
            }else{
                System.out.println("Computer Wins");
            }
        }
}
        
        else if(mode==2){
            System.out.println("Enter the name of Player 1");
            name1=ob.next();
            System.out.printf("%s's Turn\n",name1);
            p1=rp();
            clear();
            System.out.println("Enter the name of Player 2");
            name2=ob.next();
            System.out.printf("%s's Turn\n",name2);
            p2=rp();
            clear();
            ch(name1,p1);
            ch(name2,p2);
            if(p2 == p1){
                System.out.println("DRAW");
            } else{
                if(p1 == 2 && p2 == 1 || p1 == 3 && p2 == 2 || p1 == 1 && p2 == 3){
                        System.out.printf("%s Wins\n",name1);
                }else{
                    System.out.printf("%s Wins\n",name2);
                }
        }
    }
    else if(mode==3){
        exit();
    }
System.out.println("Store Game Record in a file? Y/N");
ch=ob.next().charAt(0);
records(ch,mode,name1,name2,p1,p2,comp);
    System.out.println("Play Again? Y/N");
    choice=ob.next().charAt(0);
    clear();
}while(choice=='Y' || choice=='y');
System.out.println("\t    *****************\t\t    GAME OVER\t\t\t*****************");
ob.close();
exit();
}
}

