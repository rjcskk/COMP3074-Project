package com.example.myrestaurantdiary.ui.maps;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.myrestaurantdiary.DBHandler;
import com.example.myrestaurantdiary.Restaurant;
import com.example.myrestaurantdiary.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import com.example.myrestaurantdiary.R;

public class MapsFragment extends Fragment {

    private FragmentMapsBinding binding;
    private DBHandler dbHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapsViewModel mapsViewModel =
                new ViewModelProvider(this).get(MapsViewModel.class);

        binding = FragmentMapsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHandler = new DBHandler(getContext());

        List<Restaurant> restaurants = dbHandler.getAllRestaurant();

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        OnMapReadyCallback callback = new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                for (Restaurant restaurant : restaurants) {
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(restaurant.getAddress(), 1);
                        if (!addresses.isEmpty()) {
                            Address address = addresses.get(0);
                            LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(location).title(restaurant.getName()));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!restaurants.isEmpty()) {
                    try {
                        Address address = geocoder.getFromLocationName(restaurants.get(0).getAddress(), 1).get(0);
                        LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}