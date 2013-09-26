package com.twobigears.circlesynth;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.WindowManager;
import android.widget.Toast;

public class SynthSettingsTwo extends PreferenceActivity {

	public static final String PREF_DELETE = "deletefiles";
	public static final String PREF_TUTORIAL = "tutorial";
	public static final String PREF_FEEDBACK = "feedback";
	public static final String PREF_ABOUT = "about";
	public static final String PREF_HELP = "help";

	// ZubhiumSDK sdk;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		addPreferencesFromResource(com.twobigears.circlesynth.R.xml.preferences);

		Preference deleteprefs = (Preference) findPreference(PREF_DELETE);
		deleteprefs
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						String path = Environment.getExternalStorageDirectory()
								+ "/circlesynth";
						deleteFiles(path);
						return false;

					}
				});
		Preference tutorialpref = (Preference) findPreference(PREF_TUTORIAL);
		tutorialpref
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						showTutorial();

						return false;

					}
				});

		Preference feedbackpref = (Preference) findPreference(PREF_FEEDBACK);
		feedbackpref
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						setupFeedback();
						return false;
					}
				});

		Preference helppref = (Preference) findPreference(PREF_HELP);
		helppref.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				setupHelp();
				return false;
			}
		});

		Preference aboutpref = (Preference) findPreference(PREF_ABOUT);
		aboutpref.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				setupAbout();
				return false;
			}
		});
	}

	public void deleteFiles(String path) {

		File file = new File(path);

		if (file.exists()) {
			Toast.makeText(SynthSettingsTwo.this, "All saved sketches deleted",
					Toast.LENGTH_SHORT).show();
			String deleteCmd = "rm -r " + path;
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(deleteCmd);
			} catch (IOException e) {
			}
		}
	}

	public void showTutorial() {
		MiscDialogs.showTutorialDialog(SynthSettingsTwo.this);

	}

	public void setupFeedback() {

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { "apps@twobigears.com" });
		i.putExtra(Intent.EXTRA_SUBJECT, "Circle Synth feedback");

		try {
			startActivity(Intent.createChooser(i, "Send mail"));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(SynthSettingsTwo.this,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void setupHelp() {

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { "apps@twobigears.com" });
		i.putExtra(Intent.EXTRA_SUBJECT, "Circle Synth help issue");

		try {
			startActivity(Intent.createChooser(i, "Send mail"));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(SynthSettingsTwo.this,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void setupAbout() {

		AboutDialog about = new AboutDialog(this);
		about.setTitle("Circle Synth");

		about.show();
	}

	@Override
	protected void onUserLeaveHint() {
		// TODO Auto-generated method stub
		super.onUserLeaveHint();
		finish();
	}
}
