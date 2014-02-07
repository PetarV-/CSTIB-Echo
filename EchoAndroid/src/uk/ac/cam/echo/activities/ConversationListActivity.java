package uk.ac.cam.echo.activities;

import java.util.List;

import uk.ac.cam.echo.R;
import uk.ac.cam.echo.Toaster;
import uk.ac.cam.echo.dummy.Conversation;
import uk.ac.cam.echo.fragments.AddConversationDialog;
import uk.ac.cam.echo.fragments.ConversationDialog;
import uk.ac.cam.echo.fragments.ConversationListFragment;
import uk.ac.cam.echo.fragments.ConversationListFragment.Communicator;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ConversationListActivity extends Activity implements Communicator {
	
	FragmentManager manager;
	boolean dualPane; //to manage orientations/different screensizes
	
	ConversationListFragment clf;
	List<Conversation> conversations;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.conv_list_detail_layout);
            
            manager = getFragmentManager(); 
            View detailsFrame = findViewById(R.id.convFrame);
            dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
            
            clf = (ConversationListFragment) manager.findFragmentById(R.id.convListFragment);
            clf.setCommunicator(this);
            
            handleIntent(getIntent());
            
    }
	
	/* if current view is dual-pane, update the fragment, otherwise
	 * start the stand-alone conversation activity.
	 */
	@Override
	public void respond(long id) {
		
		if(dualPane) {
			
			ConversationDialog convFrag =
					(ConversationDialog)manager.findFragmentById(R.id.convFrame);
			if(convFrag == null || convFrag.getShownIndex() != id) {
				convFrag = ConversationDialog.newInstance(id);
				FragmentTransaction ft = manager.beginTransaction();
				ft.replace(R.id.convFrame, convFrag);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		} else {
	
			ConversationDialog cd = ConversationDialog.newInstance(id);
			cd.show(manager, "conversation_info");
			
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		getMenuInflater().inflate(R.menu.list_activity_actions, menu);
		
		// Associate searchable configuration with the SearchView
		SearchManager searchManager =
				(SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView =
				(SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(
				searchManager.getSearchableInfo(getComponentName()));
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	        	//openSearch();
	            return true;
	        case R.id.action_scan:
	            openScan();
	            return true;
	        case R.id.action_add_conv:
	        	addConversation();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	    setIntent(intent);
	    handleIntent(intent);
	}
	
	private void handleIntent(Intent intent) {
		if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			/* conversations = api.conferenceResource.onlyNameSearch(
			*				service.getConferenceId(),
			*				query,
			*				10};
			*/
			//clf.getAdapter().updateList(conversations);
			clf.performSearch(query);
		} else {
			// conversations = api.conferenceResource.getConversations(conf_id);
			//clf.displayAll();
		}
		
		
	}

	
	private void openScan() {
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();
	}
	
	private void addConversation() {
		AddConversationDialog addDialog = AddConversationDialog.newInstance();
		addDialog.show(manager, "add_conv");
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve scan result
		IntentResult scanningResult =
				IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if(scanningResult != null) {
			String scanContent = scanningResult.getContents();
			//String scanFormat = scanningResult.getFormatName();
			
			int position = -1;
			try {
				position = Integer.parseInt(scanContent);
			}catch(Exception e) {
				Log.e("CLF", e.getMessage());
			}
			if (position != -1) 
				clf.openConversation(position);
		}else {
			Toaster.displayLong(this, "No scan data received!");
		}
	}
	
	
}
