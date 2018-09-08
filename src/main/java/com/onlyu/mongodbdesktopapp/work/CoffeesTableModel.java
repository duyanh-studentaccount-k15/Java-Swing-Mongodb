package com.onlyu.mongodbdesktopapp.work;

import lombok.Getter;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Getter
public class CoffeesTableModel extends AbstractTableModel {

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
