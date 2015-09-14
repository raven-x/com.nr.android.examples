package com.nimura.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limi on 04.08.2015.
 */
public class PersistentListDialogFragment extends DialogFragment {

    private final List<Integer> selectedItems = new ArrayList<>(3);

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Persistent list")
            .setMultiChoiceItems(R.array.strArr, null,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if(isChecked){
                                selectedItems.add(which);
                            }else{
                                selectedItems.remove(Integer.valueOf(which));
                            }
                        }
                    })
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedItems.clear();
                }
            });
        return builder.create();
    }

    public List<Integer> getResult(){
        return selectedItems;
    }
}
