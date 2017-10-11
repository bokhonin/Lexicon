package ru.bokhonin.lexicon.presenter;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import ru.bokhonin.lexicon.R;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import android.app.Activity;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import java.util.UUID;

public class DialogVocabList extends DialogFragment{

    public static final String ARG_ID = "ru.bokhonin.lexicon.id";
    public static UUID mId;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.action_del_list)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }


    public static DialogVocabList newInstance(UUID id) {

        mId = id;

        Bundle args = new Bundle();
        args.putSerializable(ARG_ID, id);

        DialogVocabList dialogFragment = new DialogVocabList();
        dialogFragment.setArguments(args);

        return dialogFragment;
    }


    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) return;

        Intent intent = new Intent();
        intent.putExtra(ARG_ID, mId);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
