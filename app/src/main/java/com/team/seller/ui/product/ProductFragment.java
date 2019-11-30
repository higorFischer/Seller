package com.team.seller.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.team.seller.R;
import com.team.seller.commons.IOnGetDataListener;
import com.team.seller.models.Product;
import com.team.seller.models.User;
import com.team.seller.repositories.ProductRepository;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ProductFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    Button CreateButton;
    Button DeleteButton;
    Button ReadButton;
    Button UpdateButton;
    Button FindButton;

    TextView productTextView;
    TextView sizeTextView;
    TextView descriptionTextView;
    TextView dataTextView;

    EditText ProductText;
    EditText SizeText;
    EditText IdText;
    EditText DescriptionText;

    ListView ProductView;
    ArrayAdapter<Product> Adapter;
    ArrayAdapter<String> AdapterString;
    ArrayAdapter<CharSequence> AdapterChar;
    Spinner spinner;
    String opcoes[]={"Produto 1", "Produto 2", "Produto 3"};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);
        spinner = (Spinner) root.findViewById(R.id.productsSpinner);
        //AdapterString=new ArrayAdapter<String>(ProductFragment,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.options));
        CreateButton = (Button) root.findViewById(R.id.createButton);
        DeleteButton = (Button) root.findViewById(R.id.deleteButton);
        IdText= (EditText) root.findViewById(R.id.idText);
        ReadButton = (Button) root.findViewById(R.id.readButton);
        ProductView = (ListView) root.findViewById(R.id.productList);
        ProductText = (EditText) root.findViewById(R.id.productText);
        SizeText = (EditText) root.findViewById(R.id.sizeText);
        DescriptionText = (EditText) root.findViewById(R.id.descriptionText);
        UpdateButton = (Button) root.findViewById(R.id.updateButton);
        productTextView = (TextView) root.findViewById(R.id.product);
        sizeTextView = (TextView) root.findViewById(R.id.size);
        descriptionTextView = (TextView) root.findViewById(R.id.description);
        dataTextView = (TextView) root.findViewById(R.id.data);
        FindButton = (Button) root.findViewById(R.id.find);

        final ProductRepository ProductRepository = new ProductRepository();

        AdapterString=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.options));
        AdapterChar= ArrayAdapter.createFromResource(getContext(),R.array.options,android.R.layout.simple_list_item_1);
        //AdapterString.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AdapterChar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(AdapterString);

        spinner.setOnItemSelectedListener(this);

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HasFillAllFields()){
                    ProductRepository.Create(new Product(ProductText.getText().toString(), SizeText.getText().toString(),DescriptionText.getText().toString()));
                    Toast.makeText(getContext().getApplicationContext(), "Produto cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext().getApplicationContext(), "Preencha todos so campos", Toast.LENGTH_SHORT).show();

            }
        });

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductRepository.Read(new IOnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, ProductRepository.getEntites());
                        ProductView.setAdapter(Adapter);
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
        return  !ProductText.getText().toString().matches("") &&
                !SizeText.getText().toString().matches("") &&
                !DescriptionText.getText().toString().matches("");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        switch(text){
            case "Create":
                IdText.setVisibility(View.GONE);
                DeleteButton.setVisibility(View.GONE);
                ReadButton.setVisibility(View.GONE);
                ProductView.setVisibility(View.GONE);
                dataTextView.setVisibility(View.GONE);
                productTextView.setVisibility(View.GONE);
                sizeTextView.setVisibility(View.GONE);
                descriptionTextView.setVisibility(View.GONE);
                ProductText.setVisibility(View.VISIBLE);
                SizeText.setVisibility(View.VISIBLE);
                DescriptionText.setVisibility(View.VISIBLE);
                CreateButton.setVisibility(View.VISIBLE);
                UpdateButton.setVisibility(View.GONE);
                FindButton.setVisibility(View.GONE);
                break;
            case "Update":
                ProductText.setVisibility(View.VISIBLE);
                SizeText.setVisibility(View.VISIBLE);
                DescriptionText.setVisibility(View.VISIBLE);
                CreateButton.setVisibility(View.GONE);
                dataTextView.setVisibility(View.GONE);
                productTextView.setVisibility(View.GONE);
                sizeTextView.setVisibility(View.GONE);
                descriptionTextView.setVisibility(View.GONE);
                DeleteButton.setVisibility(View.GONE);
                ReadButton.setVisibility(View.GONE);
                ProductView.setVisibility(View.GONE);
                IdText.setVisibility(View.VISIBLE);
                UpdateButton.setVisibility(View.VISIBLE);
                FindButton.setVisibility(View.VISIBLE);
                break;
            case "Delete":
                ProductText.setVisibility(View.GONE);
                SizeText.setVisibility(View.GONE);
                DescriptionText.setVisibility(View.GONE);
                CreateButton.setVisibility(View.GONE);
                ReadButton.setVisibility(View.GONE);
                ProductView.setVisibility(View.GONE);
                UpdateButton.setVisibility(View.GONE);
                IdText.setVisibility(View.VISIBLE);
                DeleteButton.setVisibility(View.VISIBLE);
                dataTextView.setVisibility(View.VISIBLE);
                productTextView.setVisibility(View.VISIBLE);
                sizeTextView.setVisibility(View.VISIBLE);
                descriptionTextView.setVisibility(View.VISIBLE);
                FindButton.setVisibility(View.VISIBLE);
                break;
            case "List":
                ProductText.setVisibility(View.GONE);
                SizeText.setVisibility(View.GONE);
                DescriptionText.setVisibility(View.GONE);
                CreateButton.setVisibility(View.GONE);
                DeleteButton.setVisibility(View.GONE);
                IdText.setVisibility(View.GONE);
                UpdateButton.setVisibility(View.GONE);
                ReadButton.setVisibility(View.VISIBLE);
                ProductView.setVisibility(View.VISIBLE);
                dataTextView.setVisibility(View.GONE);
                productTextView.setVisibility(View.VISIBLE);
                sizeTextView.setVisibility(View.VISIBLE);
                descriptionTextView.setVisibility(View.VISIBLE);
                FindButton.setVisibility(View.GONE);
                break;
                default:
                    IdText.setVisibility(View.GONE);
                    ProductText.setVisibility(View.GONE);
                    SizeText.setVisibility(View.GONE);
                    DescriptionText.setVisibility(View.GONE);
                    CreateButton.setVisibility(View.GONE);
                    DeleteButton.setVisibility(View.GONE);
                    ReadButton.setVisibility(View.GONE);
                    ProductView.setVisibility(View.GONE);
                    IdText.setVisibility(View.GONE);
                    UpdateButton.setVisibility(View.GONE);
                    dataTextView.setVisibility(View.GONE);
                    productTextView.setVisibility(View.GONE);
                    sizeTextView.setVisibility(View.GONE);
                    descriptionTextView.setVisibility(View.GONE);
                    FindButton.setVisibility(View.GONE);
                    break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}