package com.stecon.patipan_on.diarycar.custom_fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stecon.patipan_on.diarycar.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {

    public static final String KEY_STRING = "key_string";
    private String str;
    TextView textView1;

    public static OneFragment newInstance(String str) {
        //this.str = str;
        OneFragment oneFragment = new OneFragment();
        return oneFragment;
    }

    public static OneFragment newInstance() {
        //this.str = str;
        OneFragment oneFragment = new OneFragment();
        return oneFragment;
    }

    public OneFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        textView1 = (TextView) rootView.findViewById(R.id.textView1);
        Button btn_close = (Button) rootView.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        return rootView;
    }

    public String getMyText() {
        return textView1.getText().toString();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Check", "onActivityCreated");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("Check", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Check", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Check", "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Check", "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Check", "onDetach");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Check", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Check", "onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Check", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Check", "onStop");
    }


}
