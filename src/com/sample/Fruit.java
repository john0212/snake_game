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
public class Fruit {

    // 代表這個果實出現的位置
    private int x;
    private int y;

    public Fruit() {
        this.x = (int) (Math.floor(Math.random() * MainClass.column) * MainClass.CELL_SIZE);
        this.y = (int) (Math.floor(Math.random() * MainClass.row) * MainClass.CELL_SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void drawFruit(Graphics g) {
        g.setColor(Color.ORANGE);
        // 因為 x y 是隨機設定的，所以每次重啟位置都會不一樣
        g.fillOval(this.x, this.y, MainClass.CELL_SIZE, MainClass.CELL_SIZE);
    }

    public void setNewLocation(Snake s) {

        int new_x;
        int new_y;
        boolean overlapping; // 這個就是要確認新的水果會不會重疊在蛇的身上
        do {
            new_x = (int) (Math.floor(Math.random() * MainClass.column) * MainClass.CELL_SIZE);
            new_y = (int) (Math.floor(Math.random() * MainClass.row) * MainClass.CELL_SIZE);
            overlapping = check_overlap(new_x, new_y, s);
        } while (overlapping);
        
        this.x = new_x;
        this.y = new_y;
    }

    public boolean check_overlap(int x, int y, Snake s) {
        ArrayList<Node> snakeBody = s.getSnakeBody();
        for (int j = 0; j < s.getSnakeBody().size(); j++) {
            if (x == snakeBody.get(j).x && y == snakeBody.get(j).y) {
                return true;
            }
        }
        return false;
    }
}
