package kavya.sqliterecyclerstore.model;


public class UserInfo {

    String mUserLoginDateTime, mUserLogoutDateTime;
    int mId;

    public UserInfo(){

    }

    public UserInfo(int uId,String uLoginDateTime,String uLogoutDateTime){
        mId = uId;
        mUserLoginDateTime = uLoginDateTime;
        mUserLogoutDateTime = uLogoutDateTime;
    }

    public UserInfo(String uLoginDateTime,String uLogoutDateTime){
        mUserLoginDateTime = uLoginDateTime;
        mUserLogoutDateTime = uLogoutDateTime;
    }



    public void setmUserLoginDateTime(String mUserLoginDateTime) {
        this.mUserLoginDateTime = mUserLoginDateTime;
    }


    public void setmUserLogoutDateTime(String mUserLogoutDate) {
        this.mUserLogoutDateTime = mUserLogoutDate;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }


    public String getmUserLoginDateTime() {
        return mUserLoginDateTime;
    }


    public String getmUserLogoutDateTime() {
        return mUserLogoutDateTime;
    }

    public int getmId() {
        return mId;
    }
}
