package com.example.along.exercise5.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.along.exercise5.DoneItem;

public class EditOrDoneFragment extends DialogFragment {
    DoneItem doneItem;
    private int curId;
    private String curName;
    private String curDueTo;
    private int curPriority;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        doneItem = (DoneItem) getActivity();
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Edit or Done");
        dialog.setMessage("Bạn muốn chỉnh sửa hay xác nhận hoàn thành To Do này?");

        Bundle bundle = getArguments();
        if(!bundle.isEmpty()) {
            curId = bundle.getInt("id");
            curName = bundle.getString("name");
            curDueTo = bundle.getString("date");
            curPriority = bundle.getInt("priority");
        } else {
            Toast.makeText(getActivity(),"Nothing",Toast.LENGTH_SHORT).show();
            return dialog.create();
        }

            dialog.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditItemFragment editItemFragment = new EditItemFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",curId);
                    bundle.putString("name",curName);
                    bundle.putString("date",curDueTo);
                    bundle.putInt("priority",curPriority);
                    editItemFragment.setArguments(bundle);
                    editItemFragment.show(getActivity().getFragmentManager(),"EditOrDoneDialog");
                }
            });
            dialog.setNegativeButton("Hoàn thành", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doneItem.DoneToDo(curId);
                }
            });

        return dialog.create();
    }
}
