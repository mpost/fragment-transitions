package com.example.fragmenttransition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SharedElementCallback;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.List;
import java.util.Map;

public class SourceFragment extends Fragment {

  private ViewGroup elementContainer;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    postponeEnterTransition();
    setExitSharedElementCallback(new SharedElementCallback() {
      @Override
      public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        super.onMapSharedElements(names, sharedElements);
        int childCount = elementContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
          View view = elementContainer.getChildAt(i);
          String transitionName = view.getTransitionName();
          if (!names.contains(transitionName)) {
            names.add(transitionName);
          }
          if (!sharedElements.containsKey(transitionName)) {
            sharedElements.put(transitionName, view);
          }
        }
      }
    });
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.source_fragment, null);
    view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      @Override
      public boolean onPreDraw() {
        view.getViewTreeObserver().removeOnPreDrawListener(this);
        startPostponedEnterTransition();
        return true;
      }
    });
    return view;
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
      String transitionName = element.getTransitionName();
      transaction.addSharedElement(element, transitionName);
    }
    transaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
  }
}
