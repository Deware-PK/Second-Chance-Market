package com.github.dewarepk;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dewarepk.util.ValidateUtil;

public class LocationAddActivity extends AppCompatActivity{

    private EditText address;
    private EditText apartment;
    private EditText subDistrict;
    private EditText district;
    private EditText province;
    private EditText postCode;
    private EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_location_add);

        ValidateUtil.checkIntegrity(this.getApplicationContext(), this);

        address = this.findViewById(R.id.editAddress1);
        apartment = this.findViewById(R.id.editTypehere);
        subDistrict = this.findViewById(R.id.editCity);
        district = this.findViewById(R.id.editDistrict);
        province = this.findViewById(R.id.editProvince);
        postCode = this.findViewById(R.id.editPostalCode);
        phoneNumber = this.findViewById(R.id.editnumber);




    }

}
