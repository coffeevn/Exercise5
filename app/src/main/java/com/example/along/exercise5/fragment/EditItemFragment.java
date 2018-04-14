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

import com.example.along.exercise5.EditItem;
import com.example.along.exercise5.R;

public class EditItemFragment extends DialogFragment {
    private EditItem editItem;
    private int curId;
    private String curName;
    private String curDueTo;
    private int curPriority;
    public EditItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this
        editItem = (EditItem) getActivity();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_edit_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        TextView tvSave = view.findViewById(R.id.edit_tv_Save);
        TextView tvCancel = view.findViewById(R.id.edit_tv_Cancel);
        final EditText etName = view.findViewById(R.id.edit_et_Name);
        final DatePicker datePicker = view.findViewById(R.id.edit_dialog_datePicker);
        final RadioGroup radioGroup = view.findViewById(R.id.edit_rg);

        if(!bundle.isEmpty()){
            curId = bundle.getInt("id");
            curName = bundle.getString("name");
            curDueTo = bundle.getString("date");
            curPriority = bundle.getInt("priority");
        }
        etName.setText(curName);

        String[] values = curDueTo.split("/");
        int day = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);
        datePicker.updateDate(year,month-1,day);
        switch (curPriority) {
            case 1:
                radioGroup.check(R.id.edit_rb_high);
                break;
            case 3:
                radioGroup.check(R.id.edit_rb_low);
                break;
            default:
                radioGroup.check(R.id.edit_rb_normal);
                break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.edit_rb_high:
                        curPriority = 1;
                        break;
                    case R.id.edit_rb_low:
                        curPriority = 3;
                        break;
                    default:
                        curPriority = 2;
                        break;
                }
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem.EditToDo(curId,etName.getText().toString(),getDueTo(datePicker),curPriority);
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
