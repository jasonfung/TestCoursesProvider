package seneca.ict.provider;

import android.app.Activity;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CoursesProviderActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClickAddTitle(View view) {
		/*
		//---add a book---
		ContentValues values = new ContentValues();
		values.put(BooksProvider.TITLE, ((EditText)
				findViewById(R.id.txtTitle)).getText().toString());
		values.put(BooksProvider.ISBN, ((EditText)
				findViewById(R.id.txtISBN)).getText().toString());
		Uri uri = getContentResolver().insert(
				BooksProvider.CONTENT_URI, values);
		 */

        ContentValues values = new ContentValues();
        values.put("title", ((EditText)
                findViewById(R.id.txtTitle)).getText().toString());
        values.put("code", ((EditText)
                findViewById(R.id.txtCode)).getText().toString());
        values.put("room", ((EditText)
                findViewById(R.id.txtRoom)).getText().toString());
        Uri uri = getContentResolver().insert(
                Uri.parse(
                        "content://seneca.ict.provider.Courses/courses"),
                values);

        Toast.makeText(getBaseContext(),uri.toString(),
                Toast.LENGTH_LONG).show();
    }


    public void onClickRetrieveCourses(View view) {
        //---retrieve the titles---

        Uri allTitles = Uri.parse(
                "content://seneca.ict.provider.Courses/courses");

        Cursor c;
        if (android.os.Build.VERSION.SDK_INT <11) {
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


    public void updateTitle() {
        ContentValues editedValues = new ContentValues();
        editedValues.put(CoursesProvider.TITLE, "Android Tips and Tricks");
        getContentResolver().update(
                Uri.parse(
                        "content://seneca.ict.provider.Courses/courses/2"),
                editedValues,
                null,
                null);
    }

    public void deleteTitle() {

        //---delete a title---
        getContentResolver().delete(
                Uri.parse("content://seneca.ict.provider.Courses/courses/2"),
                null, null);


        //---delete all titles---
        getContentResolver().delete(
                Uri.parse("content://seneca.ict.provider.Courses/courses"),
                null, null);

    }

}