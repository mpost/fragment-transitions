package com.example.fragmenttransition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Map;

public class DestinationFragment extends Fragment {

  private LinearLayout elementContainer;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setEnterSharedElementCallback(new SharedElementCallback() {
      @Override
      public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        super.onMapSharedElements(names, sharedElements);
        int childCount = elementContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
          View element = elementContainer.getChildAt(i);
          String transitionName = element.getTransitionName();
          if (!names.contains(transitionName)) {
            names.add(transitionName);
          }
          if (!sharedElements.containsKey(transitionName)) {
            sharedElements.put(transitionName, element);
          }
        }
      }
    });
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.destination_fragment, null);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    elementContainer = (LinearLayout) view.findViewById(R.id.element_container);
    MainActivity.createElements(elementContainer);
    view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View button) {
        MainActivity.createElement(elementContainer);
      }
    });
  }

}
