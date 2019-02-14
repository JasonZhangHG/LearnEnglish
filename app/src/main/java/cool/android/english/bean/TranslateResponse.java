package cool.android.english.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslateResponse {
    /**
     * from : zh
     * to : en
     * trans_result : [{"src":"我","dst":"I"}]
     */

    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;
    @SerializedName("trans_result")
    private List<TransResultBean> transResult;

    public String getFrom() { return from;}

    public void setFrom(String from) { this.from = from;}

    public String getTo() { return to;}

    public void setTo(String to) { this.to = to;}

    public List<TransResultBean> getTransResult() {
        return transResult;
    }

    public void setTransResult(List<TransResultBean> transResult) {
        this.transResult = transResult;
    }

    @Override
    public String toString() {
        return "TranslateResponse{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", transResult=" + transResult +
                '}';
    }

    public static class TransResultBean {
        /**
         * src : 我
         * dst : I
         */

        @SerializedName("src")
        private String src;
        @SerializedName("dst")
        private String dst;

        public String getSrc() { return src;}

        public void setSrc(String src) { this.src = src;}

        public String getDst() { return dst;}

        public void setDst(String dst) { this.dst = dst;}

        @Override
        public String toString() {
            return "TransResultBean{" +
                    "src='" + src + '\'' +
                    ", dst='" + dst + '\'' +
                    '}';
        }
    }
}
