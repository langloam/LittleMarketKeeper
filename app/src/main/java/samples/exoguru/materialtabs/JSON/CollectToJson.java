package samples.exoguru.materialtabs.JSON;

import com.google.gson.annotations.SerializedName;

/**
 * Created by iii on 2015/10/27.
 */
public class CollectToJson {
    @SerializedName("fFBID")
    private String fFBID;
    @SerializedName("fNameID")
    private String fNameID;
    @SerializedName("fName")
    private String fName;
    @SerializedName("fImg")
    private String fImg;
    @SerializedName("fInfo")
    private String fInfo;
    @SerializedName("fArea")
    private String fArea;
    @SerializedName("fContent")
    private String fContent;

    public CollectToJson(){

    }
    public CollectToJson(String fbid, String nameid, String name, String img, String info , String area, String content){
        setfFBID(fbid);
        setfNameID(nameid);
        setfName(name);
        setfImg(img);
        setfInfo(info);
        setfArea(area);
        setfContent(content);

    }
    public String getfArea() {
        return fArea;
    }

    public void setfArea(String fArea) {
        this.fArea = fArea;
    }

    public String getfContent() {
        return fContent;
    }

    public void setfContent(String fContent) {
        this.fContent = fContent;
    }

    public String getfFBID() {
        return fFBID;
    }

    public void setfFBID(String fFBID) {
        this.fFBID = fFBID;
    }

    public String getfNameID() {
        return fNameID;
    }

    public void setfNameID(String fNameID) {
        this.fNameID = fNameID;
    }

    public String getfImg() {
        return fImg;
    }

    public void setfImg(String fImg) {
        this.fImg = fImg;
    }

    public String getfInfo() {
        return fInfo;
    }

    public void setfInfo(String fInfo) {
        this.fInfo = fInfo;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }



}
