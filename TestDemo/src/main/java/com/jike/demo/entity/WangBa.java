package com.jike.demo.entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author qukun
 * @date 2019/7/9
 */
public class WangBa {
    public static void main(String[] args) {
        JFrame w=new JFrame("乌龟游啊游");
        w.setSize(1720, 768);
        w.setBackground(Color.LIGHT_GRAY);
        Mypan mp=new Mypan();
        w.add(mp);

        w.addMouseListener(mp);
        w.addMouseMotionListener(mp);

        w.addKeyListener(mp);
        mp.addKeyListener(mp);

        Thread ti=new Thread(mp);
        ti.start();
        w.show();
    }

}
