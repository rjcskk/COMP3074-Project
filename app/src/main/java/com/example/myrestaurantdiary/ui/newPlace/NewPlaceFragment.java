package com.example.myrestaurantdiary.ui.newPlace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.myrestaurantdiary.databinding.FragmentNewplaceBinding;

public class NewPlaceFragment extends Fragment {

private FragmentNewplaceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        NewPlaceViewModel newPlaceViewModel =
                new ViewModelProvider(this).get(NewPlaceViewModel.class);

    binding = FragmentNewplaceBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textNewplace;
        newPlaceViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}