package kavya.sqliterecyclerstore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kavya.sqliterecyclerstore.database.DataBaseHandler;
import kavya.sqliterecyclerstore.model.UserInfo;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerList;
    Button mButtonLogin,mButtonLogout;
    DataBaseHandler  dbHandler;
    List<UserInfo> userInfoList;
    UserInfoAdapter mUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerList = (RecyclerView)findViewById(R.id.list_recycle);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mButtonLogout = (Button)findViewById(R.id.button_logout);
        userInfoList = new ArrayList<>();
        dbHandler =  new DataBaseHandler(this);
        dbHandler.deleteUsers();
        mUserAdapter = new UserInfoAdapter(userInfoList);
        RecyclerView.LayoutManager manager =  new LinearLayoutManager(this);
        mRecyclerList.setLayoutManager(manager);
        mRecyclerList.setAdapter(mUserAdapter);
    }

    public void clickOperation(View buttonView){

        if(buttonView.getId()==R.id.button_login){
            // Inserting login user date and time
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedLoginDate = df.format(c.getTime());
            dbHandler.addUserInfo(new UserInfo(formattedLoginDate,""));
            userInfoList =  dbHandler.getUserInfo();
            setRecyclerAdapter();
        }else if(buttonView.getId() == R.id.button_logout){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedLogoutDate = df.format(c.getTime());

            dbHandler.updateUserInfo(new UserInfo(1,"",formattedLogoutDate));
            userInfoList =  dbHandler.getUserInfo();
            setRecyclerAdapter();
        }

    }

    void setRecyclerAdapter(){
        mUserAdapter = new UserInfoAdapter(userInfoList);
        mRecyclerList.setAdapter(mUserAdapter);
    }


    public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.MyUserViewHolder>{
        private List<UserInfo> myList;

        public UserInfoAdapter(List<UserInfo> userInfoList) {
            this.myList = userInfoList;
        }

        public class MyUserViewHolder extends RecyclerView.ViewHolder {
            TextView mRowLogin,mRowLogout;
            public MyUserViewHolder(View itemView) {
                super(itemView);
                mRowLogin = (TextView)itemView.findViewById(R.id.row_login);
                mRowLogout = (TextView)itemView.findViewById(R.id.row_logout);
            }
        }
        @Override
        public UserInfoAdapter.MyUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);

            return new MyUserViewHolder(childView);
        }

        @Override
        public void onBindViewHolder(UserInfoAdapter.MyUserViewHolder holder, int position) {
            UserInfo info =  myList.get(position);
            holder.mRowLogin.setText(info.getmUserLoginDateTime());
            holder.mRowLogout.setText(info.getmUserLogoutDateTime());
        }

        @Override
        public int getItemCount() {
            return myList.size();
        }


    }
}
