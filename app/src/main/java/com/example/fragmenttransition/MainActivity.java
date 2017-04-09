package com.example.fragmenttransition;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  // poor mans global state
  private static int elementCount;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null) {
      elementCount = 3;
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.fragment_container, new SourceFragment())
          .commit();
    }
  }

  public static void createElement(ViewGroup parent) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    TextView textView = (TextView) inflater.inflate(R.layout.text_view, parent, false);
    parent.addView(textView);
    String title = String.valueOf(parent.getChildCount());
    textView.setText(title);
    textView.setTransitionName(title);
    elementCount = parent.getChildCount();
  }

  public static void createElements(ViewGroup parent) {
    int elementCount = MainActivity.elementCount;
    for (int i = 0; i < elementCount; i++) {
      MainActivity.createElement(parent);
    }
  }

  @Override
  public void onBackPressed() {

    ViewGroup container = (ViewGroup) findViewById(R.id.element_container);
    int childCount = container.getChildCount();
    SourceFragment fragment = new SourceFragment();
    fragment.setSharedElementEnterTransition(new ChangeBounds());
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    for (int i = 0; i < childCount; i++) {
      View element = container.getChildAt(i);
      transaction.addSharedElement(element, element.getTransitionName());
    }
    transaction.replace(R.id.fragment_container, fragment)
        .setAllowOptimization(true)
        .commit();
  }
}
