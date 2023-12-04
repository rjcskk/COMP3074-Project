package com.example.myrestaurantdiary.ui.restaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myrestaurantdiary.R;
import com.example.myrestaurantdiary.databinding.FragmentRestaurantBinding;

import java.util.ArrayList;
import java.util.List;


public class RestaurantFragment extends Fragment {

    private FragmentRestaurantBinding binding;
    private ListView infoList;
    private List<String> additionalInfoList;
    private RestaurantViewModel restaurantViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        restaurantViewModel =
                new ViewModelProvider(this).get(RestaurantViewModel.class);

        binding = FragmentRestaurantBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRestaurant;
        restaurantViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("Name");
            additionalInfoList = new ArrayList<>();
            additionalInfoList.add("Address:\n" + args.getString("Address"));
            additionalInfoList.add("Phone Number:\n" + args.getString("Phone Number"));
            additionalInfoList.add("Description:\n" + args.getString("Description"));
            additionalInfoList.add("Tags:\n" + args.getString("Tags"));

            TextView nameTextView = root.findViewById(R.id.text_restaurant_name);
            nameTextView.setText(name);

            infoList = root.findViewById(R.id.info);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, additionalInfoList);
            infoList.setAdapter(adapter);

            RatingBar ratingBar = root.findViewById(R.id.ratingBar);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    restaurantViewModel.setRating(rating);
                }
            });
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}