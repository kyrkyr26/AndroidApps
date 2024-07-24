package com.example.myteamtracker.ui.nfl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myteamtracker.R;
import com.example.myteamtracker.ui.ListViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NflFragment extends Fragment {
    private ListViewModel viewModel;
    NFLViewModel nfl_view_model;
    List<String> all_teams = new ArrayList<String>();
    LinearLayout lin;
    List<String> previouslyPickedTeams;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        nfl_view_model =
                new ViewModelProvider(this).get(NFLViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nfl, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        List<String> exitingTeams = viewModel.getList();
        previouslyPickedTeams = new ArrayList<String>();
        lin = (LinearLayout) root.findViewById(R.id.text_nfl);
        int len = 0;
        len = viewModel.getTeamLen();
        String currentTeam = "";
        // if there is a previously saved instance and the length of the list of picked teams is not 0
        if (savedInstanceState != null && len != 0)
        {
            //populate the previously picked teams list
            for (int i = 0; i < len; i++)
            {
                currentTeam = exitingTeams.get(i);
            }
            previouslyPickedTeams.add(savedInstanceState.getString(currentTeam));
        }
        //if three is no saved instance state but the view model's list of picked teams is not empty
        //populate previously picked teams list
        else if (viewModel.getList() != null)
        {
            previouslyPickedTeams = viewModel.getList();
        }
        TextView text = new TextView(getActivity());
        //show Connecting status while the async task gets the list of teams from the internet
        text.setText("Connecting...");
        lin.addView(text);

        Teams c = new Teams();
        //ASync task is deprecated but works for now to populate the list of teams from the internet
        //in the background
        c.execute();

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        String name = "";
        for (int i = 0; i < viewModel.getTeamLen(); i++)
        {
            name = viewModel.getNameAt(i);
            outState.putString(name, name);
        }
        super.onSaveInstanceState(outState);
    }

    private class Teams extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //Connect to the website
                Document document = Jsoup.connect("https://www.profootballnetwork.com/nfl-teams-in-alphabetical-order/").get();
                Elements teams = document.select(".tdb-block-inner p + h3 + ul li strong");
                int count = 0;
                for (Element headline : teams) {
                    String tag = "team" + String.valueOf(count);
                    all_teams.add(headline.text());
                    count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            TextView t = new TextView(getActivity());
            lin.removeAllViews();
            for (int i = 0; i < all_teams.size(); i++)
            {
                CheckBox check = new CheckBox(getActivity());
                check.setText(all_teams.get(i));
                if (previouslyPickedTeams.contains(all_teams.get(i)))
                {
                    check.setChecked(true);
                }
                check.setPadding(10, 10, 10, 10);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 5, 5, 5);
                params.gravity = Gravity.NO_GRAVITY;
                check.setLayoutParams(params);
                check.setGravity(Gravity.CENTER);
                check.setId(i);
                check.setTextSize(25);
                check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            Log.d("true",buttonView.getText().toString());
                            viewModel.addTeam(buttonView.getText().toString());
                        } else {
                            Log.d("false","false\n");
                            viewModel.removeName(buttonView.getText().toString());
                        }
                    }
                });

                lin.addView(check);
            }
            Log.d("end", "IM OUT");
            super.onPostExecute(aVoid);
        }
    }
}