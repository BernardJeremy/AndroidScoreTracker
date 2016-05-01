package edu.csulb.android.androidscoretracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class HistorySessionAdapter extends ArrayAdapter<SessionHistory>{

    private Context context;
    private SessionHistory[] sessionHistory;

    public HistorySessionAdapter(Context context, SessionHistory[] values) {
        super(context, -1, values);
        this.context = context;
        sessionHistory = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View historyItemView = inflater.inflate(R.layout.history_list_item, parent, false);

        TextView type = (TextView) historyItemView.findViewById(R.id.type);
        TextView date = (TextView) historyItemView.findViewById(R.id.date);
        TextView comment = (TextView) historyItemView.findViewById(R.id.comment);

        String strType;
        switch (sessionHistory[position].getType()) {
            case SessionHistory.TYPE_WIN:
                strType = "WIN";
                break;
            case SessionHistory.TYPE_DRAW:
                strType = "DRAW";
                break;
            case SessionHistory.TYPE_LOOSE:
                strType = "LOOSE";
                break;
            default:
                strType = "";
                break;
        }
        type.setText(strType);

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        date.setText(sdf.format(sessionHistory[position].getDate()));
        comment.setText(sessionHistory[position].getComment());

        return historyItemView;
    }
}