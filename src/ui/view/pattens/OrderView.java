/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.pattens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Edward Jenkins
 */
public class OrderView extends JPanel {
    
    // instance variables
    private int modType;
    private short[] orders;
    private JList<String> orderList;
    
    // constructor
    public OrderView(int modType, short[] orders) {
        this.modType = modType;
        this.orders = orders;
        init();
    }
    
    // getters
    
    public void init() {
        setLayout(new BorderLayout());
        orderList = new JList<>();
        String[] listData = new String[orders.length];
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == 255) {
                listData[i] = "---";
            } else if (orders[i] == 254) {
                listData[i] = "+++";
            } else {
                listData[i] = Short.toString(orders[i]);
            }
        }
        orderList.setListData(listData);
        add(orderList, BorderLayout.CENTER);
    }
}
