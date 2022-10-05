/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sample;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Snake {

    private ArrayList<Node> snakeBody;

    public Snake() {
        snakeBody = new ArrayList<>();
        snakeBody.add(new Node(80, 0));
        snakeBody.add(new Node(60, 0));
        snakeBody.add(new Node(40, 0));
        snakeBody.add(new Node(20, 0));
    }

    public ArrayList<Node> getSnakeBody() {
        return snakeBody;
    }

    public void drawSnake(Graphics g) {

        for (int i = 0; i < snakeBody.size(); i++) {
            if (i == 0) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.red);
            }
            Node n = snakeBody.get(i);
            if (n.x >= MainClass.width) {
                n.x = 0;
            }
            if (n.x < 0) {
                n.x = MainClass.width - MainClass.CELL_SIZE;
            }
            if (n.y >= MainClass.height) {
                n.y = 0;
            }
            if (n.y < 0) {
                n.y = MainClass.height - MainClass.CELL_SIZE;
            }
            g.fillOval(n.x, n.y, MainClass.CELL_SIZE, MainClass.CELL_SIZE);
        }
    }
}
