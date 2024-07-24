package com.example.myteamtracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myteamtracker.R;
import com.example.myteamtracker.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListViewModel extends ViewModel {

    private HomeViewModel homeViewModel;

    private final MutableLiveData<Set<String>> filters = new MutableLiveData<>();
    private MutableLiveData<List<String>> pickedTeams = new MutableLiveData<List<String>>();
    private List<String> temp = new ArrayList<String>();
    private List<String> nfl = new ArrayList<String>();

    public MutableLiveData<List<String>> getTeam() {
        if (pickedTeams == null) {
            pickedTeams = new MutableLiveData<List<String>>();
        }
        return pickedTeams;
    }


    public List<String> getList() {
        return pickedTeams.getValue();
    }

    public void setNflTeams(List<String> teams) {
        nfl = teams;
    }

    public List<String> pickedNflTeams() {
        return nfl;
    }

    public int getTeamLen()
    {
        if (pickedTeams.getValue() != null) {
            return pickedTeams.getValue().size();
        }
        else
        {
            return 0;
        }
    }

    public List<String> getNames()
    {
        List<String> team_str = pickedTeams.getValue();
        return team_str;
    }
    public void setPickedTeams(List<String> filter)
    {
        temp.addAll(filter);
        pickedTeams.setValue(temp);
    }

    public String getNameAt(int ind)
    {
        return pickedTeams.getValue().get(ind);
    }

    public void addTeam(String teamName)
    {
        temp.add(teamName);
        pickedTeams.setValue(temp);
    }

    public void removeName(String teamName) {
        temp.remove(teamName);
        pickedTeams.setValue(temp);
    }
}