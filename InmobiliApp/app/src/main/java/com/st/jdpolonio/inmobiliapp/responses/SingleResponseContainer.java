package com.st.jdpolonio.inmobiliapp.responses;

import com.st.jdpolonio.inmobiliapp.models.Rows;

public class SingleResponseContainer {

    private int count;
    private Rows rows;

    public SingleResponseContainer() {   }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Rows getRows() {
        return rows;
    }

    public void setRows(Rows rows) {
        this.rows = rows;
    }
}
