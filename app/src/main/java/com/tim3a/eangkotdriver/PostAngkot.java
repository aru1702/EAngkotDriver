package com.tim3a.eangkotdriver;

public class PostAngkot {
    public String plate_num, pool;

    public PostAngkot(String plate_num, String pool) {
        this.plate_num = plate_num;
        this.pool = pool;
    }

    public String getPlate_num() {
        return plate_num;
    }

    public void setPlate_num(String plate_num) {
        this.plate_num = plate_num;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }
}
