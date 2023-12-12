package com.example.myrestaurantdiary.ui.restaurantList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.myrestaurantdiary.DBHandler;
import com.example.myrestaurantdiary.MainActivity;
import com.example.myrestaurantdiary.R;
import com.example.myrestaurantdiary.Restaurant;
import com.example.myrestaurantdiary.databinding.FragmentRestaurantlistBinding;
import java.util.ArrayList;
import java.util.List;

public class RestaurantListFragment extends Fragment {

    private FragmentRestaurantlistBinding binding;
    private DBHandler dbHandler;
    private ListView restaurantList;
    private EditText searchText;
    private Button search;
    private List<Restaurant> restaurants;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RestaurantListViewModel restaurantListViewModel =
                new ViewModelProvider(this).get(RestaurantListViewModel.class);

        binding = FragmentRestaurantlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRestaurantlist;
        restaurantListViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        dbHandler = new DBHandler(getContext());

        restaurantList = binding.restaurantList;
        searchText = binding.searchEditText;
        search = binding.searchButton;

        restaurants = new ArrayList<>();
        restaurants = dbHandler.getAllRestaurant();

        String[] nameArray = new String[restaurants.size()];

        for(int i=0; i<restaurants.size(); i++){
            nameArray[i] = restaurants.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, nameArray);
        restaurantList.setAdapter(adapter);

        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = restaurants.get(position);

                Bundle bundle = new Bundle();
                bundle.putString("Name", restaurant.getName());
                bundle.putString("Address", restaurant.getAddress());
                bundle.putString("Phone Number", restaurant.getPhoneNumber());
                bundle.putString("Description", restaurant.getDescription());
                bundle.putString("Tags", restaurant.getTags());

                Navigation.findNavController(view)
                        .navigate(R.id.action_nav_restaurantList_to_nav_restaurantFragment, bundle);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchText.getText().toString().trim();
                searchRestaurants(searchTerm);
            }
        });

        return root;
    }

    private void searchRestaurants(String searchTerm) {
        List<Restaurant> filteredList = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredList.add(restaurant);
            }
        }

        updateListView(filteredList);
    }

    private void updateListView(List<Restaurant> updatedList) {
        String[] nameArray = new String[updatedList.size()];

        for (int i = 0; i < updatedList.size(); i++) {
            nameArray[i] = updatedList.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, nameArray);
        restaurantList.setAdapter(adapter);

        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = updatedList.get(position);

                // Use the getRestaurant method to get the full details of the selected restaurant
                Restaurant selectedRestaurant = dbHandler.getRestaurant(restaurant.getId());

                Bundle bundle = new Bundle();
                bundle.putString("Name", selectedRestaurant.getName());
                bundle.putString("Address", selectedRestaurant.getAddress());
                bundle.putString("Phone Number", selectedRestaurant.getPhoneNumber());
                bundle.putString("Description", selectedRestaurant.getDescription());
                bundle.putString("Tags", selectedRestaurant.getTags());
                bundle.putFloat("Rating", restaurant.getRating());

                Navigation.findNavController(view)
                        .navigate(R.id.action_nav_restaurantList_to_nav_restaurantFragment, bundle);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}