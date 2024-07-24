package com.example.myteamtracker.ui.home;

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myteamtracker.R;
import com.example.myteamtracker.ui.ListViewModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ListViewModel viewModel;
    private String chosenteam = "";
    boolean live = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        final Observer<List<String>> nameObserver = new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> newName) {
                // Update the UI, in this case, a TextView.
                for (int i = 0; i < newName.size(); i++)
                {
                    CheckBox check = new CheckBox(getActivity());
                    check.setText(newName.get(i).toString());
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
                            if (isChecked)
                            {
                                chosenteam = check.getText().toString();
                                Teams c = new Teams();
                                c.execute();
                            }
                            else
                            {

                            }
                        }
                    });

                    LinearLayout lin = (LinearLayout) root.findViewById(R.id.text_home);
                    lin.addView(check);
                }
            }
        };
        viewModel.getTeam().observe(getViewLifecycleOwner(), nameObserver);
        //String team = viewModel.getName();

       // textView.setText(team);
        return root;
    }

    private class Teams extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String printout = "";
            String teamname = "https://www.google.com/search?q=" + chosenteam + "&num=20";
            Elements teams;
            try {
                //Connect to the website
                Document document = Jsoup.connect(teamname).get();
                try
                {
                        teams = document.select(".liveresults-sports-immersive__team-name-width");
                        if (teams.size() == 0)
                        {
                            live = false;
                            Log.d("nolivegame", "No live game");
                            teams = document.select(".imspo_mt__ms-w .imspo_mt__ns-pm-s");
                            for (Element next_game : teams)
                            {
                                printout = next_game.text() + "\n";
                                Log.d("nextgame",printout);
                            }
                            return null;
                        }
                        else
                        {
                            live = true;
                        }
                }
                catch (Selector.SelectorParseException e)
                {
                    Log.d("error", "No live game");
                    return null;
                }
                int count = 0;
                String tag;
                for (Element team : teams) {
                    if (count == 0)
                    {
                        Elements home_score = document.select(".imso_mh__l-tm-sc");
                        for (Element score : home_score) {
                            printout = team.text() + " score: " + score.text() + "\n";
                            Log.d("homescore",printout);
                        }
                    }
                    else
                    {
                        tag = "awayteam";
                        Elements away_score = document.select(".imso_mh__r-tm-sc");
                        for (Element score : away_score) {
                            printout = team.text() + " score: " + score.text() + "\n";
                            Log.d("awayscore",printout);
                        }
                    }
                    count++;
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}