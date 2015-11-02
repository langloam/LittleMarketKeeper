package samples.exoguru.materialtabs.common.Demo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iii on 2015/11/2.
 */
public class couponInfo{

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBengindate() {
        return begindate;
    }

    public void setBengindate(String bengindate) {
        this.begindate = bengindate;
    }

    public String getBuliddate() {
        return buliddate;
    }

    public void setBuliddate(String buliddate) {
        this.buliddate = buliddate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String id;
    public String name;
    public String info;
    public String begindate;
    public String enddate;
    public String buliddate;
    public String shopid;
    public String shopname;

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String address;

}
