package vn.funtap.funtapsdklite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private UserInfo userInfo;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Data data) {
        this.userInfo = userInfo;
    }

    public class UserInfo{
        @SerializedName("access_token")
        @Expose
        private String accessToken;
        @SerializedName("ft_id")
        @Expose
        private String ftId;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getFtId() {
            return ftId;
        }

        public void setFtId(String ftId) {
            this.ftId = ftId;
        }
    }
}
