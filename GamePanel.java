import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private int[] snake;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private int moves = 0;
    private int score = 0;

    private boolean gameOver = false;

    private int[] xPos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] yPos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    private Random random = new Random();
    private int enemyX, enemyY;

    private int[] snakeX = new int[750];
    private int[] snakeY = new int[750];
    private int lengthSnake = 3;
    private ImageIcon snaketitle = new ImageIcon( getClass().getResource("snaketitle.jpg"));
    private ImageIcon leftmouth = new ImageIcon( getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth = new ImageIcon( getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth = new ImageIcon( getClass().getResource("upmouth.png"));
    private ImageIcon downmouth = new ImageIcon( getClass().getResource("downmouth.png"));
    private ImageIcon snakeimage = new ImageIcon( getClass().getResource("snakeimage.png"));
    private ImageIcon enemy = new ImageIcon( getClass().getResource("enemy.png"));

    private Timer timer;
    private int delay = 100;
    GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay,this);
        timer.start();
        newEnemy();

    }

    private void newEnemy() {
        enemyX = xPos[random.nextInt(34)];
        enemyY = yPos[random.nextInt(23)];

        for(int i = lengthSnake -1 ; i >= 0 ; i-- )
        {
            if(snakeX[i] == enemyX && snakeY[i] == enemyY )
            {
                newEnemy();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.WHITE);
        g.drawRect(24,10,851,55);
        g.drawRect(24, 74, 851, 576);

        snaketitle.paintIcon(this, g, 25, 11);
        g.setColor(Color.BLACK);
        g.fillRect(25,75,850,575);

        if(moves == 0 )
        {
            snakeX[0] = 100;
            snakeX[1] = 75;
            snakeX[2] = 50;

            snakeY[0] = 100;
            snakeY[1] = 100;
            snakeY[2] = 100;
        }
        if(left)
        {
            leftmouth.paintIcon(this, g, snakeX[0], snakeY[0] );
        }
        if(right)
        {
            rightmouth.paintIcon(this, g, snakeX[0], snakeY[0] );
        }
        if(up)
        {
            upmouth.paintIcon(this, g, snakeX[0], snakeY[0] );
        }
        if(down)
        {
            downmouth.paintIcon(this, g, snakeX[0], snakeY[0] );
        }
        for(int i = 1 ; i < lengthSnake ; i++ )
        {
            snakeimage.paintIcon(this,g,snakeX[i], snakeY[i]);
        }
        enemy.paintIcon(this,g,enemyX,enemyY);

        if(gameOver){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD,50));
            g.drawString("Game Over", 300, 300);

            g.setFont(new Font("Arial", Font.PLAIN,20));
            g.drawString("Press SPACE to Restart", 320, 350);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN,14));
        g.drawString("Score :  " +score, 750, 30);
        g.drawString("Length :  " +lengthSnake, 750, 50);

        g.dispose();

        }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        for(int i = lengthSnake -1  ; i > 0 ; i-- )
        {
            snakeX[i] = snakeX[i-1];
            snakeY[i] = snakeY[i-1];
        }

        if(left)
        {
            snakeX[0] = snakeX[0] - 25;
        }
        if(right)
        {
            snakeX[0] = snakeX[0] + 25;
        }
        if(up)
        {
            snakeY[0] = snakeY[0] - 25;
        }
        if(down)
        {
            snakeY[0] = snakeY[0] + 25;
        }

        if(snakeX[0] > 850) snakeX[0] = 25;
        if(snakeX[0] < 25 ) snakeX[0] = 850;

        if(snakeY[0] > 625) snakeY[0] = 75;
        if(snakeY[0] < 75 ) snakeY[0] = 625;

        collide();
        selfeat();

        repaint();
    }

    private void selfeat() {
        for(int i = lengthSnake-1 ; i > 0 ; i-- )
        {
            if(snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0] )
            {
                timer.stop();
                gameOver = true;
            }
        }
    }

    private void collide() {
        if(snakeX[0] == enemyX && snakeY[0] == enemyY )
        {
            newEnemy();
            lengthSnake++;
            score++;


        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_SPACE )
        {
            restart();
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT && (!right))
        {
            left = true;
            right = false;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && (!left))
        {
            left = false;
            right = true;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP && (!down))
        {
            left = false;
            right = false;
            up = true;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN && (!up))
        {
            left = false;
            right = false;
            up = false;
            down = true;
            moves++;
        }

    }

    private void restart() {
        gameOver = false;
        moves= 0;
        score = 0;
        lengthSnake = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
