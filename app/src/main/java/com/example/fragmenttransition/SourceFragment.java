package com.example.fragmenttransition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SourceFragment extends Fragment {

  private ViewGroup elementContainer;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.source_fragment, null);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    elementContainer = (ViewGroup) getView().findViewById(R.id.element_container);
    MainActivity.createElements(elementContainer);
    view.findViewById(R.id.open_destination).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View button) {
        showDestinationFragment();
      }
    });
  }

  private void showDestinationFragment() {
    int childCount = elementContainer.getChildCount();
    DestinationFragment fragment = new DestinationFragment();
    fragment.setSharedElementEnterTransition(new ChangeBounds());
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    for (int i = 0; i < childCount; i++) {
      View element = elementContainer.getChildAt(i);
      transaction.addSharedElement(element, element.getTransitionName());
    }
    transaction.replace(R.id.fragment_container, fragment)
        .addToBackStack(null)
        .setAllowOptimization(true)
        .commit();
  }
}
