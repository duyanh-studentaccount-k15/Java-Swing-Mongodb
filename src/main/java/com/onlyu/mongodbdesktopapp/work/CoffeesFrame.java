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
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.*;
import org.bson.Document;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


@SuppressWarnings({"unused", "FieldCanBeLocal", "unchecked", "rawtypes"})
public class CoffeesFrame extends JFrame implements RowSetListener {

    private static int countTempRecords = 0;
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection collection;
    private MongoCredential credential;
    private MongoClientURI uri;
    private CoffeesFrame() {

        super("The Coffee Break: 'coffee' Table"); // Set window title
        setDefaultLookAndFeelDecorated(true);



        // Close connections exit the application when the user
        // closes the window

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    client.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        });

        final JTable table = new JTable(new CoffeesTableModel()); // Displays the table

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

        button_ADD_ROW.addActionListener(e -> {

            JOptionPane.showMessageDialog(CoffeesFrame.this,
                    new String[]{
                            "Adding the following row:",
                            "Coffee name: [" + textField_COF_NAME.getText() + "]",
                            "Supplier ID: [" + textField_SUP_ID.getText() + "]",
                            "Price: [" + textField_PRICE.getText() + "]",
                            "Sales: [" + textField_SALES.getText() + "]",
                            "Total: [" + textField_TOTAL.getText() + "]"});


            try {

                CoffeesTableModel model = (CoffeesTableModel) table.getModel();
                model.insertRow(textField_COF_NAME.getText().trim(),
                        textField_SUP_ID.getText().trim(),
                        textField_PRICE.getText().trim(),
                        textField_SALES.getText().trim(),
                        textField_TOTAL.getText().trim());
                countTempRecords += 1;

            } catch (Exception e1) {
                displaySQLExceptionDialog(e1);
            }
        });

        button_UPDATE_DATABASE.addActionListener(e -> {
            try {
                //Thread.sleep(1);
                CoffeesTableModel model = (CoffeesTableModel) table.getModel();
                List<Coffee> data = model.getData();
                int numOfRecords = data.size();
                List<Document> documents = new LinkedList<>();
                for (int i = numOfRecords - countTempRecords; i < numOfRecords; i++)
                    documents.add(toDocument(data.get(i)));
                collection.insertMany(documents);
                countTempRecords = 0; // reset
                JOptionPane.showMessageDialog(this, "Insert success!", "Success!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                table.setModel(new CoffeesTableModel());
                displaySQLExceptionDialog(e1);
            }
        });

        button_DISCARD_CHANGES.addActionListener(e -> {
            try {
                table.setModel(new CoffeesTableModel());
                countTempRecords = 0;
            } catch (Exception e1) {
                displaySQLExceptionDialog(e1);
            }
        });
    }

    private static Coffee toCoffee(Document document) {
        return new Coffee(
                document.getString("name"),
                document.getInteger("supId"),
                document.getDouble("price"),
                document.getInteger("sales"),
                document.getInteger("total")
        );
    }

    private static Document toDocument(Coffee coffee) {
        return new Document()
                .append("name", coffee.name)
                .append("supId", coffee.supId)
                .append("price", coffee.price)
                .append("sales", coffee.sales)
                .append("total", coffee.total)
                ;
    }

    @Override
    public void rowSetChanged(RowSetEvent event) {

    }

    @Override
    public void rowChanged(RowSetEvent event) {

    }

    private void displaySQLExceptionDialog(Exception e) {

        // Display the SQLException in a dialog box
        JOptionPane.showMessageDialog(
                CoffeesFrame.this,
                new String[]{
                        e.getClass().getName() + ": ",
                        e.getMessage()
                }
        );
    }


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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    private static class Coffee {

        private String name;
        private int supId;
        private double price;
        private int sales;
        private int total;

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

    @SuppressWarnings({"unused", "FieldCanBeLocal", "rawTypes"})
    @Getter
    private class CoffeesTableModel extends AbstractTableModel {

        private String[] columnNames = {"COF_NAME", "SUP_ID", "PRICE", "SALES", "TOTAL"};
        private List<Coffee> data = new LinkedList<>();
        private int numOfRow;

//        private Object[][] data2 = {
//            {"Colombian", 101, 7.99, 0, 0},
//            {"Colombian_Decaf", 101, 8.99, 0, 0},
//            {"Espresso", 150, 9.99, 0, 0},
//            {"French_Roast", 49, 8.99, 0, 0},
//            {"French_Roast_Decaf", 49, 9.99, 0, 0}
//        };

        private CoffeesTableModel() {
            super();
//            data.add(new Coffee("Colombian", 101, 7.99, 0, 0));
//            data.add(new Coffee("Colombian_Decaf", 101, 8.99, 0, 0));
//            data.add(new Coffee("Espresso", 150, 9.99, 0, 0));
//            data.add(new Coffee("French_Roast", 49, 8.99, 0, 0));
//            data.add(new Coffee("French_Roast_Decaf", 49, 9.99, 0, 0));
            try {
                String host = System.getenv("DNS_MONGODB");

                uri = new MongoClientURI("mongodb://onlyu:kinlove32@" + host + ":27097/?authSource=coffee-shop&ssl=false&authMode=scram-sha1");
                client = new MongoClient(uri);
                Thread.sleep(1500);

//                client = new MongoClient(host , 27097);
//                credential = MongoCredential.createCredential("onlyu", "coffee-shop", "kinlove32".toCharArray());

                database = client.getDatabase("coffee-shop");
//                database.createCollection("coffees", null);
                collection = database.getCollection("coffees");
                FindIterable<Document> documents = collection.find();

                System.out.println("Retrieving documents...");
                for (Document document : documents) {
                    Coffee coffee = toCoffee(document);
                    data.add(coffee);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            numOfRow = data.size();
        }

        private void insertRow(String name, String supId, String price, String sales, String total) {
            data.add(new Coffee(name, Integer.parseInt(supId), Double.parseDouble(price), Integer.parseInt(sales), Integer.parseInt(total)));
            setValueAt(name, data.size() - 1, 0);
            setValueAt(supId, data.size() - 1, 1);
            setValueAt(price, data.size() - 1, 2);
            setValueAt(sales, data.size() - 1, 3);
            setValueAt(total, data.size() - 1, 4);
            numOfRow++;
            fireTableDataChanged();
        }

        public void insertRow(Coffee coffee) {
            data.add(coffee);
            setValueAt(coffee.name, data.size() - 1, 0);
            setValueAt(coffee.supId, data.size() - 1, 1);
            setValueAt(coffee.price, data.size() - 1, 2);
            setValueAt(coffee.sales, data.size() - 1, 3);
            setValueAt(coffee.total, data.size() - 1, 4);
            numOfRow++;
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }

        @Override
        public int getRowCount() {
            return numOfRow;
            //return data2.length;
            //return (data != null) ? data.size() : 0;
        }

        @Override
        public int getColumnCount() {
            return 5;
            //return columnNames.length;
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
            Coffee coffee = data.get(row);
            switch (col) {
                case 0:
                    return coffee.getName();
                case 1:
                    return coffee.getSupId();
                case 2:
                    return coffee.getPrice();
                case 3:
                    return coffee.getSales();
                case 4:
                    return coffee.getTotal();
            }
            return "";
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            Coffee coffee = data.get(row);
            switch (col) {
                case 0:
                    coffee.setName((String) value);
                    break;
                case 1:
                    coffee.setSupId(Integer.parseInt((String) value));
                    break;
                case 2:
                    coffee.setPrice(Double.parseDouble((String) value));
                    break;
                case 3:
                    coffee.setSales(Integer.parseInt((String) value));
                    break;
                case 4:
                    coffee.setTotal(Integer.parseInt((String) value));
                    break;
            }
            //data2[row][col] = value;
            fireTableCellUpdated(row, col);
        }

    }

}
