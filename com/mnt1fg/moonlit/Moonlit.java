/**
 * Moonlit Project
 * @author Manato1fg
 * 
 * This is a library written in Java which enables you to make canvas easily.
 * This project is inspired by The Coding Train.
 * 
 * MIT License
 * 
 * Copyright (c) 2019 Manato

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.mnt1fg.moonlit;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Moonlit extends JFrame{
    private static Moonlit instance = null;
    private MoonlitPanel panel = null;
    //how many times onUpdate() is called.
    private int ticks = 10;
    //background color
    private Color backgroundColor = Color.white;
    private int width, height;
    private boolean setupOk = false;

    public static Moonlit getInstance() {

        if (instance == null) {
            instance = new Moonlit();
        }

        return instance;
    }

    /**
     * register class to let its onUpdate() be called.
     * 
     * @param cls must implements MoonlitInterface
     */
    public void register(MoonlitInterface cls) {
        this.panel.register(cls);
    }


    public void createWindow(int width, int height) {
        this.setSize(width, height);
        this.width = width;
        this.height = height;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.panel = new MoonlitPanel();
        this.add(this.panel);
        setupOk = true;
    }

    public void showWindow(){
        if( ! setupOk ) {
            Moonlit.log("you must call createWindow method first.");
            System.exit(0);
        }
        this.setVisible(true);
        final int _ticks = this.ticks;
        final MoonlitPanel _panel = this.panel;
        new Thread(new Runnable(){
            @Override
            public void run(){
                while (true) {
                    try {
                        _panel.repaint();
                        Thread.sleep((int) (1000 / _ticks));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void setTicks(int ticks){
        this.ticks = ticks;
    }

    /**
     * draw functions
     */

    public void setColor(Graphics _g, int r, int g, int b) {
        _g.setColor(new Color(r, g, b));
    }

    public void drawRectangle(Graphics g, int x, int y, int width, int height) {
        g.drawRect(x, y, width, height);
    }

    public void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
    }

    public void fillRect(Graphics g, int x, int y, int width, int height){
        g.fillRect(x, y, width, height);
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * utilities
     */

    public static void log(String str){
        System.out.println("[Moonlit] "+str);
    }

    private class MoonlitPanel extends JPanel{

        private ArrayList<MoonlitInterface> updateClasses = new ArrayList<MoonlitInterface>();

        public void register(MoonlitInterface cls) {
            this.updateClasses.add(cls);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Moonlit.getInstance().getBackgroundColor());
            g.fillRect(0, 0, Moonlit.getInstance().getWidth(), Moonlit.getInstance().getHeight());
            this.updateClasses.forEach(c -> c.onUpdate(g));
        }

    }
}
