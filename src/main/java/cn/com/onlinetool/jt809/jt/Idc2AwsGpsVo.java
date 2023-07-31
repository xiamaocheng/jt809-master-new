package cn.com.onlinetool.jt809.jt;

public class Idc2AwsGpsVo {

    private int Direction;
    private int Lon;

    private Number Lat;

    private String name ;

    private Number  code;




    private String  flight;


    private String  remark;

    public Number getLat() {
        return Lat;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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
