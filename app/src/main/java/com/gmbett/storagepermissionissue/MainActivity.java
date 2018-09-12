package com.gmbett.storagepermissionissue;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int RC_STORAGE_PERMISSIONS = 1;

    private View mBtnWriteFile;
    private View mTxtWriteFileMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });

        mBtnWriteFile = findViewById(R.id.btn_write_file);
        mBtnWriteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WriteFileTask(MainActivity.this).execute();
            }
        });

        mTxtWriteFileMessage = findViewById(R.id.txt_write_file_message);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_go_to_settings:
                PermissionUtils.openPermissionSettings(this, 0);
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RC_STORAGE_PERMISSIONS && PermissionUtils.werePermissionsGranted(grantResults)) {
            checkPermission();
        } else {
            Toast.makeText(this, R.string.storage_permission_denied, Toast.LENGTH_SHORT).show();
        }

        showWriteFile();
    }

    private void checkPermission() {
        if (PermissionUtils.checkStoragePermissions(this, RC_STORAGE_PERMISSIONS)) {
            showWriteFile();
            Toast.makeText(this, R.string.storage_permission_granted, Toast.LENGTH_SHORT).show();
        }
    }

    private void showWriteFile() {
        mBtnWriteFile.setVisibility(View.VISIBLE);
        mTxtWriteFileMessage.setVisibility(View.VISIBLE);
    }
}
