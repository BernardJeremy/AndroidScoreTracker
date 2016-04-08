package edu.csulb.android.androidscoretracker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ScoreboardCommentDialog extends DialogFragment {

    Dialog dialogFragment;
    TextView comment;

    public static ScoreboardCommentDialog newInstance() {
        return new ScoreboardCommentDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scoreboard_comment_dialog, container, false);

        dialogFragment = this.getDialog();
        dialogFragment.setTitle(R.string.title_comment_dialog);

        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(cancelButtonListener);

        Button add = (Button) view.findViewById(R.id.add);
        add.setOnClickListener(addButtonListener);

        comment = (TextView) view.findViewById(R.id.comment);

        return view;
    }

    private void sendResult(int REQUEST_CODE) {
        Intent intent = new Intent();
        intent.putExtra("Comment", comment.getText());
        getTargetFragment().onActivityResult(getTargetRequestCode(), REQUEST_CODE, intent);
    }

    // Listener click for cancel button
    private View.OnClickListener cancelButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogFragment.dismiss();
        }
    };

    // Listener click for add button
    private View.OnClickListener addButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            sendResult(Scoreboard.COMMENT);
            dialogFragment.dismiss();
        }
    };
}
