package uk.ac.cam.echo.fragments;

import uk.ac.cam.echo.R;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;





public class ConversationListFragment extends Fragment implements
		OnItemClickListener {
		
	Context context;
	ListView listView;
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                     Bundle savedInstanceState) {
             context = getActivity();
             return inflater.inflate(R.layout.conv_listview_layout, container, false);
     }
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// TODO Auto-generated method stub

	}

}
