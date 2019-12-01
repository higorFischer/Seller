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
import com.team.seller.models.Sell;
import com.team.seller.models.User;
import com.team.seller.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ProductFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    Button CreateButton;
    Button DeleteButton;
    Button ReadButton;
    Button UpdateButton;

    TextView productTextView;
    TextView sizeTextView;
    TextView descriptionTextView;

    EditText ProductText;
    EditText DescriptionText;

    ListView ProductView;
    ArrayAdapter<Product> Adapter;
    ArrayAdapter<String> AdapterString;
    ArrayAdapter<CharSequence> AdapterChar;
    Spinner spinner;

    Spinner ProductSpinner;
    String ProductSpinnerValue;
    ArrayList<String> ProductOptions = new ArrayList<String>();
    ArrayAdapter<Product> ProductAdapter;
    ArrayAdapter<String> ProductAdapterString;
    ArrayAdapter<CharSequence> ProductAdapterChar;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);
        spinner = (Spinner) root.findViewById(R.id.crudSpinner);
        ProductSpinner = (Spinner) root.findViewById(R.id.productsSpinner);
        CreateButton = (Button) root.findViewById(R.id.createButton);
        DeleteButton = (Button) root.findViewById(R.id.deleteButton);
        ReadButton = (Button) root.findViewById(R.id.readButton);
        ProductView = (ListView) root.findViewById(R.id.productList);
        ProductText = (EditText) root.findViewById(R.id.productText);
        DescriptionText = (EditText) root.findViewById(R.id.descriptionText);
        UpdateButton = (Button) root.findViewById(R.id.updateButton);
        productTextView = (TextView) root.findViewById(R.id.product);
        sizeTextView = (TextView) root.findViewById(R.id.size);
        descriptionTextView = (TextView) root.findViewById(R.id.description);

        final ProductRepository ProductRepository = new ProductRepository();

        AdapterString=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.options));
        AdapterChar= ArrayAdapter.createFromResource(getContext(),R.array.options,android.R.layout.simple_list_item_1);
        //AdapterString.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AdapterChar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        StartSpinner();

        spinner.setAdapter(AdapterString);
        spinner.setOnItemSelectedListener(this);

        ProductSpinner = root.findViewById(R.id.productsSpinner);
        ProductSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ProductSpinnerValue = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HasFillAllFields()){
                    ProductRepository.Create(new Product(ProductText.getText().toString(), DescriptionText.getText().toString()), new IOnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Toast.makeText(getContext().getApplicationContext(), "Produto cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                            StartSpinner();
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                }
                else
                    Toast.makeText(getContext().getApplicationContext(), "Preencha todos so campos", Toast.LENGTH_SHORT).show();

            }
        });

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductRepository.ReadByName(ProductSpinnerValue, new IOnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Product product = ProductRepository.getEntites().get(0);

                        if(!DescriptionText.getText().toString().matches(""));
                            product.setDescription(DescriptionText.getText().toString());

                        if(!ProductText.getText().toString().matches(""));
                            product.setName(ProductText.getText().toString());

                        ProductRepository.Delete(product, new IOnGetDataListener() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                ProductRepository.Create(product, new IOnGetDataListener() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        StartSpinner();
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

                        Toast.makeText(getContext().getApplicationContext(), "Produto cadastrado com sucesso", Toast.LENGTH_SHORT).show();
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

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ProductRepository.ReadByName(ProductSpinnerValue, new IOnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        ProductRepository.Delete(ProductRepository.getEntites().get(0), new IOnGetDataListener() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                Toast.makeText(getContext().getApplicationContext(), "Produto cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                                StartSpinner();
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
                !DescriptionText.getText().toString().matches("");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        StartSpinner();

        switch(text){
            case "Create":
                DeleteButton.setVisibility(View.GONE);
                ReadButton.setVisibility(View.GONE);
                ProductView.setVisibility(View.GONE);
                productTextView.setVisibility(View.GONE);
                sizeTextView.setVisibility(View.GONE);
                ProductSpinner.setVisibility(View.GONE);
                descriptionTextView.setVisibility(View.GONE);
                ProductText.setVisibility(View.VISIBLE);
                DescriptionText.setVisibility(View.VISIBLE);
                CreateButton.setVisibility(View.VISIBLE);
                UpdateButton.setVisibility(View.GONE);
                break;
            case "Update":
                ProductText.setVisibility(View.VISIBLE);
                DescriptionText.setVisibility(View.VISIBLE);
                ProductSpinner.setVisibility(View.VISIBLE);
                CreateButton.setVisibility(View.GONE);
                productTextView.setVisibility(View.GONE);
                sizeTextView.setVisibility(View.GONE);
                descriptionTextView.setVisibility(View.GONE);
                DeleteButton.setVisibility(View.GONE);
                ReadButton.setVisibility(View.GONE);
                ProductView.setVisibility(View.GONE);
                UpdateButton.setVisibility(View.VISIBLE);
                break;
            case "Delete":
                ProductText.setVisibility(View.GONE);
                DescriptionText.setVisibility(View.GONE);
                CreateButton.setVisibility(View.GONE);
                ReadButton.setVisibility(View.GONE);
                ProductView.setVisibility(View.GONE);
                UpdateButton.setVisibility(View.GONE);
                ProductSpinner.setVisibility(View.VISIBLE);
                DeleteButton.setVisibility(View.VISIBLE);
                productTextView.setVisibility(View.VISIBLE);
                sizeTextView.setVisibility(View.VISIBLE);
                descriptionTextView.setVisibility(View.VISIBLE);
                break;
            case "List":
                ProductText.setVisibility(View.GONE);
                DescriptionText.setVisibility(View.GONE);
                CreateButton.setVisibility(View.GONE);
                DeleteButton.setVisibility(View.GONE);
                ProductSpinner.setVisibility(View.GONE);
                UpdateButton.setVisibility(View.GONE);
                ReadButton.setVisibility(View.VISIBLE);
                ProductView.setVisibility(View.VISIBLE);
                productTextView.setVisibility(View.VISIBLE);
                sizeTextView.setVisibility(View.VISIBLE);
                descriptionTextView.setVisibility(View.VISIBLE);
                break;
            default:
                ProductText.setVisibility(View.GONE);
                ProductSpinner.setVisibility(View.GONE);
                DescriptionText.setVisibility(View.GONE);
                CreateButton.setVisibility(View.GONE);
                DeleteButton.setVisibility(View.GONE);
                ReadButton.setVisibility(View.GONE);
                ProductView.setVisibility(View.GONE);
                UpdateButton.setVisibility(View.GONE);
                productTextView.setVisibility(View.GONE);
                sizeTextView.setVisibility(View.GONE);
                descriptionTextView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void StartSpinner(){
        final ProductRepository ProductRepository = new ProductRepository();

        ProductRepository.Read(new IOnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                ProductOptions = new ArrayList();
                for(Product opt : ProductRepository.getEntites())
                    ProductOptions.add(opt.getName());

                ProductAdapterString = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, ProductOptions);
                ProductAdapterChar= ArrayAdapter.createFromResource(getContext(),R.array.options,android.R.layout.simple_list_item_1);
                ProductAdapterChar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ProductSpinner.setAdapter(ProductAdapterString);

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