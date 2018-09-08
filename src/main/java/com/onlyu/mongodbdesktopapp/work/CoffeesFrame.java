/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.onlyu.mongodbdesktopapp.work;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.*;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;


@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class CoffeesFrame extends JFrame implements RowSetListener {

    private CoffeesFrame() {

        super("The Coffee Break: 'coffee' Table"); // Set window title
        setDefaultLookAndFeelDecorated(true);

        try {
            String host = System.getenv("DNS_MONGODB");
            MongoClientURI uri = new MongoClientURI("mongodb://onlyu:kinlove32@" + host + ":27097/?authSource=coffee-shop&ssl=false");
            mongoClient = new MongoClient(uri);
            database = mongoClient.getDatabase("coffee-shop");
            collection = database.getCollection("coffee");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Close connections exit the application when the user
        // closes the window

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    mongoClient.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        });

        table = new JTable(new CoffeesTableModel()); // Displays the table

        label_COF_NAME = new JLabel();
        label_SUP_ID = new JLabel();
        label_PRICE = new JLabel();
        label_SALES = new JLabel();
        label_TOTAL = new JLabel();

        textField_COF_NAME = new JTextField(10);
        textField_SUP_ID = new JTextField(10);
        textField_PRICE = new JTextField(10);
        textField_SALES = new JTextField(10);
        textField_TOTAL = new JTextField(10);

        button_ADD_ROW = new JButton();
        button_UPDATE_DATABASE = new JButton();
        button_DISCARD_CHANGES = new JButton();

        label_COF_NAME.setText("Coffee Name:");
        label_SUP_ID.setText("Supplier ID:");
        label_PRICE.setText("Price:");
        label_SALES.setText("Sales:");
        label_TOTAL.setText("Total Sales:");

        textField_COF_NAME.setText("Enter new coffee name");
        textField_SUP_ID.setText("101");
        textField_PRICE.setText("0");
        textField_SALES.setText("0");
        textField_TOTAL.setText("0");

        button_ADD_ROW.setText("Add row to table");
        button_UPDATE_DATABASE.setText("Update database");
        button_DISCARD_CHANGES.setText("Discard changes");

        // Place the components within the container contentPane; use GridBagLayout
        // as the layout.

        Container contentPane = getContentPane();
        contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        contentPane.add(new JScrollPane(table), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.25;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        contentPane.add(label_COF_NAME, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.75;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        contentPane.add(textField_COF_NAME, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.25;
        c.weighty = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        contentPane.add(label_SUP_ID, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.75;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        contentPane.add(textField_SUP_ID, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.25;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        contentPane.add(label_PRICE, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.75;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        contentPane.add(textField_PRICE, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.25;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        contentPane.add(label_SALES, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.75;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        contentPane.add(textField_SALES, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.25;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        contentPane.add(label_TOTAL, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.75;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        contentPane.add(textField_TOTAL, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.5;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        contentPane.add(button_ADD_ROW, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        contentPane.add(button_UPDATE_DATABASE, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.5;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        contentPane.add(button_DISCARD_CHANGES, c);

        // Add listeners for the buttons in the application

        button_ADD_ROW.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(CoffeesFrame.this,
                        new String[]{
                                "Adding the following row:",
                                "Coffee name: [" + textField_COF_NAME.getText() + "]",
                                "Supplier ID: [" + textField_SUP_ID.getText() + "]",
                                "Price: [" + textField_PRICE.getText() + "]",
                                "Sales: [" + textField_SALES.getText() + "]",
                                "Total: [" + textField_TOTAL.getText() + "]"});


//          try {
//
//            myCoffeesTableModel.insertRow(textField_COF_NAME.getText(),
//                                          Integer.parseInt(textField_SUP_ID.getText().trim()),
//                                          Float.parseFloat(textField_PRICE.getText().trim()),
//                                          Integer.parseInt(textField_SALES.getText().trim()),
//                                          Integer.parseInt(textField_TOTAL.getText().trim()));
//          } catch (SQLException sqle) {
//            displaySQLExceptionDialog(sqle);
//          }
            }
        });

        button_UPDATE_DATABASE.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//          try {
//            //myCoffeesTableModel.coffeesRowSet.acceptChanges();
//          } catch (SQLException sqle) {
//            displaySQLExceptionDialog(sqle);
//            // Now revert back changes
//            try {
//              createNewTableModel();
//            } catch (SQLException sqle2) {
//              displaySQLExceptionDialog(sqle2);
//            }
//          }
            }
        });

        button_DISCARD_CHANGES.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                try {
//                    createNewTableModel();
//                } catch (SQLException sqle) {
//                    displaySQLExceptionDialog(sqle);
//                }
            }
        });
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    private class Coffee {

        private String name;
        private int supId;
        private double price;
        private int sales;
        private double total;

    }

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection collection;
    private JTable table; // The table for displaying data
    private JLabel label_COF_NAME;
    private JLabel label_SUP_ID;
    private JLabel label_PRICE;
    private JLabel label_SALES;
    private JLabel label_TOTAL;
    private JTextField textField_COF_NAME;
    private JTextField textField_SUP_ID;
    private JTextField textField_PRICE;
    private JTextField textField_SALES;
    private JTextField textField_TOTAL;
    private JButton button_ADD_ROW;
    private JButton button_UPDATE_DATABASE;
    private JButton button_DISCARD_CHANGES;

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    @Getter
    private class CoffeesTableModel extends AbstractTableModel {

        private boolean DEBUG = false;
        private String[] columnNames = {"COF_NAME", "SUP_ID", "PRICE", "SALES", "TOTAL"};


        private Object[][] data = {
                {"Colombian", 101, 7.99, 0, 0},
                {"Colombian_Decaf", 101, 8.99, 0, 0},
                {"Espresso", 150, 9.99, 0, 0},
                {"French_Roast", 49, 8.99, 0, 0},
                {"French_Roast_Decaf", 49, 9.99, 0, 0}
        };

        @Override
        public boolean isCellEditable(int row, int col) {
            return col >= 2;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value + " (an instance of "
                        + value.getClass() + ")");
            }

            data[row][col] = value;
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j = 0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }

    }

    public static void main(String[] args) {

        try {
            CoffeesFrame qf = new CoffeesFrame();
            qf.pack();
            qf.setVisible(true);
        } catch (Exception e) {
            System.out.println("Unexpected exception");
            e.printStackTrace();
        }
    }

    @Override
    public void cursorMoved(RowSetEvent event) {

    }

    private void displaySQLExceptionDialog(SQLException e) {

        // Display the SQLException in a dialog box
        JOptionPane.showMessageDialog(
                CoffeesFrame.this,
                new String[]{
                        e.getClass().getName() + ": ",
                        e.getMessage()
                }
        );
    }

    @Override
    public void rowSetChanged(RowSetEvent event) {

    }

    public void rowChanged(RowSetEvent event) {

//    CachedRowSet currentRowSet = this.myCoffeesTableModel.coffeesRowSet;
//
//    try {
//      currentRowSet.moveToCurrentRow();
//      myCoffeesTableModel =
//        new CoffeesTableModel(myCoffeesTableModel.getCoffeesRowSet());
//      table.setModel(myCoffeesTableModel);
//
//    } catch (SQLException ex) {
//
//      ex.printStackTrace();
//
//      // Display the error in a dialog box.
//
//      JOptionPane.showMessageDialog(
//        CoffeesFrame.this,
//        new String[] { // Display a 2-line message
//          ex.getClass().getName() + ": ",
//          ex.getMessage()
//        }
//      );
//    }

    }


}
