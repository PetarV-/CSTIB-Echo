package uk.ac.cam.echo.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import uk.ac.cam.echo.ConversationAdapter;
import uk.ac.cam.echo.R;
import uk.ac.cam.echo.Toaster;
import uk.ac.cam.echo.activities.ConversationListActivity;
import uk.ac.cam.echo.client.ClientApi;
import uk.ac.cam.echo.data.Conversation;
import uk.ac.cam.echo.onListLoadedListener;

import java.util.ArrayList;
import java.util.List;

public class ConversationListFragment extends Fragment implements
											OnItemClickListener {

	private Context context;
    private static ClientApi api;

	private Communicator comm;
	private ConversationAdapter adapter;
	
	private ListView listView;
	private ProgressBar listProgress;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		return inflater.inflate(R.layout.conv_listview_layout, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView = (ListView)getView().findViewById(R.id.convListView);
		listProgress = (ProgressBar)getView().findViewById(R.id.listProgress);
		Log.d("SEARCH", "Frag onViewCreated");
        //new PerformSearch().execute();
	}

    public void getAllConversations() {
        Log.d("SEARCH", "gettingAllConversations()");
        new PerformSearch().execute();
    }

	public void performSearch(String query) {
        Log.d("SEARCH", "performingSearch()");
		new PerformSearch().execute(query);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Log.d("ConversationListFragment", "clicked on "+ id);
		comm.respond(id);
	}

	public void openConversation(long id) {
        int pos = adapter.getPosition(id);

		listView.performItemClick(
				listView.getAdapter().getView(pos, null, null),
				pos,
                id);
	}

	public interface Communicator {
		public void respond(long id);
	}

	public void setCommunicator(Communicator comm) {
		this.comm = comm;
	}

    public ClientApi getApi() { return api; }
    public void setApi(ClientApi clientApi) { api = clientApi; }

    public ConversationAdapter getAdapter() {return adapter; }

	private class PerformSearch extends AsyncTask<String, Void, List<Conversation>> {
		

        ArrayList<String> some = new ArrayList<String>();
		
		@Override
		protected List<Conversation> doInBackground(String... params) {
		    List<Conversation> result;
			
			if(params.length == 0) {
                Log.d("SEARCH","params = 0");
				result = api.conferenceResource.getConversations(1);
                Log.d("SEARCH", "all conversations downloaded");
			} else {
                String query = params[0];
                Log.d("SEARCH","params > 0: searching :" + query);
                result = api.conferenceResource.search(1, query, 10);
                Log.d("SEARCH", "all search results downloaded");
            }

			for(uk.ac.cam.echo.data.Conversation c : result)
                some.add(c.getName());
			Log.d("ConversationListFragment", result.get(0).getName());
            Log.d("ConversationListFragment", result.size()+"");
			return result;
		}

		@Override
		protected void onPostExecute(List<Conversation> result) {
			// update list view
			super.onPostExecute(result);

			if(adapter == null) {
				//adapter = new ConversationAdapter(context, R.layout.conv_list_row, result);
                adapter = ConversationAdapter.newInstance(context, R.layout.conv_list_row, result, api);

				listView.setAdapter(adapter);
                adapter.setListViewListener((ConversationListActivity)getActivity());
				listView.setOnItemClickListener(ConversationListFragment.this);
				listView.setVisibility(View.VISIBLE);
				listProgress.setVisibility(View.GONE);
				Log.d("SEARCH", "createdListView PerformSearch");
			} else {
				Log.d("SEARCH", "updatedListView PerformSearch");
				adapter.updateList(result);
			}
		}
	}
}
