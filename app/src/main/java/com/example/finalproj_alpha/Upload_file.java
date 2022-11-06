package com.example.finalproj_alpha;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import static com.example.finalproj_alpha.DB_refs.storageReference;

public class Upload_file extends AppCompatActivity {

    Button upload_file_btn, next_page_btn;
    Uri image_uri;
    TextView upload_status;
    PDFView pdfView;
    Intent settings_page_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_file_activity);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        upload_file_btn = findViewById(R.id.upload_file_btn);
        next_page_btn = findViewById(R.id.next_page);
        next_page_btn.setVisibility(View.INVISIBLE);

        upload_status = findViewById(R.id.upload_status);
        pdfView = findViewById(R.id.pdfView);

    }

    public void upload_func(View view) {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        // Restrict uploads only to Pdfs.
        galleryIntent.setType("application/pdf");
        startActivityForResult(galleryIntent, 1);
    }

    ProgressDialog dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Upload PDF to Firebase
            image_uri = data.getData();

            File file = new File(String.valueOf(image_uri));
            file.setReadable(true, true);

            String timestamp = "" + System.currentTimeMillis();
            String messagePushID = timestamp;

            // Show loading Dialog Box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");
            dialog.show();

            StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
            filepath.putFile(image_uri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
              @Override
              public void onComplete(@NonNull Task<Uri> task) {
                  if (task.isSuccessful()) {
                      dialog.dismiss();
                      Uri uri = task.getResult();
                      String myurl;
                      myurl = uri.toString();
                      upload_status.setText("Upload Successful");


                      pdfView.fromUri(image_uri)
                              .enableSwipe(true) // allows to block changing pages using swipe
                              .swipeHorizontal(false)
                              .enableDoubletap(true)
                              .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                              .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                              // spacing between pages in dp. To define spacing color, set view background
                              .spacing(0)
                              .load();

                      next_page_btn.setVisibility(View.VISIBLE);
                  } else {
                      dialog.dismiss();
                      upload_status.setText("Upload Failed");
                  }
              }
            });
        }
    }

    public void next_page_func(View view) {
        Intent settings_page_intent = new Intent(this, settings_page.class);
        settings_page_intent.putExtra("uri", image_uri.toString());
        startActivity(settings_page_intent);
    }
}