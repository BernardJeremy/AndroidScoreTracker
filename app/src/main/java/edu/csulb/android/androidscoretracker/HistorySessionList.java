package edu.csulb.android.androidscoretracker;

import android.app.Fragment;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HistorySessionList extends Fragment {

    private SessionHistoryDatabaseManager sessionHistoryDatabaseManager = new SessionHistoryDatabaseManager();
    private ListView listView;

    public static HistorySessionList newInstance(int idSession) {
        HistorySessionList historySessionList = new HistorySessionList();
        Bundle bundle = new Bundle();
        bundle.putInt("idSession", idSession);
        historySessionList.setArguments(bundle);
        return historySessionList;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.history_session_fragment, container, false);

        listView = (ListView) layout.findViewById(R.id.history_list);
        TextView historyTitle = (TextView) layout.findViewById(R.id.history_title);
        SpannableString gameNameSpan = new SpannableString("History");
        gameNameSpan.setSpan(new UnderlineSpan(), 0, gameNameSpan.length(), 0);
        historyTitle.setText(gameNameSpan);

        updateHistory();

        return layout;
    }

    public void updateHistory() {
        Integer sessionId = getArguments().getInt("idSession");

        ArrayList<SessionHistory> arrayListSessionHistory = sessionHistoryDatabaseManager.getAllHistory(sessionId);

        HistorySessionAdapter adapter = new HistorySessionAdapter(getActivity(),
                arrayListSessionHistory.toArray(new SessionHistory[arrayListSessionHistory.size()]));

        listView.setAdapter(adapter);
    }
}
