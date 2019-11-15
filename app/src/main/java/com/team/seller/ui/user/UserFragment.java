package com.team.seller.ui.user;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.team.seller.R;
import com.team.seller.commons.IOnGetDataListener;
import com.team.seller.models.Product;
import com.team.seller.models.User;
import com.team.seller.repositories.ProductRepository;
import com.team.seller.repositories.SellRepository;
import com.team.seller.repositories.UserRepository;

public class UserFragment extends Fragment {

    Button CreateButton;
    Button ReadButton;

    EditText NameText;
    EditText EmailText;
    EditText PasswordText;

    ListView UserView;
    ArrayAdapter<User> Adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        CreateButton = root.findViewById(R.id.createButton);
        ReadButton = root.findViewById(R.id.readButton);
        UserView = root.findViewById(R.id.userList);
        NameText = root.findViewById(R.id.nameText);
        EmailText = root.findViewById(R.id.emailText);
        PasswordText = root.findViewById(R.id.passwordText);

        final UserRepository UserRepository = new UserRepository();
        final ProductRepository ProductRepository = new ProductRepository();

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HasFillAllFields()){
                    UserRepository.Create(new User(NameText.getText().toString(), EmailText.getText().toString(),PasswordText.getText().toString()));
                    Toast.makeText(getContext().getApplicationContext(), "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext().getApplicationContext(), "Preencha todos so campos", Toast.LENGTH_SHORT).show();

            }
        });

        ReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRepository.Read(new IOnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, UserRepository.getEntites());
                        UserView.setAdapter(Adapter);
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

        return root;
    }

    public boolean HasFillAllFields(){
        return  !NameText.getText().toString().matches("") &&
                !EmailText.getText().toString().matches("") &&
                !PasswordText.getText().toString().matches("");
    }
}