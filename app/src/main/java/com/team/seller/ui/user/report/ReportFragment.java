package com.team.seller.ui.user.report;

import android.os.Build;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.core.Repo;
import com.team.seller.R;
import com.team.seller.commons.IOnGetDataListener;
import com.team.seller.models.Product;
import com.team.seller.models.Report;
import com.team.seller.models.Sell;
import com.team.seller.models.User;
import com.team.seller.repositories.ProductRepository;
import com.team.seller.repositories.SellRepository;
import com.team.seller.repositories.UserRepository;

import java.lang.reflect.Array;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportFragment extends Fragment {

    TextView TotalValue;
    TextView TotalQuantity;

    Button ByMonth;
    Button ByProduct;
    Button BySeller;

    ArrayList<Report> Reports = new ArrayList<Report>();
    ListView ReportView;
    ArrayAdapter<Report> Adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_report, container, false);


        TotalValue = root.findViewById(R.id.totalValue);
        TotalQuantity = root.findViewById(R.id.totalQuantity);
        ByMonth = root.findViewById(R.id.byMonth);
        ByProduct = root.findViewById(R.id.byProduct);
        BySeller = root.findViewById(R.id.bySeller);
        ReportView = root.findViewById(R.id.reportList);

        BuildReportByDate();
        BuildReportByProduct();
        BuildReportBySeller();

        return root;
    }

    public void BuildReportByProduct(){
        final SellRepository SellRepository = new SellRepository();

        ByProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SellRepository.Read(new IOnGetDataListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Reports = new ArrayList<Report>();

                        Map<String, List<Sell>> groups =  SellRepository.getEntites()
                                .stream()
                                .collect(Collectors.groupingBy(Sell::getProductID));

                        double totalQuantity = 0;
                        double totalValue = 0;

                        for(Map.Entry<String, List<Sell>> group: groups.entrySet()){
                            double quantity = 0;
                            double value = 0;

                            for (Sell sell: group.getValue()){
                                quantity += sell.getQuantity();
                                value += sell.getPrice();
                            }

                            totalQuantity += quantity;
                            totalValue += value;

                            Reports.add(new Report(group.getKey(),quantity,value));
                        }

                        TotalValue.setText(""+totalValue);
                        TotalQuantity.setText(""+totalQuantity);

                        Adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, Reports);
                        ReportView.setAdapter(Adapter);

                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    };
                });
            }
        });
    }

    public void BuildReportByDate(){
        final SellRepository SellRepository = new SellRepository();

        ByMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SellRepository.Read(new IOnGetDataListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Reports = new ArrayList<Report>();

                        Map<Integer, List<Sell>> groups =  SellRepository.getEntites()
                                .stream()
                                .collect(Collectors.groupingBy(Sell::getMonthDate));

                        double totalQuantity = 0;
                        double totalValue = 0;

                        for(Map.Entry<Integer, List<Sell>> group: groups.entrySet()){
                            double quantity = 0;
                            double value = 0;

                            for (Sell sell: group.getValue()){
                                quantity += sell.getQuantity();
                                value += sell.getPrice();
                            }

                            totalQuantity += quantity;
                            totalValue += value;

                            Reports.add(new Report(group.getKey().toString(),quantity,value));
                        }

                        TotalValue.setText(""+totalValue);
                        TotalQuantity.setText(""+totalQuantity);

                        Adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, Reports);
                        ReportView.setAdapter(Adapter);

                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    };
                });
            }
        });
    }

    public void BuildReportBySeller(){
        final SellRepository SellRepository = new SellRepository();

        BySeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SellRepository.Read(new IOnGetDataListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Reports = new ArrayList<Report>();

                        Map<String, List<Sell>> groups =  SellRepository.getEntites()
                                .stream()
                                .collect(Collectors.groupingBy(Sell::getSellerID));

                        double totalQuantity = 0;
                        double totalValue = 0;

                        for(Map.Entry<String, List<Sell>> group: groups.entrySet()){
                            double quantity = 0;
                            double value = 0;

                            for (Sell sell: group.getValue()){
                                quantity += sell.getQuantity();
                                value += sell.getPrice();
                            }

                            totalQuantity += quantity;
                            totalValue += value;

                            Reports.add(new Report(group.getKey(),quantity,value));
                        }

                        TotalValue.setText(""+totalValue);
                        TotalQuantity.setText(""+totalQuantity);

                        Adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, Reports);
                        ReportView.setAdapter(Adapter);

                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    };
                });
            }
        });
    }
}