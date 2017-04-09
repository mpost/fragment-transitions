package com.example.fragmenttransition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class DestinationFragment extends Fragment {

  private LinearLayout elementContainer;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.destination_fragment, null);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    elementContainer = (LinearLayout) view.findViewById(R.id.element_container);
    MainActivity.createElements(elementContainer);
    view.findViewById(R.id.add_element).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View button) {
        MainActivity.createElement(elementContainer);
      }
    });
  }

}
