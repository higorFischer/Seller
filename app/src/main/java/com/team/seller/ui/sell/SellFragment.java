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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.team.seller.R;
import com.team.seller.commons.IOnGetDataListener;
import com.team.seller.models.Product;
import com.team.seller.models.Sell;
import com.team.seller.models.User;
import com.team.seller.repositories.ProductRepository;
import com.team.seller.repositories.SellRepository;
import com.team.seller.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SellFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    LocationManager LocationManager;
    LocationListener LocationListener;
    Button LocationButton;
    Button CreateButton;
    Button NowButton;

    EditText LatText;
    EditText LongText;
    EditText PriceText;
    EditText QuantityText;
    EditText DayValue;
    EditText MonthValue;
    EditText YearValue;

    Spinner Spinner;
    ArrayAdapter<String> AdapterString;
    ArrayAdapter<CharSequence> AdapterChar;
    String SpinnerValue;
    ArrayList<String> opcoes = new ArrayList<String>();

    Spinner SellerSpinner;
    ArrayAdapter<String> SellerAdapterString;
    ArrayAdapter<CharSequence> SellerAdapterChar;
    String SellerSpinnerValue;
    ArrayList<String> SellerOpcoes = new ArrayList<String>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sell, container, false);

        LatText = root.findViewById(R.id.latValue);
        LongText = root.findViewById(R.id.lngValue);
        PriceText = root.findViewById(R.id.priceValue);
        QuantityText = root.findViewById(R.id.quantityValue);
        LocationButton = root.findViewById(R.id.locationButton);
        CreateButton = root.findViewById(R.id.createSell);

        NowButton = root.findViewById(R.id.useNow);
        DayValue = root.findViewById(R.id.dayValue);
        MonthValue = root.findViewById(R.id.monthValue);
        YearValue = root.findViewById(R.id.yearValue);

        CreateButtonListener();

        LocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Spinner = root.findViewById(R.id.productsSpinner);
        Spinner.setOnItemSelectedListener(this);

        SellerSpinner = root.findViewById(R.id.sellerSpinner);
        SellerSpinner.setOnItemSelectedListener(this);

        StartSpinner();
        StartSellerSpinner();

        NowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Brazil"));
                DayValue.setText(cal.get(Calendar.DAY_OF_MONTH)+"");
                MonthValue.setText(cal.get(Calendar.MONTH)+1+"");
                YearValue.setText(cal.get(Calendar.YEAR)+"");
            }
        });

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SpinnerValue = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void CreateButtonListener(){

        final SellRepository SellRepository = new SellRepository();
        final ProductRepository ProductRepository = new ProductRepository();

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductRepository.ReadByName("testeProduto", new IOnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {

                        Sell sell = new Sell(
                                SpinnerValue,
                                SellerSpinnerValue,
                                Double.parseDouble(LatText.getText().toString()),
                                Double.parseDouble(LongText.getText().toString()),
                                Double.parseDouble(PriceText.getText().toString()),
                                Double.parseDouble(QuantityText.getText().toString()),
                                new Date(Integer.parseInt(YearValue.getText().toString()),
                                        Integer.parseInt(MonthValue.getText().toString()) - 1,
                                        Integer.parseInt(DayValue.getText().toString())));

                        SellRepository.Create(sell, new IOnGetDataListener() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                Toast.makeText(getContext().getApplicationContext(), "Venda cadastrada com sucesso", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onFailure() {

                            }
                        });

                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });
    }

    public void StartSpinner(){
        final ProductRepository ProductRepository = new ProductRepository();

          ProductRepository.Read(new IOnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                for(Product opt : ProductRepository.getEntites())
                    opcoes.add(opt.getName());

                AdapterString = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, opcoes);
                AdapterChar= ArrayAdapter.createFromResource(getContext(),R.array.options,android.R.layout.simple_list_item_1);
                AdapterChar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner.setAdapter(AdapterString);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    public void StartSellerSpinner(){
        final UserRepository UserRepository = new UserRepository();

        SellerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SellerSpinnerValue = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        UserRepository.Read(new IOnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                for(User opt : UserRepository.getEntites())
                    SellerOpcoes.add(opt.getName());

                SellerAdapterString = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, SellerOpcoes);
                SellerAdapterChar= ArrayAdapter.createFromResource(getContext(),R.array.options,android.R.layout.simple_list_item_1);
                SellerAdapterChar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SellerSpinner.setAdapter(SellerAdapterString);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });
    }
}