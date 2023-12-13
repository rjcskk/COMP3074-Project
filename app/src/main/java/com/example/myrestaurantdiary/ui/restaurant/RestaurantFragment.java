package com.example.myrestaurantdiary.ui.restaurant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private String restaurantAddress;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        restaurantViewModel =
                new ViewModelProvider(this).get(RestaurantViewModel.class);

        binding = FragmentRestaurantBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("Name");
            float rating = args.getFloat("Rating", 0.0f);
            Log.d("RestaurantFragment", "Received restaurant with rating: " + rating);
            RatingBar ratingBar = root.findViewById(R.id.ratingBar);
            ratingBar.setRating(rating);

            restaurantAddress = args.getString("Address");
            additionalInfoList = new ArrayList<>();
            additionalInfoList.add("Name:\n" + name);
            additionalInfoList.add("Address:\n" + restaurantAddress);
            additionalInfoList.add("Phone Number:\n" + args.getString("Phone Number"));
            additionalInfoList.add("Description:\n" + args.getString("Description"));
            additionalInfoList.add("Tags:\n" + args.getString("Tags"));

            infoList = root.findViewById(R.id.info);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, additionalInfoList);
            infoList.setAdapter(adapter);


            Button directionButton = root.findViewById(R.id.btn_direction);
            directionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Uri.encode(restaurantAddress));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
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