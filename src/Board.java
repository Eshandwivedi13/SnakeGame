import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Board extends JPanel implements ActionListener {//like a container inside frame
    int B_Height = 600;//board initial height
    int B_WIDTH = 600;//board initial width

    int MAX_DOTS = 1600;
    int DOT_SIZE = 10;
    int DOTS;
    int x[] = new int[MAX_DOTS];
    int y[] = new int[MAX_DOTS];
    int apple_x;
    int apple_y;
    //images
    Image body,head,apple;
    Timer timer;
    int DELAY = 150;//map the timer to actual time
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;
    Boolean inGame = true;
    int score = 0;
    Board(){
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_Height));//first setting the size and creating dimension of that size
        setBackground(Color.BLACK);
        initGame();
        loadImages();
    }
    //initialize the coordinates of game
    public void initGame(){
        DOTS = 3;//dot initiliaze yaha kar rhe hai
        //initialize snake's position
        x[0] = 250;
        y[0] = 250;
        for(int i=0;i<DOTS;i++){
            x[i] = x[0] + DOT_SIZE * i;
            y[i] = y[0];
        }
        //initliaze apple -> later by randemizing see below
        locateApple();
        //timer
        timer = new Timer(DELAY, this);//delay, actionlistenor -> we whenever an action event occurs, we tell the compiler using action performed method, that we need to move
        timer.start();
    }
    //load these images from resources into Image Object written above

    public void loadImages(){
        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");//storing .img in bodyIcon imageIcon
        //jo bhi image object banaya hai usse image icon ko store karna hai
        body = bodyIcon.getImage();//we get the image from the body icon

        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();

        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();
    }
    //draw images at particular position at snakes and apple's position
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);//copy the original definition of paint component method (Paint component is a method inside jpanel)
        doDrawing(g);

    }
    //method for draw image
    public void doDrawing(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);//observer is our board object, this will be drawn in jpanel so thats why we are providing the observer
            for(int i=0;i<DOTS;i++){
                if(i==0){// it means the head
                    g.drawImage(head,x[0],y[0], this);
                }else{
                    g.drawImage(body,x[i],y[i], this);//body image
                }

            }
        }else{
            gameOver(g);
            timer.stop();
        }
    }
    //randomize apple position now, upar likha tha neche karenge
    public void locateApple(){

        apple_x = ((int)(Math.random()*39)) * DOT_SIZE;//math.random -> it basically generates a random number from 0 to 1, we will go to 39 at max 390 tak ja sakte hai
        apple_y = ((int)(Math.random()*39)) * DOT_SIZE;
    }

    //check collisions with border and body
    public void checkCollision(){
        //collision with body
        for(int i=1;i<DOTS;i++){
            if(i>4 && x[0] == x[i] && y[0] == y[i]){//agar aisa ho rha hai to colide ho rha hai apas mei
                inGame = false;
            }
        }
        //collision with border
        if(x[0] < 0){
            inGame = false;
        }
        if(x[0] >= B_WIDTH){
            inGame = false;
        }
        if(y[0] < 0){
            inGame = false;
        }
        if(y[0] >=  B_Height){
            inGame = false;
        }
    }
    //Display game over mesg
    public void gameOver(Graphics g){
        String msg = "Game Over";
        score = (DOTS -3) * 100; // score hai, intially 3 dots to -3
        String scoremsg = "Score:" + Integer.toString(score);
        String restart = "Press SPACE to Restart";
        Font small = new Font("Helvetica", Font.BOLD,14);
        FontMetrics fontMetrics = getFontMetrics(small);//it basically gets the size of msg
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(B_WIDTH-fontMetrics.stringWidth(msg))/2 , B_Height/4);
        g.drawString(scoremsg,(B_WIDTH-fontMetrics.stringWidth(scoremsg))/2 , 2*(B_Height/4));
        g.drawString(restart,(B_WIDTH-fontMetrics.stringWidth(restart))/2 , 3*(B_Height/4));

    }
    //it is going to take some operation with actionEvent
    @Override
    public void actionPerformed(ActionEvent actionEvent){//whenever this action event generated we need to perform move function
        if(inGame){
            move();
            checkCollision();
            checkApple();
        }
        repaint();//sab repaint karo firse
    }
    //make snake move
    public void move(){
        //first change the direction of last dot then at last position of head will be updated taaki dikkat na ho
        for(int i=DOTS-1;i>=1;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection == true){
            x[0] -= DOT_SIZE;
        }
        if(rightDirection == true){
            x[0] += DOT_SIZE;
        }
        if(upDirection == true){
            y[0] -= DOT_SIZE;
        }
        if(downDirection == true){
            y[0] += DOT_SIZE;
        }
    }

    //implement controls
    private class TAdapter extends KeyAdapter {//implement controls
        @Override//we do customization by overRiding parent class method
        public void keyPressed(KeyEvent e) {//jab bhi button click karo karo to key event chalta hai, according to it perform some actions
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {//matlab space button dabayi hai
                restart();
            }
            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {//matlab left button dabayi hai
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {//matlab left button dabayi hai
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if ((key == KeyEvent.VK_UP) && (!downDirection)) {//matlab left button dabayi hai
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {//matlab left button dabayi hai
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }

        }
    }
    //make snake eat food
    public void checkApple(){
        if(apple_x == x[0] && apple_y == y[0]){//head pe hoga to kha lega
            DOTS++;//khake bada ho jayega
            locateApple();
        }
    }
    private void restart(){
        inGame = true;
//        addKeyListener(new TAdapter());
//        setFocusable(true);
//        setPreferredSize(new Dimension(B_WIDTH, B_Height));//first setting the size and creating dimension of that size
//        setBackground(Color.BLACK);
        initGame();
        loadImages();
//        move();
//        repaint();
//        checkApple();
        score = 0;
        DOTS = 3;
        DELAY = 150;
//       if(!leftDirection &&
//        !rightDirection &&
//        !upDirection){
//           downDirection = true;
//       }
//       else if(!leftDirection &&
//               !rightDirection &&
//               !downDirection){
//           upDirection = true;
//       }
//       else if(!upDirection &&
//               !rightDirection &&
//               !downDirection){
//           leftDirection = true;
//       }else{
//           rightDirection = true;
//       }
        timer.start();
    }
}
