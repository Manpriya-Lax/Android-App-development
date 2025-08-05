package com.example.imagesearcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imagesearcher.model.UploadModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;

public class UploadingActivity extends AppCompatActivity {
    String imageUri;
    Uri imageUriFinal;
    ImageView ivImage;
    EditText etName;
    MaterialButton btnCancel, btnUpload;
    ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading);

        ivImage = findViewById(R.id.ivImage);
        etName = findViewById(R.id.etName);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpload = findViewById(R.id.btnUpload);

        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance("https://image-searcher-ac9ac-default-rtdb.firebaseio.com").getReference("ImagesData");
        storageReference = FirebaseStorage.getInstance().getReference("SearcherImages");

        if (getIntent().getExtras() != null){
            imageUri = getIntent().getStringExtra("url");
            Glide.with(UploadingActivity.this)
                    .load(imageUri)
                    .centerCrop()
                    .into(ivImage);
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = etName.getText().toString();
                    imageUriFinal = getImageUriFromImageView(ivImage);
                    if (name.isEmpty()){
                        Toast.makeText(UploadingActivity.this, "Please enter image name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progressDialog.setTitle("Going Good...");
                    progressDialog.setMessage("It takes Just a few Seconds... ");
                    progressDialog.setIcon(R.drawable.happy);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    StorageReference imageReference1 = storageReference.child(imageUriFinal.getLastPathSegment());
                    imageReference1.putFile(imageUriFinal).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String pushID = databaseReference.push().getKey();
                                    String uploadedImgURL = uri.toString();
                                    UploadModel model = new UploadModel(pushID, name, uploadedImgURL);
                                    databaseReference.child(pushID).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            progressDialog.dismiss();
                                            Toast.makeText(UploadingActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();;
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(UploadingActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(UploadingActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadingActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private Uri getImageUriFromImageView(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        String imageName = "image_" + etName.getText().toString();
        File cachePath = new File(getCacheDir(), imageName);
        try {
            cachePath.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(cachePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri finalImageUri = Uri.fromFile(cachePath);

        return finalImageUri;
    }

}