package cool.android.english.bean;

import cn.bmob.v3.BmobObject;

public class Listener extends BmobObject {

    private String title;
    private String introduce;
    private String value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Listener{" +
                "title='" + title + '\'' +
                ", introduce='" + introduce + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
