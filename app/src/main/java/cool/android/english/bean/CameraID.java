package cool.android.english.bean;

import cn.bmob.v3.BmobObject;

public class CameraID extends BmobObject {

    private String camera1Id;
    private String camera2Id;

    public String getCamera1Id() {
        return camera1Id;
    }

    public void setCamera1Id(String camera1Id) {
        this.camera1Id = camera1Id;
    }

    public String getCamera2Id() {
        return camera2Id;
    }

    public void setCamera2Id(String camera2Id) {
        this.camera2Id = camera2Id;
    }

    @Override
    public String toString() {
        return "CameraID{" +
                "camera1Id='" + camera1Id + '\'' +
                ", camera2Id='" + camera2Id + '\'' +
                '}';
    }
}
