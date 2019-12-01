package com.team.seller.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.team.seller.R;
import com.team.seller.commons.IOnGetDataListener;
import com.team.seller.models.Sell;
import com.team.seller.repositories.SellRepository;

public class SlideshowFragment extends Fragment {


    GoogleMap mGoogleMap;
    private SupportMapFragment mSupportMapFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        final SellRepository SellRepository = new SellRepository();

        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapwhere);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.mapwhere, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null)
        {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override public void onMapReady(final GoogleMap googleMap) {
                    if (googleMap != null) {

                        googleMap.getUiSettings().setAllGesturesEnabled(true);
                        SellRepository.Read(new IOnGetDataListener() {

                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {

                                for(Sell entity : SellRepository.getEntites()){
                                    googleMap.addMarker(new MarkerOptions().position(new LatLng(entity.getLatitude(), entity.getLongitude()))
                                            .title("Vendido\n 2")).setTag(0);

                                }

                                LatLng marker_latlng  = new LatLng(-19.93227755263461,-43.93133278436153);// MAKE THIS WHATEVER YOU WANT

                                CameraPosition cameraPosition = new CameraPosition.Builder().target(marker_latlng).zoom(13.0f).build();
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                                googleMap.moveCamera(cameraUpdate);
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
            });
        }
        return root;
    }
}