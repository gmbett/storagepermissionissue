package com.gmbett.storagepermissionissue;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.ref.WeakReference;

/**
 * Extension of {@link AsyncTask} used to save a file in {@link Environment#DIRECTORY_DOCUMENTS}.
 *
 * To be able to save the file the app needs {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
 */
class WriteFileTask extends AsyncTask<Void, Void, Boolean> {

    private final WeakReference<Context> mContextRef;

    WriteFileTask(@NonNull final Context context) {
        mContextRef = new WeakReference<>(context);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        boolean fileWasSaved = true;

        final String directory = Environment.DIRECTORY_DOCUMENTS;
        final File path = Environment.getExternalStoragePublicDirectory(directory);
        final File file = new File(path, "storage_permission_issue.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write("Hello World");
        } catch (Exception ex) {
            ex.printStackTrace();
            fileWasSaved = false;
        }

        return fileWasSaved;
    }

    @Override
    protected void onPostExecute(final Boolean result) {
        final Context context = mContextRef.get();

        if (context != null) {
            final String message = context.getString(R.string.write_file_result, result);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
