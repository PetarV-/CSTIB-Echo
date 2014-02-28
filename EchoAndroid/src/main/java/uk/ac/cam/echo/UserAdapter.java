package uk.ac.cam.echo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import uk.ac.cam.echo.client.ClientApi;
import uk.ac.cam.echo.data.Conversation;
import uk.ac.cam.echo.data.Interest;
import uk.ac.cam.echo.data.User;


public class UserAdapter extends BaseExpandableListAdapter {

    private int layoutResourceId;
    private HashMap<Long, UserCache> userMap;
    private Context context;
    private ClientApi api;
    private List<User> data;

    public UserAdapter(Context context, int layoutResourceId,
                               List<User> data) {

        //super(context, layoutResourceId, data);
        this.context = context;
        userMap = new HashMap<Long, UserCache>();

        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    public static UserAdapter newInstance(Context context, int layoutResourceId,
                                                  List<User> data, ClientApi api) {

        UserAdapter adapter = new UserAdapter(context, layoutResourceId, data);
        adapter.setApi(api);
        return adapter;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View row = convertView;
        UserHolder holder;

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.user_child_item, parent, false);

            holder = new UserHolder();
            holder.avatar = (ImageView)row.findViewById(R.id.avatar);
            holder.user = (TextView)row.findViewById(R.id.username);
            holder.jobAndCompany = (TextView)row.findViewById(R.id.jobAndCompany);
            holder.interests = (TextView)row.findViewById(R.id.interests);
            holder.phone = (TextView)row.findViewById(R.id.phone);
            holder.email = (TextView)row.findViewById(R.id.email);

            row.setTag(holder);
        } else {
            holder = (UserHolder)row.getTag();
        }

        User user = data.get(groupPosition);

        if(userMap.containsKey(user.getId())) {
            UserCache userCache = userMap.get(user.getId());
            holder.avatar.setImageBitmap(userCache.avatar);
            holder.user.setText(userCache.user);
            holder.jobAndCompany.setText(userCache.jobAndCompany);
            holder.interests.setText(userCache.interests);
            holder.phone.setText(userCache.phone);
            holder.email.setText(userCache.email);
        } else {
            new AsyncAdapter().execute(user, holder.avatar, holder.user, holder.jobAndCompany,
                    holder.interests, holder.phone, holder.email);
        }
        return row;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View row = convertView;

        User user = data.get(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.user_group_item, parent, false);
        }

        TextView userDisplay = (TextView) row.findViewById(R.id.userFullName);
        userDisplay.setText(user.getDisplayName());
        Log.d("USERADAPTER", "creating new view " + groupPosition);
        return row;
    }

    public void setApi(ClientApi clientApi) { api = clientApi; }

    public void updateList(List<User> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int i2) {
        return data.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return data.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return data.get(groupPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    static class UserHolder {
        ImageView avatar;
        TextView user;
        TextView jobAndCompany;
        TextView interests;
        TextView phone;
        TextView email;
    }

    static class UserCache {
        Bitmap avatar;
        String user;
        String jobAndCompany;
        String interests;
        String phone;
        String email;

        public UserCache(Bitmap avatar, String user, String jobAndCompany,
                         String interests, String phone, String email) {
            this.avatar = avatar;
            this.user = user;
            this.jobAndCompany = jobAndCompany;
            this.interests = interests;
            this.phone = phone;
            this.email = email;
        }
    }


    private class AsyncAdapter extends AsyncTask<Object, Void, String> {
        User user;

        ImageView imgView;
        TextView username;
        TextView jobAndCompany;
        TextView interests;
        TextView phone;
        TextView email;
        Bitmap avatar;

        String usernameText;
        String jobAndCompanyText;
        String interestsText;
        String phoneText;
        String emailText;


        @Override
        protected String doInBackground(Object... params) {
            user = (User)params[0];
            imgView = (ImageView)params[1];
            username = (TextView)params[2];
            jobAndCompany = (TextView)params[3];
            interests = (TextView)params[4];
            phone = (TextView)params[5];
            email = (TextView)params[6];

            usernameText = user.getUsername();
            Collection<Interest> interests = user.getInterests();
            interestsText = ConversationStringUtil.getInterestText(interests);
            jobAndCompanyText = ConversationStringUtil.getJobCompanyText(user.getJobTitle(), user.getCompany());
            phoneText = user.getPhoneNumber();
            emailText = user.getEmail();
            avatar = BitmapUtil.getBitmapFromURL(user.getAvatarLink() + "&s=90");

            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            username.setText(usernameText);

            jobAndCompany.setText(jobAndCompanyText);
            interests.setText(interestsText);

            phone.setText(phoneText);
            email.setText(emailText);

            imgView.setImageBitmap(avatar);
            userMap.put(user.getId(), new UserCache(avatar, usernameText, jobAndCompanyText,
                    interestsText, phoneText, emailText ));
        }
    }
}
