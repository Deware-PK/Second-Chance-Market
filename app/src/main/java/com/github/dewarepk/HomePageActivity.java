package com.github.dewarepk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dewarepk.model.ItemData;
import com.github.dewarepk.model.ItemType;
import com.github.dewarepk.model.widget.ItemAdapter;
import com.github.dewarepk.util.SimpleUtil;
import com.github.dewarepk.util.ValidateUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        ValidateUtil.checkIntegrity(this.getApplicationContext(), this);

        ArrayList<ItemData> data = new ArrayList<>();
        loadData(data);

        TextView fullNameDisplay = this.findViewById(R.id.fullname_display);

        SimpleUtil.getUserFullName(this.getApplicationContext(), fullNameDisplay::setText);

        LinearLayout profileButton = this.findViewById(R.id.me_btn);

        profileButton.setOnClickListener(aVoid -> this.startActivity(new Intent(HomePageActivity.this, TopUpActivity.class)));



        ItemAdapter adapter = new ItemAdapter(this, data);
        RecyclerView recyclerView = this.findViewById(R.id.recommendView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

    }

    private void loadData(List<ItemData> data) {
        data.add(new ItemData("0" , "shirt_holder", 5, 4.5f, 50.0 , ItemType.CLOTHE));
        data.add(new ItemData("1" , "https://play-lh.googleusercontent.com/QPo012nDmGHkDLe7MtXkP4J7y_W_MZ8VgfyY6Iqx79ePl_n-0o6cDFLKI0ATdMEqea2Gnq_WtQEY32U5Zg", 5, 4.5f, 50.0 , ItemType.CLOTHE));
        data.add(new ItemData("2" , "box", 2, 4.5f, 150.0 , ItemType.CLOTHE));
        data.add(new ItemData("3" , "user", 1, 4.5f, 500.0 , ItemType.CLOTHE));
        data.add(new ItemData("4" , "box", 2, 4.5f, 150.0 , ItemType.CLOTHE));
        data.add(new ItemData("5" , "user", 1, 4.5f, 500.0 , ItemType.CLOTHE));
        data.add(new ItemData("6" , "box", 2, 4.5f, 150.0 , ItemType.CLOTHE));
        data.add(new ItemData("7" , "user", 1, 4.5f, 500.0 , ItemType.CLOTHE));
        data.add(new ItemData("8" , "box", 2, 4.5f, 150.0 , ItemType.CLOTHE));
        data.add(new ItemData("9" , "user", 1, 4.5f, 500.0 , ItemType.CLOTHE));
        data.add(new ItemData("10" , "user", 1, 4.5f, 500.0 , ItemType.CLOTHE));
        data.add(new ItemData("11" , "user", 1, 4.5f, 500.0 , ItemType.CLOTHE));
        data.add(new ItemData("12" , "user", 1, 4.5f, 500.0 , ItemType.CLOTHE));
        data.add(new ItemData("13" , "user", 1, 4.5f, 500.0 , ItemType.CLOTHE));
        data.add(new ItemData("14" , "user", 1, 4.5f, 500.0 , ItemType.CLOTHE));
        data.add(new ItemData("15" , "user", 1, 4.5f, 500.0 , ItemType.CLOTHE));
    }
}