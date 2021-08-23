package com.openclassrooms.entrevoisins.utils;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.openclassrooms.entrevoisins.R;

import org.hamcrest.Matcher;

public class ShowDetailAction implements ViewAction {
    String name;
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    public String getName(){

        return name;
    }

    @Override
    public void perform(UiController uiController, View view) {
        TextView nameHolder = view.findViewById(R.id.item_list_name);
        name = (String) nameHolder.getText();

        // Maybe check for null
        nameHolder.performClick();

    }
}
