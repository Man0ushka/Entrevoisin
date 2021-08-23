package com.openclassrooms.entrevoisins.utils;

import android.view.View;
import android.widget.Toolbar;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.openclassrooms.entrevoisins.R;

import org.hamcrest.Matcher;

public class AddFavoriteViewAction implements ViewAction {
    View button;
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void perform(UiController uiController, View view) {
        button = view.findViewById(R.id.add_favorite);
        // Maybe check for null
        button.performClick();

    }
}
