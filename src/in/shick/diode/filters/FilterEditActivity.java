package in.shick.diode.filters;

import java.util.ArrayList;

import in.shick.diode.R;
import in.shick.diode.settings.RedditSettings;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FilterEditActivity extends Activity {
	
	private TextView m_tw_name;
	private TextView m_tw_subreddit;
	private TextView m_tw_filtertxt;
	private Button m_btn_add;
	private RedditSettings m_settings;
	private int m_filteridx;
	
	public static final String INTENT_FILTERID  = "INTENT_FILTERID";
	@Override
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.edit_filter_layout);
		m_tw_name = (TextView)findViewById(R.id.filter_name);
		m_tw_subreddit = (TextView)findViewById(R.id.subreddit_name);
		m_tw_filtertxt = (TextView)findViewById(R.id.filter_text);
		m_btn_add = (Button)findViewById(R.id.filter_addbtn);
		Intent i = getIntent();
		m_filteridx = i.getIntExtra(INTENT_FILTERID, -1);
		
		
		m_btn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				save();
				
			}
		});
		m_settings = new RedditSettings();
		m_settings.loadRedditPreferences(this,null);
		
		if(m_filteridx != -1) {
			SubredditFilter f = m_settings.getFilters().get(m_filteridx);
			m_tw_name.setText(f.getName());
			m_tw_subreddit.setText(f.getSubReddit());
			m_tw_filtertxt.setText(f.getPatternString());
		}
		
	}
	
	protected void save() {
		String name = m_tw_name.getText().toString().trim();
		String subreddit = m_tw_subreddit.getText().toString().trim();
		String filtertxt = m_tw_filtertxt.getText().toString();
		SubredditFilter f;
		ArrayList<SubredditFilter> filters = m_settings.getFilters();
		if(m_filteridx == -1)
		{
			// We're adding a filter
			f = new SubredditFilter(name, subreddit,true,filtertxt);
			filters.add(f);
		}
		else {
			f = filters.get(m_filteridx);
			f.setName(name);
			f.setSubreddit(subreddit);
			f.setPattern(filtertxt);
		}
		m_settings.setFilters(filters);
		m_settings.saveRedditPreferences(this);
		finish();
	}
	
	
}
