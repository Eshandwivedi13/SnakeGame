import javax.swing.*;
import java.awt.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class SnakeGame extends JFrame {//Jframe class creates a window/frame, inside that window our application will will be contained
    Board board;//intializing object of Board class
    SnakeGame(){
        board = new Board();//ab object create hi kar diya yaha par
        add(board);//frame will be adjusted to this board size with help of pack function
        pack();//basically it packs the fram, particular compenent with children component -> it will make the frame resize to the board dimension
        setResizable(false);//it should not be resizable by the user
        setVisible(true);//visible karna padega na jaise frame krte the, ye frame ke andar container h, behaving like a jframe
//       setLocation(SOMEBITS, Toolkit.getDefaultToolkit().getScreenSize().height - 400);
        //        setLocation(500, 200);
        setLocationRelativeTo(null);
//        board.setState(Frame.NORMAL);
        setTitle("Snake");


    }

    public static void main(String[] args) {
    /*    //         Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
        for (int i = 1; i <= 5; i++) {

            // Press Shift+F9 to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Ctrl+F8.
            System.out.println("i = " + i);
        }*/

        //initialize snake game->
        SnakeGame snakeGame = new SnakeGame();
    }
}