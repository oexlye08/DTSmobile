package com.example.dtsmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class home extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    private LinearLayout mRootLayout;
    private ListView mListView;

    private static final int MY_PERMISSION_REQUEST_CODE = 123;

    private HashMap<Long,String> mAudioMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Get the application context
        mContext = getApplicationContext();
        mActivity = home.this;

        // Get the widget reference from xml layout
        mRootLayout = findViewById(R.id.root_layout);
        mListView = findViewById(R.id.list_view);

        // Custom method to check permission at run time
        checkPermission();

        // Perintah listView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(home.this, Playing.class);

                startActivity(intent);
            }
        });

    }

    // Metode khusus untuk mendapatkan semua daftar file audio dari penyimpanan eksternal
    protected void getMusicFilesList(){
        // Dapatkan penyelesai konten
        ContentResolver contentResolver = mContext.getContentResolver();

        // Dapatkan penyimpanan eksternal dari audio penyimpanan media
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        /*
             String IS_MUSIC
                 Bukan nol jika file audio adalah musik

             Jenis: INTEGER (boolean)
        */

        // String permintaan untuk memfilter file musik saja
        // Hapus nada dering, notifikasi dll suara dari daftar
        String selectionString = MediaStore.Audio.Media.IS_MUSIC + "!=0";

        // String kueri untuk mengurutkan musik berdasarkan tanggal ditambahkan
        String sortString = MediaStore.Audio.Media.DATE_ADDED;

        // Minta toko media hanya untuk file musik
        Cursor cursor = contentResolver.query(
                uri, // Uri
                null, // Projection
                selectionString, // Selection
                null, // Selection args
                sortString // Sort order
        );

        if (cursor == null) {
            // Permintaan gagal, atasi kesalahan
        } else if (!cursor.moveToFirst()) {
            // Tidak ada media di perangkat
        } else {
            int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            // Ulangi musik
            do {
                // Dapatkan id file audio saat ini
                long thisId = cursor.getLong(id);

                // Dapatkan judul audio saat ini
                String thisTitle = cursor.getString(title);

                // Memproses musik saat ini di sini
                mAudioMap.put(thisId,thisTitle);
            } while (cursor.moveToNext());
        }

        // Dapatkan array nilai dari  hash map
        String[] titlesArray = mAudioMap.values().toArray(new String[mAudioMap.size()]);

        // Inisialisasi array adapter
        ArrayAdapter adapter = new ArrayAdapter(
                mContext, // Context
                android.R.layout.simple_list_item_1, // Item layout
                titlesArray // Data source
        );
        // Tampilan daftar data mengikat dengan array adapter
        mListView.setAdapter(adapter);
    }

    // Metode khusus untuk memeriksa dan memberikan izin pada run time
    protected void checkPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    // Show an alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setMessage("Read external storage permission is required.");
                    builder.setTitle("Please grant permission");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(
                                    mActivity,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSION_REQUEST_CODE
                            );
                        }
                    });
                    builder.setNeutralButton("Cancel",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    // Request permission
                    ActivityCompat.requestPermissions(
                            mActivity,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSION_REQUEST_CODE
                    );

                }
            }else {
                // Permission already granted
                getMusicFilesList();
            }
        }else {
            // Permission granted by manifest file
            getMusicFilesList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch(requestCode){
            case MY_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Permission granted
                    getMusicFilesList();
                }else {
                    // Permission denied
                }
            }
        }
    }



    /***menambahkan option menu ke layout yg di tuju***/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=
                getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);  /***optionmenu adalah namna xml***/
        return true;
    }
    /*** Perintah Menu***/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.profil){ /***profil adalah nama id***/
            startActivity(new Intent(this, profil.class));
        } else if (item.getItemId()==R.id.keluar){  /*** keluar nama id***/
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }

}
