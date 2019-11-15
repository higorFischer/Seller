package com.team.seller.ui.sell;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.team.seller.R;
import com.team.seller.models.Sell;
import com.team.seller.repositories.SellRepository;

public class SellFragment extends Fragment {

    LocationManager LocationManager;
    LocationListener LocationListener;
    Button LocationButton;
    Button CreateButton;

    EditText LatText;
    EditText LongText;
    EditText PriceText;
    EditText NameText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sell, container, false);

        LatText = root.findViewById(R.id.latValue);
        LongText = root.findViewById(R.id.lngValue);
        PriceText = root.findViewById(R.id.priceValue);
        NameText = root.findViewById(R.id.nameValue);
        LocationButton = root.findViewById(R.id.locationButton);
        CreateButton = root.findViewById(R.id.createSell);

        final SellRepository SellRepository = new SellRepository();


        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sell sell = new Sell(
                        NameText.getText().toString(),
                        Double.parseDouble(LatText.getText().toString()),
                        Double.parseDouble(LongText.getText().toString()),
                        Double.parseDouble(PriceText.getText().toString()));

                SellRepository.Create(sell);

                Toast.makeText(getContext().getApplicationContext(), "Venda cadastrada com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        LocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        LocationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                LatText.setText(""+location.getLatitude());
                LongText.setText(""+location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 10);
            return root;
        } else {
            configure_button();
        }
        return root;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void configure_button() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 10);
            return;
        }

        LocationButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                LocationManager.requestSingleUpdate("gps", LocationListener, null);
            }
        });
    }
}