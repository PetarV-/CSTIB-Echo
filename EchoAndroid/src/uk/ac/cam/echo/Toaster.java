package uk.ac.cam.echo;

import android.content.Context;
import android.widget.Toast;

/* Helper class to display Toast messages without verbose syntax */
public class Toaster {
	
	public static void displayShort(Context c, String s) {
		Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
	}
	
	public static void displayLong(Context c, String s) {
		Toast.makeText(c, s, Toast.LENGTH_LONG).show();
	}
}
