package cn.com.onlinetool.jt809.jt;

public class Idc2AwsGpsVo {

    private int Direction;
    private int Lon;

    private Number Lat;

    private String name ;

    private Number  code;

    public void setLat(Number lat) {
        Lat = lat;
    }

    public Number getCode() {
        return code;
    }

    public void setCode(Number code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDirection() {
        return Direction;
    }

    public void setDirection(int direction) {
        Direction = direction;
    }

    public int getLon() {
        return Lon;
    }

    public void setLon(int lon) {
        Lon = lon;
    }

//    public int getLat() {
//        return Lat;
//    }

    public void setLat(int lat) {
        Lat = lat;
    }
}
