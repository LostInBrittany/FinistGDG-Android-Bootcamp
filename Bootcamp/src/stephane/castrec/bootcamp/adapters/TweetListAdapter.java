package stephane.castrec.bootcamp.adapters;

import java.util.LinkedList;
import java.util.List;

import stephane.castrec.bootcamp.activities.R;
import stephane.castrec.bootcamp.object.SessionObject;
import stephane.castrec.bootcamp.object.Tweet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TweetListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Tweet> mTweets;

	/**
	 * Ctor
	 * @param pContext : calling activity 
	 * @param pList : list to map
	 */
	public TweetListAdapter(Context pContext, List<Tweet> pList){
		mInflater = LayoutInflater.from(pContext);
		if(pList == null)
			mTweets = new LinkedList<Tweet>();
		else
			mTweets = pList;
	}

	/**
	 * updating list when receiving new item from server
	 * @param pList
	 */
	public void updateList(List<Tweet> pList){
		if(pList != null){
			mTweets.clear();
			mTweets.addAll(pList);
		}
	}

	/**
	 * return count of list
	 */
	public int getCount() {
		if(mTweets == null)
			return 0;
		return mTweets.size();
	}

	/**
	 * get Item
	 */
	public Object getItem(int arg0) {
		return mTweets.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	/**
	 * getView
	 * map view Item
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		try{
			// A ViewHolder keeps references to children views to avoid unneccessary calls
			// to findViewById() on each row.
			ViewHolder holder;

			// When convertView is not null, we can reuse it directly, there is no need
			// to reinflate it. We only inflate a new View when the convertView supplied
			// by ListView is null.
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.tweet, null);

				// Creates a ViewHolder and store references to the two children views
				// we want to bind data to.
				holder = new ViewHolder();
				holder.user = (TextView) convertView.findViewById(R.id.tweet_author);
				holder.message = (TextView) convertView.findViewById(R.id.tweet_message);

				convertView.setTag(holder);
			} else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
				holder = (ViewHolder) convertView.getTag();
			}

			// Bind the data efficiently with the holder.
			holder.user.setText(mTweets.get(position).getAuthor());
			holder.message.setText(mTweets.get(position).getMessage());
		} catch (Exception e){

		}	

		return convertView;
	}

	static class ViewHolder {
		TextView user;
		TextView message;
	}

}
