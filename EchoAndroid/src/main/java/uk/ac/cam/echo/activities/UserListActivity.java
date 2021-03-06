package uk.ac.cam.echo.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cam.echo.R;
import uk.ac.cam.echo.UserAdapter;
import uk.ac.cam.echo.client.ClientApi;
import uk.ac.cam.echo.data.User;
import uk.ac.cam.echo.services.EchoService;

public class UserListActivity extends Activity {

    private EchoService echoService;
    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            echoService = ((EchoService.LocalBinder)service).getService();
            api = echoService.getApi();
            user = echoService.getUser();
            Log.d("USER", "user is now " + user.getUsername());
            onServiceReady();
        }
        public void onServiceDisconnected(ComponentName className) {
            echoService = null;
        }
    };
    ClientApi api;
    User user; //current user
    long id; //conversationId;
    UserAdapter adapter;
    ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        id = getIntent().getLongExtra("_id", 1);
        listView = (ExpandableListView)findViewById(R.id.userList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent service = new Intent(this, EchoService.class);
        bindService(service, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(connection);
    }


    private void onServiceReady() {
        new GetUsers().execute();
    }

    public void phoneUser(String phoneNumber) {
        String uri = "tel:" + phoneNumber.trim();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    public void emailUser(String emailAddress, String conversationName, String loggedInUserName, String userDisplay) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, conversationName + " - a personal message from " + loggedInUserName);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear " + userDisplay + ",\n\n\n" + loggedInUserName);
        startActivity(Intent.createChooser(emailIntent, "Choose an email client: "));
    }

    public EchoService getService() { return echoService; }

    private class GetUsers extends AsyncTask<String, Void, List<User>> {


        String conversationName;

        @Override
        protected List<User> doInBackground(String... params) {
            List<User> result;

            conversationName = api.conversationResource.get(id).getName();
            result = (List<User>) api.conversationResource.get(id).getUsers();

            return result;
        }

        @Override
        protected void onPostExecute(List<User> result) {
            // update list view
            super.onPostExecute(result);

            if(adapter == null) {
                getActionBar().setTitle(conversationName);
                getActionBar().setSubtitle("Active users ");
                adapter = UserAdapter.newInstance(UserListActivity.this, R.layout.user_group_item, result, api);
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
                Log.d("USERADAPTER", "createdListView PerformSearch");
            } else {
                Log.d("USERADAPTER", "updatedListView PerformSearch");
                adapter.updateList(result);
            }
        }
    }
}
