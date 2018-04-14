package com.example.along.exercise5.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.along.exercise5.AddItem;
import com.example.along.exercise5.R;
import com.example.along.exercise5.model.Item;

import io.realm.Realm;

public class AddItemFragment extends DialogFragment {
    Realm realm;
    AddItem addItem;
    private int Priority;
    public AddItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this
        addItem = (AddItem) getActivity();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView tvSave = view.findViewById(R.id.add_tv_Save);
        TextView tvCancel = view.findViewById(R.id.add_tv_Cancel);
        final EditText etName = view.findViewById(R.id.add_et_Name);
        final DatePicker datePicker = view.findViewById(R.id.add_dialog_datePicker);
        final RadioGroup radioGroup = view.findViewById(R.id.add_rg);
        radioGroup.check(R.id.add_rb_normal);
        Priority = 2;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.add_rb_high:
                        Priority = 1;
                        break;
                    case R.id.add_rb_low:
                        Priority = 3;
                        break;
                    default:
                        Priority = 2;
                        break;
                }
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm = Realm.getDefaultInstance();
                int nextId = 1;
                if(!realm.isEmpty()) {
                    nextId = realm.where(Item.class).max("id").intValue() + 1;
                }
                addItem.AddToDo(nextId,etName.getText().toString(),getDueTo(datePicker),Priority);
                dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }


    public String getDueTo(DatePicker datePicker){
        int ngay = datePicker.getDayOfMonth();
        int thang = datePicker.getMonth()+1;
        int nam = datePicker.getYear();
        return (String.valueOf(ngay)+"/"+String.valueOf(thang)+"/"+String.valueOf(nam));
    }
}
