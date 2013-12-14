package org.finistgdg.bootcamp.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.finistgdg.bootcamp.android.object.Tweet;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by stephane on 13/12/2013.
 */
public class TweetsDBDao {

    private SQLiteDatabase _bdd;

    private  BaseSQLite _BaseSqlite;

    public TweetsDBDao(Context context){
        //On cr�er la BDD et sa table
        _BaseSqlite = BaseSQLite.getInstance(context);
    }


    /**
     * in charge of opening DB
     * @param readOnly
     */
    public void open(Boolean readOnly){
        //on ouvre la BDD en �criture
        if(readOnly)
            _bdd = _BaseSqlite.getReadableDatabase();
        else
            _bdd = _BaseSqlite.getWritableDatabase();
    }

    /**
     * in charge of closing DB
     * BEWARE: Do NOT forget to use this method
     */
    public void close(){
        _bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return _bdd;
    }

    public long[] insertTweets(List<Tweet> tweets){
        try	{
            //avoid using multiple access
            if(_bdd == null || _bdd.isReadOnly()){
                open(false);
            }
            long[] ids = new long [tweets.size()];
            int i = 0;
            //insert each media
            for(Tweet tweet: tweets){
                ContentValues values = new ContentValues();
                values.put(TweetTable.COL_ID, tweet.getId());
                values.put(TweetTable.COL_AUTHOR, tweet.getAuthor());
                values.put(TweetTable.COL_MESSAGE, tweet.getMessage());
                values.put(TweetTable.COL_TIMESTAMP, tweet.getTimestamp());

                Log.d("bootcamp", "insert tweet: " + tweet.getId());
                ids[i] = _bdd.insert(TweetTable.TWEET_TABLE_NAME, null, values);
                i++;
            }
            return ids;
        }
        catch (Exception e) {
            Log.e("bootcamp", " exception", e);
        }
        return null;
    }

    public int removeTweetWithID(String id){
        if(_bdd == null || _bdd.isReadOnly()){
            open(false);
        }
        //delete media
        int code = _bdd.delete(TweetTable.TWEET_TABLE_NAME, TweetTable.COL_ID + " = " +id, null);

        return code;
    }

    /**
     * getting tweets from DB
     * @return
     */
    public List<Tweet> getTweets(){
        if(_bdd == null){
            open(true);
        }
        //get Query in cursor
        Cursor c = _bdd.rawQuery("SELECT * FROM "+TweetTable.TWEET_TABLE_NAME, null);

        LinkedList<Tweet> tweets = new LinkedList<Tweet>();
        for(int i = 0; i<c.getCount(); i++)	{
            tweets.add(cursorToTweet(c, i));
        }
        //close cursor
        c.close();

        return tweets;
    }



    /**
     * <p>Convert cursor to a tweet</p>
     */
    private Tweet cursorToTweet(Cursor c, int i){
        try	{
            //if nothing found, return null;
            if (c.getCount() == 0)
                return null;

            //Sinon on se place sur le premier �l�ment
            c.moveToPosition(i);
            Tweet tweet = new Tweet();
            tweet.setId(c.getString(0));
            tweet.setAuthor(c.getString(1));
            tweet.setMessage(c.getString(2));
            tweet.setTimestamp(c.getString(3));
            Log.d("bootcamp", "cursorToTweet tweet: " + tweet.getId());

            return tweet;
        }
        catch (Exception e) {
            Log.e("bootcamp", "TweetBddDao cursorToProduct exception",e);
        }
        return null;
    }
}
