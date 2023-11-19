package com.example.myrestaurantdiary.ui.restaurantList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.myrestaurantdiary.databinding.FragmentRestaurantlistBinding;

public class RestaurantListFragment extends Fragment {

private FragmentRestaurantlistBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        RestaurantListViewModel restaurantListViewModel =
                new ViewModelProvider(this).get(RestaurantListViewModel.class);

    binding = FragmentRestaurantlistBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textRestaurantlist;
        restaurantListViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}