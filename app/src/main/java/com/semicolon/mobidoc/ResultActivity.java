package com.semicolon.mobidoc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    private LinearLayout resultSection;
    private TextView resultNeural;
    private TextView precationHead;
    private TextView precationDetail;
    private ScrollView scrollResult;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("neural");

        resultNeural = findViewById(R.id.resultNeural);
        precationDetail = findViewById(R.id.precautionDetail);
        precationHead = findViewById(R.id.precationHead);
        resultSection = findViewById(R.id.resultSection);
        scrollResult = findViewById(R.id.scrollResult);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Computing Data...");
        progressDialog.setMessage("In-Progress");
        progressDialog.show();
        databaseReference.child("result").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String neuralData = snapshot.getValue().toString();
                if(neuralData.equals("0")) {
                    progressDialog.dismiss();
                    scrollResult.setVisibility(View.VISIBLE);
                    resultSection.setVisibility(View.VISIBLE);
                    resultNeural.setVisibility(View.VISIBLE);
                    resultNeural.setText("Normal Chest (No Congestion)");
                    databaseReference.child("result").setValue(-1);
                }
                else if(neuralData.equals("1")) {
                    progressDialog.dismiss();
                    scrollResult.setVisibility(View.VISIBLE);
                    resultSection.setVisibility(View.VISIBLE);
                    resultNeural.setVisibility(View.VISIBLE);
                    precationHead.setVisibility(View.VISIBLE);
                    precationDetail.setVisibility(View.VISIBLE);
                    resultNeural.setText("Possibility of Pneumonia");
                    precationDetail.setText("\u2022 Wash your hands regularly, especially after you go to the bathroom and before you eat.\n" +
                            "\u2022 Eat right, with plenty of fruits and vegetables.\n" +
                            "\u2022 Exercise.\n" +
                            "\u2022 Get enough sleep.\n" +
                            "\u2022 Quit smoking.\n" +
                            "\u2022 Stay away from sick people, if possible.");
                    databaseReference.child("result").setValue(-1);
                } else if(neuralData.equals("2")) {
                    progressDialog.dismiss();
                    scrollResult.setVisibility(View.VISIBLE);
                    resultSection.setVisibility(View.VISIBLE);
                    resultNeural.setVisibility(View.VISIBLE);
                    resultNeural.setText("Normal Breast Histopathology Image");
                    databaseReference.child("result").setValue(-1);
                    databaseReference.child("result").setValue(-1);
                } else if(neuralData.equals("3")) {
                    progressDialog.dismiss();
                    scrollResult.setVisibility(View.VISIBLE);
                    resultSection.setVisibility(View.VISIBLE);
                    resultNeural.setVisibility(View.VISIBLE);
                    precationHead.setVisibility(View.VISIBLE);
                    precationDetail.setVisibility(View.VISIBLE);
                    resultNeural.setText("Invasive Ductal Carcinoma Possibility");
                    precationDetail.setText("\u2022 limit alcohol\n" +
                            "\u2022 dont smoke (particularly for premenopausal women).\n" +
                            "\u2022 control your weight\n" +
                            "\u2022 be physically active:\n" +
                            "\u2022 Breast-feed\n" +
                            "\u2022 Limit dose and duration of hormone therapy. \n" +
                            "\u2022 Avoid exposure to radiation and environmental pollution. ");
                    databaseReference.child("result").setValue(-1);
                } else if(neuralData.equals("Heart:4")) {
                    progressDialog.dismiss();
                    scrollResult.setVisibility(View.VISIBLE);
                    resultSection.setVisibility(View.VISIBLE);
                    resultNeural.setVisibility(View.VISIBLE);
                    resultNeural.setText("Very less possibility of heart disease");
                    databaseReference.child("result").setValue(-1);
                } else if(neuralData.equals("Heart:5")) {
                    progressDialog.dismiss();
                    scrollResult.setVisibility(View.VISIBLE);
                    resultSection.setVisibility(View.VISIBLE);
                    resultNeural.setVisibility(View.VISIBLE);
                    precationHead.setVisibility(View.VISIBLE);
                    precationDetail.setVisibility(View.VISIBLE);
                    resultNeural.setText("Less possibility of heart disease");
                    precationDetail.setText("\u2022 Don't smoke or use tobacco\n" +
                            "\u2022 Exercise for about 30 minutes on most days of the week\n" +
                            "\u2022 Eat a heart-healthy diet\n" +
                            "\u2022 Maintain a healthy weight\n" +
                            "\u2022 Get enough quality sleep\n" +
                            "\u2022 Manage stress\n" +
                            "\u2022 Get regular health screenings");
                    databaseReference.child("result").setValue(-1);
                } else if(neuralData.equals("Heart:6")) {
                    progressDialog.dismiss();
                    scrollResult.setVisibility(View.VISIBLE);
                    resultSection.setVisibility(View.VISIBLE);
                    resultNeural.setVisibility(View.VISIBLE);
                    precationHead.setVisibility(View.VISIBLE);
                    precationDetail.setVisibility(View.VISIBLE);
                    resultNeural.setText("More than average possibility of heart disease");
                    precationDetail.setText("\u2022 Don't smoke or use tobacco\n" +
                            "\u2022 Exercise for about 30 minutes on most days of the week\n" +
                            "\u2022 Eat a heart-healthy diet\n" +
                            "\u2022 Maintain a healthy weight\n" +
                            "\u2022 Get enough quality sleep\n" +
                            "\u2022 Manage stress\n" +
                            "\u2022 Get regular health screenings");
                    databaseReference.child("result").setValue(-1);
                } else if(neuralData.equals("Heart:7")) {
                    progressDialog.dismiss();
                    scrollResult.setVisibility(View.VISIBLE);
                    resultSection.setVisibility(View.VISIBLE);
                    resultNeural.setVisibility(View.VISIBLE);
                    precationHead.setVisibility(View.VISIBLE);
                    precationDetail.setVisibility(View.VISIBLE);
                    resultNeural.setText("Maximum possibility of heart disease");
                    precationDetail.setText("\u2022 Don't smoke or use tobacco\n" +
                            "\u2022 Exercise for about 30 minutes on most days of the week\n" +
                            "\u2022 Eat a heart-healthy diet\n" +
                            "\u2022 Maintain a healthy weight\n" +
                            "\u2022 Get enough quality sleep\n" +
                            "\u2022 Manage stress\n" +
                            "\u2022 Get regular health screenings");
                    databaseReference.child("result").setValue(-1);
                } else if(neuralData.equals("8")) {
                    progressDialog.dismiss();
                    scrollResult.setVisibility(View.VISIBLE);
                    resultSection.setVisibility(View.VISIBLE);
                    resultNeural.setVisibility(View.VISIBLE);
                    resultNeural.setText("Normal blood report");
                    databaseReference.child("result").setValue(-1);
                } else if(neuralData.equals("9")) {
                    progressDialog.dismiss();
                    scrollResult.setVisibility(View.VISIBLE);
                    resultSection.setVisibility(View.VISIBLE);
                    resultNeural.setVisibility(View.VISIBLE);
                    precationHead.setVisibility(View.VISIBLE);
                    precationDetail.setVisibility(View.VISIBLE);
                    resultNeural.setText("Shows signs of anemia");
                    precationDetail.setText("\u2022 Eat plenty of iron-rich foods.\n" +
                            "\u2022 Eat and drink vitamin C-rich foods and drinks.\n" +
                            "\u2022 Avoid drinking tea or coffee with your meals, as they can affect iron absorption.\n" +
                            "\u2022 Get enough vitamin B12 and folic acid in your diet.");

                    databaseReference.child("result").setValue(-1);
                } else if(neuralData.equals("Diabetes:10")) {
                    progressDialog.dismiss();
                    scrollResult.setVisibility(View.VISIBLE);
                    resultSection.setVisibility(View.VISIBLE);
                    resultNeural.setVisibility(View.VISIBLE);
                    resultNeural.setText("No Diabetes");
                    databaseReference.child("result").setValue(-1);
                } else if(neuralData.equals("Diabetes:11")) {
                    progressDialog.dismiss();
                    scrollResult.setVisibility(View.VISIBLE);
                    resultSection.setVisibility(View.VISIBLE);
                    resultNeural.setVisibility(View.VISIBLE);
                    precationHead.setVisibility(View.VISIBLE);
                    precationDetail.setVisibility(View.VISIBLE);
                    resultNeural.setText("Diabetes found");
                    precationDetail.setText("\u2022 Cut Sugar and Refined Carbs From Your Diet\n" +
                            "\u2022 Work Out Regularly\n" +
                            "\u2022 Drink Water as Your Primary Beverage\n" +
                            "\u2022 Lose Weight If You're Overweight or Obese\n" +
                            "\u2022 Quit Smoking");

                    databaseReference.child("result").setValue(-1);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
