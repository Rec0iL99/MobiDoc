package com.semicolon.mobidoc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView searchView;

    //main input for the neural net
    private String neuralData="";

    //Parameters
    private LinearLayout age;
    private LinearLayout sex;
    private LinearLayout cp;
    private LinearLayout trestbps;
    private LinearLayout chol;
    private LinearLayout fbs;
    private LinearLayout restecg;
    private LinearLayout thalach;
    private LinearLayout exang;
    private LinearLayout oldpeak;
    private LinearLayout slope;
    private LinearLayout ca;
    private LinearLayout thal;
    private LinearLayout imageSection;

    //new Parameters
    private LinearLayout pregnancies;
    private LinearLayout glucose;
    private LinearLayout bloodPressure;
    private LinearLayout skinThick;
    private LinearLayout insulin;
    private LinearLayout bmi;
    private LinearLayout diabetes;


    //Buttons
    private Button submitAge;
    private Button submitSex;
    private Button submitCp;
    private Button submitTrestbps;
    private Button submitChol;
    private Button submitFbs;
    private Button submitRestecg;
    private Button submitThalach;
    private Button submitExang;
    private Button submitOldpeak;
    private Button submitSlope;
    private Button submitCa;
    private Button submitThal;
    private Button submitImage;

    //new buttons
    private Button submitPreg;
    private Button submitGlucose;
    private Button submitBlood;
    private Button submitSkinThick;
    private Button submitInsulin;
    private Button submitBmi;
    private Button submitDiabetes;

    //Spinners
    private Spinner sexSpinner;
    private Spinner cpSpinner;
    private Spinner fbsSpinner;
    private Spinner restecgSpinner;
    private Spinner exangSpinner;
    private Spinner slopeSpinner;
    private Spinner caSpinner;
    private Spinner thalSpinner;
    private Spinner imageSpinner;

    //Edittext for user input
    private EditText valueAge;
    private EditText valueBps;
    private EditText valueChol;
    private EditText valueThalach;
    private EditText valueOldpeak;

    //new edittext
    private EditText valuePreg;
    private EditText valueGlucose;
    private EditText valueBlood;
    private EditText valueSkinThick;
    private EditText valueInsulin;
    private EditText valueBmi;
    private EditText valueDiabetes;


    //Firebase Connection
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //Firebase storage
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    //Image upload
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filepath;

    private Button sendDataToFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("neural");

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        searchView = findViewById(R.id.searchMobi);

        //Linearlayout initialising
        age = findViewById(R.id.age);
        sex = findViewById(R.id.sex);
        cp = findViewById(R.id.cp);
        trestbps = findViewById(R.id.trestbps);
        chol = findViewById(R.id.chol);
        fbs = findViewById(R.id.fbs);
        restecg = findViewById(R.id.restecg);
        thalach = findViewById(R.id.thalach);
        exang = findViewById(R.id.exang);
        oldpeak = findViewById(R.id.oldpeak);
        slope = findViewById(R.id.slope);
        ca = findViewById(R.id.ca);
        thal = findViewById(R.id.thal);
        imageSection = findViewById(R.id.imageSection);

        //new linear
        pregnancies = findViewById(R.id.pregnancies);
        glucose = findViewById(R.id.glucose);
        bloodPressure = findViewById(R.id.bloodPressure);
        skinThick = findViewById(R.id.skinThick);
        insulin = findViewById(R.id.insulin);
        bmi = findViewById(R.id.bmi);
        diabetes = findViewById(R.id.diabetes);


        //spinner initialising
        sexSpinner = findViewById(R.id.sexSpinner);
        cpSpinner = findViewById(R.id.cpSpinner);
        fbsSpinner = findViewById(R.id.fbsSpinner);
        restecgSpinner = findViewById(R.id.restecgSpinner);
        exangSpinner = findViewById(R.id.exangSpinner);
        slopeSpinner = findViewById(R.id.slopeSpinner);
        caSpinner = findViewById(R.id.caSpinner);
        thalSpinner = findViewById(R.id.thalSpinner);
        imageSpinner = findViewById(R.id.imageSpinner);

        //Submit Button initialising
        submitAge = findViewById(R.id.submitAge);
        submitSex = findViewById(R.id.submitSex);
        submitCp = findViewById(R.id.submitCp);
        submitTrestbps = findViewById(R.id.submitBps);
        submitChol = findViewById(R.id.submitChol);
        submitFbs = findViewById(R.id.submitFbs);
        submitRestecg = findViewById(R.id.submitRestecg);
        submitThalach = findViewById(R.id.submitThalach);
        submitExang = findViewById(R.id.submitExang);
        submitOldpeak = findViewById(R.id.submitOldPeak);
        submitSlope = findViewById(R.id.submitSlope);
        submitCa = findViewById(R.id.submitCa);
        submitThal = findViewById(R.id.submitThal);
        submitImage = findViewById(R.id.submitImage);

        //new buttons
        submitPreg = findViewById(R.id.submitPreg);
        submitGlucose = findViewById(R.id.submitGlucose);
        submitBlood = findViewById(R.id.submitBlood);
        submitSkinThick = findViewById(R.id.submitThick);
        submitInsulin = findViewById(R.id.submitInsulin);
        submitBmi = findViewById(R.id.submitBmi);
        submitDiabetes = findViewById(R.id.submitDiabetes);

        //Edittext initialising
        valueAge = findViewById(R.id.valueAge);
        valueBps = findViewById(R.id.valueBps);
        valueChol = findViewById(R.id.valueChol);
        valueThalach = findViewById(R.id.valueThalach);
        valueOldpeak = findViewById(R.id.valueOldpeak);

        //new editext
        valuePreg = findViewById(R.id.valuePreg);
        valueGlucose = findViewById(R.id.valueGlucose);
        valueBlood = findViewById(R.id.valueBloodPressure);
        valueSkinThick = findViewById(R.id.valueThick);
        valueInsulin = findViewById(R.id.valueInsulin);
        valueBmi = findViewById(R.id.valueBmi);
        valueDiabetes = findViewById(R.id.valueDiabetes);

        //Senddata button
        sendDataToFire = findViewById(R.id.mainSendData);

        //Search
        String[] parameters = getResources().getStringArray(R.array.parameters);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, parameters);
        searchView.setAdapter(adapter);

        //Sex Spinner
        String[] sexPar = getResources().getStringArray(R.array.sexValues);
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, sexPar);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(sexAdapter);

        //cp Spinner
        String[] cpPar = getResources().getStringArray(R.array.cpValues);
        ArrayAdapter<String> cpAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cpPar);
        cpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cpSpinner.setAdapter(cpAdapter);

        //fbs Spinner
        String[] fbsPar = getResources().getStringArray(R.array.fbsValues);
        ArrayAdapter<String> fbsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, fbsPar);
        fbsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fbsSpinner.setAdapter(fbsAdapter);

        //restecg Spinner
        String[] restecgPar = getResources().getStringArray(R.array.restecgValues);
        ArrayAdapter<String> restecgAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, restecgPar);
        restecgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restecgSpinner.setAdapter(restecgAdapter);

        //exang spinner
        String[] exangPar = getResources().getStringArray(R.array.exangValues);
        ArrayAdapter<String> exangAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, exangPar);
        exangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exangSpinner.setAdapter(exangAdapter);

        //slope spinner
        String[] slopePar = getResources().getStringArray(R.array.slopeValues);
        ArrayAdapter<String> slopeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, slopePar);
        slopeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slopeSpinner.setAdapter(slopeAdapter);

        //ca spinner
        String[] caPar = getResources().getStringArray(R.array.caValues);
        ArrayAdapter<String> caAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, caPar);
        caAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        caSpinner.setAdapter(caAdapter);

        //thal Spinner
        String[] thalPar = getResources().getStringArray(R.array.thalValues);
        ArrayAdapter<String> thalAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, thalPar);
        thalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        thalSpinner.setAdapter(thalAdapter);

        //Image Spinner
        String[] imagePar = getResources().getStringArray(R.array.imageValues);
        ArrayAdapter<String> imageAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, imagePar);
        imageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSpinner.setAdapter(imageAdapter);


        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchBarClick();
            }
        });

        sendDataToFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToFirebase();
            }
        });

    }

    //For item clicked on parameter search
    public void searchBarClick() {
        String searchText = searchView.getText().toString();
        if(searchText.equals("Age")) {
            age.setVisibility(View.VISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Sex")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.VISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Chest Pain")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.VISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Resting Blood Pressure")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.VISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Serum Cholesterol")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.VISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Fasting Blood Sugar Larger")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.VISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Resting Electroc. Result")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.VISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Maximum Heart Rate Achieved")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.VISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Exercise Induced Angina")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.VISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("ST Depression Induc. ex.")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.VISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Slope of Peak Exercise ST")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.VISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Number of Major Vessel")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.VISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Thalassemia")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.VISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Pregnancies")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.VISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Glucose")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.VISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Blood Pressure")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.VISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Skin Thickness")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.VISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Insulin")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.VISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("BMI")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.VISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        } else if(searchText.equals("Diabetes Pedigree Function")) {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.VISIBLE);
        } else {
            age.setVisibility(View.INVISIBLE);
            sex.setVisibility(View.INVISIBLE);
            cp.setVisibility(View.INVISIBLE);
            trestbps.setVisibility(View.INVISIBLE);
            chol.setVisibility(View.INVISIBLE);
            fbs.setVisibility(View.INVISIBLE);
            restecg.setVisibility(View.INVISIBLE);
            thalach.setVisibility(View.INVISIBLE);
            exang.setVisibility(View.INVISIBLE);
            oldpeak.setVisibility(View.INVISIBLE);
            slope.setVisibility(View.INVISIBLE);
            ca.setVisibility(View.INVISIBLE);
            thal.setVisibility(View.INVISIBLE);
            imageSection.setVisibility(View.INVISIBLE);

            pregnancies.setVisibility(View.INVISIBLE);
            glucose.setVisibility(View.INVISIBLE);
            bloodPressure.setVisibility(View.INVISIBLE);
            skinThick.setVisibility(View.INVISIBLE);
            bmi.setVisibility(View.INVISIBLE);
            insulin.setVisibility(View.INVISIBLE);
            diabetes.setVisibility(View.INVISIBLE);
        }
    }


    public void clickAge(View view) {
        String data = valueAge.getText().toString();
        neuralData += "age:"+data+"#";
        submitAge.setEnabled(false);
    }

    public void clickSex(View view) {
        String data = sexSpinner.getSelectedItem().toString();
        neuralData += "sex:"+data+"#";
        submitSex.setEnabled(false);
    }

    public void clickCp(View view) {
        String data = cpSpinner.getSelectedItem().toString();
        neuralData += "cp:"+data+"#";
        submitCp.setEnabled(false);
    }

    public void clickBps(View view) {
        String data = valueBps.getText().toString();
        neuralData += "trestbps:"+data+"#";
        submitTrestbps.setEnabled(false);
    }

    public void clickChol(View view) {
        String data = valueChol.getText().toString();
        neuralData += "chol:"+data+"#";
        submitChol.setEnabled(false);
    }

    public void clickFbs(View view) {
        String data = fbsSpinner.getSelectedItem().toString();
        neuralData += "fbs:"+data+"#";
        submitFbs.setEnabled(false);
    }

    public void clickRestecg(View view) {
        String data = restecgSpinner.getSelectedItem().toString();
        neuralData += "restecg:"+data+"#";
        submitRestecg.setEnabled(false);
    }

    public void clickThalach(View view) {
        String data = valueThalach.getText().toString();
        neuralData += "thalach:"+data+"#";
        submitThalach.setEnabled(false);
    }

    public void clickExang(View view) {
        String data = exangSpinner.getSelectedItem().toString();
        neuralData += "exang:"+data+"#";
        submitExang.setEnabled(false);
    }

    public void clickOldpeak(View view) {
        String data = valueOldpeak.getText().toString();
        neuralData += "oldpeak:"+data+"#";
        submitOldpeak.setEnabled(false);
    }

    public void clickSlope(View view) {
        String data = slopeSpinner.getSelectedItem().toString();
        neuralData += "slope:"+data+"#";
        submitSlope.setEnabled(false);
    }

    public void clickCa(View view) {
        String data = caSpinner.getSelectedItem().toString();
        neuralData += "ca:"+data+"#";
        submitCa.setEnabled(false);
    }

    public void clickThal(View view) {
        String data = thalSpinner.getSelectedItem().toString();
        neuralData += "thal:"+data+"#";
        submitThal.setEnabled(false);
    }


    //The main code for database (Sends to neural network)
    public void sendToFirebase() {

        String imageCategory = imageSpinner.getSelectedItem().toString();
        final Intent intent = new Intent(MainActivity.this, ResultActivity.class);

        if(filepath!=null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Data...");
            progressDialog.setMessage("In-Progress...");
            progressDialog.show();

            final StorageReference sRef = storageReference.child("images/"+ UUID.randomUUID().toString());

            if(imageCategory.equals("Medical CBC Report")) {
                sRef.putFile(filepath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        return sRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Uri downUri = task.getResult();
                            progressDialog.dismiss();
                            neuralData+="imageTag:cbc#"+"imageUrl:"+downUri+"#";
                            databaseReference.child("joel").setValue(neuralData);
                            startActivity(intent);
                        }
                    }
                });
            } else if(imageCategory.equals("Chest X-Ray")){
                sRef.putFile(filepath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        return sRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Uri downUri = task.getResult();
                            progressDialog.dismiss();
                            neuralData+="imageTag:xray#"+"imageUrl:"+downUri+"#";
                            databaseReference.child("joel").setValue(neuralData);
                            startActivity(intent);
                        }
                    }
                });
            } else if(imageCategory.equals("Breast Histopathology")) {
                sRef.putFile(filepath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        return sRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Uri downUri = task.getResult();
                            progressDialog.dismiss();
                            neuralData+="imageTag:breast#"+"imageUrl:"+downUri+"#";
                            databaseReference.child("joel").setValue(neuralData);
                            startActivity(intent);
                        }
                    }
                });
            }

        }

        if(!neuralData.isEmpty()) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Data...");
            progressDialog.setMessage("In-Progress...");
            progressDialog.show();
            databaseReference.child("joel").setValue(neuralData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.dismiss();
                    startActivity(intent);
                }
            });

        }

        if(neuralData.isEmpty()&&filepath==null) {
            Toast.makeText(this,"You need to input something!", Toast.LENGTH_LONG).show();
        }
    }

    public void chooseImage(View view) {
        age.setVisibility(View.INVISIBLE);
        sex.setVisibility(View.INVISIBLE);
        cp.setVisibility(View.INVISIBLE);
        trestbps.setVisibility(View.INVISIBLE);
        chol.setVisibility(View.INVISIBLE);
        fbs.setVisibility(View.INVISIBLE);
        restecg.setVisibility(View.INVISIBLE);
        thalach.setVisibility(View.INVISIBLE);
        exang.setVisibility(View.INVISIBLE);
        oldpeak.setVisibility(View.INVISIBLE);
        slope.setVisibility(View.INVISIBLE);
        ca.setVisibility(View.INVISIBLE);
        thal.setVisibility(View.INVISIBLE);
        imageSection.setVisibility(View.VISIBLE);
    }

    public void uploadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData()!=null) {
            filepath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearField(View view) {
        age.setVisibility(View.INVISIBLE);
        sex.setVisibility(View.INVISIBLE);
        cp.setVisibility(View.INVISIBLE);
        trestbps.setVisibility(View.INVISIBLE);
        chol.setVisibility(View.INVISIBLE);
        fbs.setVisibility(View.INVISIBLE);
        restecg.setVisibility(View.INVISIBLE);
        thalach.setVisibility(View.INVISIBLE);
        exang.setVisibility(View.INVISIBLE);
        oldpeak.setVisibility(View.INVISIBLE);
        slope.setVisibility(View.INVISIBLE);
        ca.setVisibility(View.INVISIBLE);
        thal.setVisibility(View.INVISIBLE);
        imageSection.setVisibility(View.INVISIBLE);

        neuralData = "";

        submitAge.setEnabled(true);
        submitSex.setEnabled(true);
        submitCp.setEnabled(true);
        submitTrestbps.setEnabled(true);
        submitChol.setEnabled(true);
        submitFbs.setEnabled(true);
        submitRestecg.setEnabled(true);
        submitThalach.setEnabled(true);
        submitExang.setEnabled(true);
        submitOldpeak.setEnabled(true);
        submitSlope.setEnabled(true);
        submitCa.setEnabled(true);
        submitThal.setEnabled(true);


        valueAge.setText("");
        valueBps.setText("");
        valueChol.setText("");
        valueThalach.setText("");
        valueOldpeak.setText("");

        searchView.setText("");


    }

    public void clickPreg(View view) {
        String data = valuePreg.getText().toString();
        neuralData += "preg:"+data+"#";
        submitPreg.setEnabled(false);
    }

    public void clickGlucose(View view) {
        String data = valueGlucose.getText().toString();
        neuralData += "gluc:"+data+"#";
        submitGlucose.setEnabled(false);
    }

    public void clickBlood(View view) {
        String data = valueBlood.getText().toString();
        neuralData += "blood:"+data+"#";
        submitBlood.setEnabled(false);
    }

    public void clickThick(View view) {
        String data = valueSkinThick.getText().toString();
        neuralData += "thick:"+data+"#";
        submitSkinThick.setEnabled(false);
    }

    public void clickInsulin(View view) {
        String data = valueInsulin.getText().toString();
        neuralData += "insulin:"+data+"#";
        submitInsulin.setEnabled(false);
    }

    public void clickBmi(View view) {
        String data = valueBmi.getText().toString();
        neuralData += "bmi:"+data+"#";
        submitBmi.setEnabled(false);
    }

    public void clickDiabetes(View view) {
        String data = valueDiabetes.getText().toString();
        neuralData += "diabetes:"+data+"#";
        submitDiabetes.setEnabled(false);
    }
}
