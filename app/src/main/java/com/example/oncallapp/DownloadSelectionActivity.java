package com.example.oncallapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DownloadSelectionActivity displays a list of files in the bucket. Users can
 * select a file to download.
 */
public class DownloadSelectionActivity extends ListActivity {

    // The S3 client used for getting the list of objects in the bucket
    private AmazonS3Client s3;

    private static final String APP_COLOUR = "#0070AD";

    // An adapter to show the objects
    private SimpleAdapter simpleAdapter;
    private ArrayList<HashMap<String, Object>> transferRecordMaps;
    private Util util;
    private String bucket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_selection);
        util = new Util();
        Button btnBack = (Button) findViewById(R.id.buttonBack);
        btnBack.setBackgroundColor(Color.parseColor(APP_COLOUR));
        bucket = new AWSConfiguration(this).optJsonObject("S3TransferUtility").optString("Bucket");
        initData();
        initUI();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(DownloadSelectionActivity.this, DownloadActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(a);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the file list.
        new GetFileListTask().execute();
    }

    private void initData() {
        // Gets the default S3 client.
        s3 = util.getS3Client(DownloadSelectionActivity.this);
        transferRecordMaps = new ArrayList<>();
    }

    private void initUI() {
        simpleAdapter = new SimpleAdapter(this, transferRecordMaps,
                R.layout.bucket_item, new String[]{
                "key"
        },
                new int[]{
                        R.id.key
                });
        simpleAdapter.setViewBinder((view, data, textRepresentation) -> {
            if (view.getId() == R.id.key) {
                TextView fileName = (TextView) view;
                fileName.setAllCaps(true);
                fileName.setTextColor(Color.parseColor(APP_COLOUR));
                fileName.setText(data.toString());
                fileName.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
                fileName.setTextSize(18);
                return true;
            }
            return false;
        });
        setListAdapter(simpleAdapter);

        // When an item is selected, finish the activity and pass back the S3
        // key associated with the object selected
        getListView().setOnItemClickListener((adapterView, view, pos, id) -> {
            Intent intent = new Intent();
            intent.putExtra("key", (String) transferRecordMaps.get(pos).get("key"));
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    /**
     * This async task queries S3 for all files in the given bucket so that they
     * can be displayed on the screen
     */
    private class GetFileListTask extends AsyncTask<Void, Void, Void> {
        // A dialog to let the user know we are retrieving the files
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(DownloadSelectionActivity.this,
                    getString(R.string.refreshing),
                    getString(R.string.please_wait));
        }

        @Override
        protected Void doInBackground(Void... inputs) {
            // Queries files in the bucket from S3.
            List<S3ObjectSummary> s3ObjList = s3.listObjects(bucket).getObjectSummaries();
            transferRecordMaps.clear();
            for (S3ObjectSummary summary : s3ObjList) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("key", summary.getKey());
                transferRecordMaps.add(map);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            simpleAdapter.notifyDataSetChanged();
        }
    }
}
