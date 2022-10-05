/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.sample;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class MainClass extends JPanel implements KeyListener {

    public static final int CELL_SIZE = 20; // 每一格的大小
    public static int width = 400;
    public static int height = 400;

    public static int row = height / CELL_SIZE; // 橫列總共的格子
    public static int column = width / CELL_SIZE; // 直列總共的格子
    private Snake snake;
    private Fruit fruit;
    private int speed = 100;

    private Timer t;
    private static String direction; // 代表要跑的方向在哪裡
    private boolean allowKeyPress; // 可以看 no.82的影片，為了避免你按住一個鍵後，在案另一個鍵就能改變方向

    private int score;
    private int highest_score;
    public int response;

    public MainClass() {
        read_highest_score();
        reset();
        addKeyListener(this);
    }

    private void setTimer() {
        t = new Timer();
        // 代表在固定的時間會讓這個 timer 去執行一件事情
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint(); // 這裡就會去重新執行 paintComponent()
            }
        }, 0, speed); // 0 代表 delay 的值，speed 是代表幾秒(毫秒)執行一次

    }

    private void reset() {
        score = 0;
        if (snake != null) {
            snake.getSnakeBody().clear();
        }
        allowKeyPress = true;
        direction = "Right";
        snake = new Snake();
        fruit = new Fruit();
        setTimer();
    }

    @Override
    public void paintComponent(Graphics g) {
        // check if the snake bites itself
        ArrayList<Node> snake_body = snake.getSnakeBody();
        Node head = snake_body.get(0);
        for (int i = 1; i < snake_body.size(); i++) {
            if (snake_body.get(i).x == head.x && snake_body.get(i).y == head.y) {
                allowKeyPress = false;
                // 這兩行會強制讓遊戲暫停
                t.cancel();
                t.purge();
                showMessage();
            }
        }

        // draw a black background
        g.fillRect(0, 0, width, height);
        fruit.drawFruit(g);
        snake.drawSnake(g);

        // remove snake tail and put in in head
        int snakeX = snake.getSnakeBody().get(0).x;
        int snakeY = snake.getSnakeBody().get(0).y;
        // right, x += CELL_SIZE
        // left, x -= CELL_SIZE
        // down, y += CELL_SIZE
        // up, y -= CELL_SIZE
        if (direction.equals("Left")) {
            snakeX -= CELL_SIZE;
        } else if (direction.equals("Up")) {
            snakeY -= CELL_SIZE;
        } else if (direction.equals("Down")) {
            snakeY += CELL_SIZE;
        } else if (direction.equals("Right")) {
            snakeX += CELL_SIZE;
        }
        Node newHead = new Node(snakeX, snakeY);

        // check if the snake eats the fruit
        if (snake.getSnakeBody().get(0).x == fruit.getX() && snake.getSnakeBody().get(0).y == fruit.getY()) {
            // 1. set fruit to a new location
            fruit.setNewLocation(snake);
            // 2. drawFruit
            fruit.drawFruit(g);
            // 3. score++
            score++;
        } else {
            snake.getSnakeBody().remove(snake.getSnakeBody().size() - 1);
        }
        snake.getSnakeBody().add(0, newHead);

        allowKeyPress = true;
        requestFocusInWindow();

    }

    // 這樣視窗出現時會出現在螢幕正中央，並且是 400 * 400 的大小
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame window = new JFrame("Snake Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new MainClass());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false); // 代表視窗打開後是沒有辦法調整大小的
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());

        if (allowKeyPress) {
            // 左 -> 37 , 上 -> 38 , 右 -> 39 , 下 -> 40
            if (e.getKeyCode() == 37 && !direction.equals("Right")) {
                direction = "Left";
            } else if (e.getKeyCode() == 38 && !direction.equals("Down")) {
                direction = "Up";
            } else if (e.getKeyCode() == 39 && !direction.equals("Left")) {
                direction = "Right";
            } else if (e.getKeyCode() == 40 && !direction.equals("Up")) {
                direction = "Down";
            }
            allowKeyPress = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void read_highest_score() {
        try {
            File myobj = new File("filename.txt");
            Scanner myReader = new Scanner(myobj);
            highest_score = myReader.nextInt();
            myReader.close();
        } catch (FileNotFoundException e) {
            highest_score = 0;
            try {
                File myobj = new File("filename.txt");
                if (myobj.createNewFile()) {
                    System.out.println("File created: " + myobj.getName());
                }
                FileWriter myWriter = new FileWriter(myobj.getName());
                myWriter.write("" + 0);
            } catch (IOException ex) {
                System.out.println("An error occurred.");
                ex.printStackTrace();
            }
        }
    }

    public void write_a_file(int score) {
        try {
            FileWriter myWriter = new FileWriter("filename.txt");
            if (score > highest_score) {
                myWriter.write("" + score);
                highest_score = score;
            } else {
                myWriter.write("" + highest_score);
            }
            myWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void writeAfile() {
        write_a_file(score);
        switch (response) {
            case JOptionPane.CLOSED_OPTION:
                System.exit(0);
                break;
            case JOptionPane.NO_OPTION:
                System.exit(0);
                break;
            case JOptionPane.YES_OPTION:
                reset();
                return;
        }
    }

    public void showMessage() {
        if (score < highest_score | score == highest_score) {
            response = JOptionPane.showOptionDialog(this, "Game Over! Your score is " + score + ". The highest score was " + highest_score + ". Would you want to try again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, JOptionPane.YES_OPTION);
            writeAfile();
        } else {
            response = JOptionPane.showOptionDialog(this, "Game Over! Your score is " + score + ". Congratulations you broke the record. Would you want to try again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, JOptionPane.YES_OPTION);
            writeAfile();
        }
    }
}
