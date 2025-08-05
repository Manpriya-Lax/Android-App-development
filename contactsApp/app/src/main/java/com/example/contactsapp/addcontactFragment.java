package com.example.contacts;

import android.bluetooth.le.ScanSettings;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.app.Activity;






public class addcontactFragment extends Fragment {

    private Button Back, Add;

    private EditText name, number;
    private ImageView DP;
    String sname ,snumber;

    private static final int CAMERA_PERMITION_CODE =100;
    private static final int STORAGE_PERMITION_CODE =200;
    private static final int IMAGE_FROM_GALLERY_CODE =300;
    private static final int IMAGE_FROM_CAMERA_CODE =400;
    private ImageView profileIv;


    Uri img;
    private String[] campermition;
    private String[] storagepermition;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addcontact, container, false);

        Back = view.findViewById(R.id.back);
        Add = view.findViewById(R.id.add);
        name = view.findViewById(R.id.name);
        number = view.findViewById(R.id.number);
        DP = view.findViewById(R.id.DP);
        profileIv = view.findViewById(R.id.DP);


        campermition =new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagepermition =new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        Add.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view){
                

                saveData();

            }});



        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addcontactFragment addcontactFragment = new addcontactFragment();

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame,new recyclerviewFragment());
                transaction.commit();

            }

        });
        
        DP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgpicker();
            }

            private void imgpicker() {
                String options[] = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Select an image source");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the selection here
                        if (which == 0) {
                            if (!checkcampermition()) {
                                reqstoragepermition();
                            } else {
                                pickfromcam();
                            }
                        } else if (which == 1) {
                            if (!checkstoragepermition()) {
                                reqstoragepermition();
                            } else {
                                pickfromgall();
                            }
                        }
                    }

                });

                builder.show();
            }
        });

        return view;

        }

    private void pickfromgall() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");

        startActivityForResult(galleryIntent, IMAGE_FROM_GALLERY_CODE);
    }


    private void pickfromcam() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"IMAGE TITLE ");
        values.put(MediaStore.Images.Media.DESCRIPTION,"IMAGE DETAILS");

        img = requireActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent camIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         camIntent.putExtra(MediaStore.EXTRA_OUTPUT,img);


         startActivityForResult(camIntent,IMAGE_FROM_CAMERA_CODE);

    }


    private void saveData() {

        sname=name.getText().toString();
        snumber=number.getText().toString();

        if(!sname.isEmpty() || !snumber.isEmpty() ){

        }else {
            Toast.makeText(getActivity().getApplicationContext(),"try again ", Toast.LENGTH_SHORT).show();
        }


    }


    private boolean checkcampermition(){
        boolean result = ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);


        return result & result1;

    }

    private void reqCampermition(){
        ActivityCompat.requestPermissions(requireActivity(),campermition,CAMERA_PERMITION_CODE);
    }

    private boolean checkstoragepermition(){
        boolean result = ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);


        return result ;

    }

    private void reqstoragepermition(){
        ActivityCompat.requestPermissions(requireActivity(),storagepermition,CAMERA_PERMITION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode , @NonNull String[] permission , @NonNull int [] grantResults){
        super.onRequestPermissionsResult(requestCode,permission,grantResults);

        switch (requestCode) {
            case CAMERA_PERMITION_CODE:
                if (grantResults.length > 0) {
                    boolean camaccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageaccept = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (camaccept && storageaccept) {
                        pickfromcam();

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "camera & storage permition needed ", Toast.LENGTH_SHORT).show();
                    }

                }

                break;

            case STORAGE_PERMITION_CODE:
                if (grantResults.length > 0) {

                    boolean storageaccept = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (storageaccept) {
                        pickfromgall();

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "storage permition needed ", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_FROM_GALLERY_CODE) {
                // Handle the image selected from the gallery
                Uri selectedImageUri = data.getData();
                // Now you can do whatever you need with the selectedImageUri, like displaying it in an ImageView.
            } else if (requestCode == IMAGE_FROM_CAMERA_CODE) {
                // Handle the image captured from the camera
                // The captured image is already in 'img', so you can use it as needed.
                // For example, you can display it in an ImageView:
                profileIv.setImageURI(img);
            }
        }
    }



}


