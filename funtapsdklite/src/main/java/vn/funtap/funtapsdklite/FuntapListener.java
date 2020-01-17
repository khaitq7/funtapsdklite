package vn.funtap.funtapsdklite;

public interface FuntapListener {
    public void onLoginSuccessful(String access_token , String ft_id);
    public void onError(int errorCode, String message);
    public void onCloseForm();
}
