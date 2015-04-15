package seneca.ict.provider;

import android.app.Activity;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by student on 4/14/15.
 */
public class CoursesView extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    public void onClickRetrieveCourses(View view) {
            Uri allTitles = Uri.parse(
                    "content://seneca.ict.provider.Courses/courses");

            Cursor c;
            if (android.os.Build.VERSION.SDK_INT < 11) {
                //---before Honeycomb---
                c = managedQuery(allTitles, null, null, null,
                        "title desc");
            } else {
                //---Honeycomb and later---
                CursorLoader cursorLoader = new CursorLoader(
                        this,
                        allTitles, null, null, null,
                        "title desc");
                c = cursorLoader.loadInBackground();
            }

            ListView courseList = (ListView) findViewById(R.id.listview);
            ArrayList<String> courses = new ArrayList<String>();
            if (c.moveToFirst()) {
                do {
                    courses.add(c.getString(c.getColumnIndex(
                            CoursesProvider.TITLE)) + ", " +
                            c.getString(c.getColumnIndex(
                                    CoursesProvider.CODE)) + ", " +
                            c.getString(c.getColumnIndex(
                                    CoursesProvider.ROOM)));
                } while (c.moveToNext());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courses);

            courseList.setAdapter(adapter);
        }
    }


