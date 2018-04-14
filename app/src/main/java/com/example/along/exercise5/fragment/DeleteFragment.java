package com.example.along.exercise5.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.along.exercise5.DeleteItem;

public class DeleteFragment extends DialogFragment {
    private DeleteItem deleteItem;
    private int id;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        deleteItem = (DeleteItem) getActivity();

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Delete");
        dialog.setMessage("Bạn có muốn xóa To Do này không?");

        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = getArguments();
                if(!bundle.isEmpty()) {
                    id = bundle.getInt("id");
                    deleteItem.DeleteToDo(id);
                } else {
                    Toast.makeText(getActivity(),"Không có gì để xóa",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return dialog.create();
    }
}
