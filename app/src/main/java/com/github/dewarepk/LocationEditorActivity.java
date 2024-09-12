package com.github.dewarepk;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dewarepk.model.AddressHandler;
import com.github.dewarepk.model.FirestoreHandler;
import com.github.dewarepk.model.InvalidAddressCause;
import com.github.dewarepk.model.SecureAccess;
import com.github.dewarepk.util.ValidateUtil;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class LocationEditorActivity extends AppCompatActivity{

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
        setContentView(R.layout.activity_location_editor);

        ValidateUtil.checkIntegrity(this.getApplicationContext(), this);

        AddressHandler addressHandler = new AddressHandler();
        FirestoreHandler firestoreHandler = new FirestoreHandler();
        String userId;
        final String[] addressKey = new String[1];

        try {
            SecureAccess localFile = new SecureAccess(this.getApplicationContext(), "UserPreferences");
            userId = localFile.getValue("userId" , String.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        address = this.findViewById(R.id.editAddress1);
        apartment = this.findViewById(R.id.editTypehere);
        subDistrict = this.findViewById(R.id.editCity);
        district = this.findViewById(R.id.editDistrict);
        province = this.findViewById(R.id.editProvince);
        postCode = this.findViewById(R.id.editPostalCode);
        phoneNumber = this.findViewById(R.id.editnumber);
        AppCompatButton confirm = this.findViewById(R.id.confirm_button_address);

        firestoreHandler.getSpecificData(userId, "address").thenAccept(value -> {
            addressKey[0] = (String) value;
            addressHandler.getAddress((String) value).thenAccept(data -> {
                address.setText(data.getAddress());
                apartment.setText(data.getApartment());
                subDistrict.setText(data.getSubDistrict());
                district.setText(data.getDistrict());
                province.setText(data.getProvince());
                postCode.setText(data.getPostalCode());
                phoneNumber.setText(data.getPhoneNumber());
            });
        });

        confirm.setOnClickListener(aVoid -> {
            String postalCode = postCode.getText().toString();
            String phone = phoneNumber.getText().toString();
            InvalidAddressCause cause = ValidateUtil.validateAddress(postalCode , phone);

            if (cause == InvalidAddressCause.INVALID_POSTAL_CODE) {
                Toast.makeText(LocationEditorActivity.this.getApplicationContext(), "Postal code should be 5 digits.", Toast.LENGTH_SHORT).show();
                return;

            } else if (cause == InvalidAddressCause.INVALID_PHONE_START) {
                Toast.makeText(LocationEditorActivity.this.getApplicationContext(), "Phone number should start with '0'", Toast.LENGTH_SHORT).show();
                return;

            } else if (cause == InvalidAddressCause.INVALID_PHONE_NUMBER) {
                Toast.makeText(LocationEditorActivity.this.getApplicationContext() ,"Phone number should be 10 digits.", Toast.LENGTH_SHORT).show();
                return;
            }

            final Map<String, Object> map = new HashMap<>();
            map.put("address", address.getText().toString());
            map.put("apartment", apartment.getText().toString());
            map.put("district", district.getText().toString());
            map.put("phone", phone);
            map.put("postal", postalCode);
            map.put("province", province.getText().toString());
            map.put("sub-district", subDistrict.getText().toString());
            map.put("lastUpdated" , FieldValue.serverTimestamp());

            firestoreHandler.updateData("addresses", addressKey[0] , map);
            Toast.makeText(LocationEditorActivity.this.getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
        });


    }

}
