package com.openclassrooms.entrevoisins.view_neighbour;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ViewNeighbourActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ViewNeighbourTest {


    @Test
    public void changeImageResolution_Test()
    {
        String originalImg = "https://i.pravatar.cc/150?u=a042581f4e29026706d";
        int resolution = 300;
        String expectedUrl = "https://i.pravatar.cc/300?u=a042581f4e29026706d";
        String actualUrl = ViewNeighbourActivity.changeImageResolution(originalImg,300);
        assertEquals(actualUrl,expectedUrl);
    }
}
