package org.telosys.history;

import java.util.Date;
import java.util.Map;

public class ChartPoint {
    private Date x;
    private Number y;

    public ChartPoint(Date x, Number y) {
        this.x = x;
        this.y = y;
    }

    public Date getX() {
        return x;
    }

    public Number getY() {
        return y;
    }
}
