package com.example.myrestaurantdiary.ui.newPlace;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myrestaurantdiary.DBHandler;
import com.example.myrestaurantdiary.R;
import com.example.myrestaurantdiary.Restaurant;
import com.example.myrestaurantdiary.databinding.FragmentNewplaceBinding;
import com.example.myrestaurantdiary.ui.restaurantList.RestaurantListFragment;

public class NewPlaceFragment extends Fragment {

    private FragmentNewplaceBinding binding;
    DBHandler dbHandler;
    EditText et_name, et_address, et_phone, et_desc, et_tags;
    Button add;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        NewPlaceViewModel newPlaceViewModel = new ViewModelProvider(this)
                .get(NewPlaceViewModel.class);

        binding = FragmentNewplaceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNewplace;
        newPlaceViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        dbHandler = new DBHandler(getContext());

        et_name = binding.textNameInput;
        et_address = binding.textAddressInput;
        et_phone = binding.textPhoneInput;
        et_desc = binding.textDescriptionInput;
        et_tags = binding.textTagInput;

        add = binding.addButton;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String address = et_address.getText().toString();
                String phone = et_phone.getText().toString();
                String desc = et_desc.getText().toString();
                String tags = et_tags.getText().toString();

                if(name.trim().length() > 0 && address.trim().length() > 0
                        && phone.trim().length() > 0 && desc.trim().length() > 0
                        && tags.trim().length() > 0 ){
                    Restaurant restaurant = new Restaurant(name, address, phone, desc, tags);
                    dbHandler.addRestaurant(restaurant);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("New restaurant added successfully")
                            .setPositiveButton("OK", null)
                            .show();
                } else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Please fill all the fields")
                            .setNegativeButton("OK", null)
                            .show();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}