package com.example.babybuy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link HomeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class HomeFragment extends Fragment {
    private FloatingActionButton mainFabIcon, assignedIcon, newListIcon;
    private Animation fabOpenAnim, fabCloseAnim, rotateOpen, rotateClose;
    View mainFragmentHolder;
    private boolean isOpen;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public HomeFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment HomeFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static HomeFragment newInstance(String param1, String param2) {
//        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainFragmentHolder =  inflater.inflate(R.layout.fragment_home, container, false);

        mainFabIcon = mainFragmentHolder.findViewById(R.id.fabAddIconID);
        assignedIcon = mainFragmentHolder.findViewById(R.id.fabAssignedIconID);
        newListIcon = mainFragmentHolder.findViewById(R.id.fabCreateNewListIconID);

        fabOpenAnim = AnimationUtils.loadAnimation(container.getContext(), R.anim.fab_open);
        fabCloseAnim = AnimationUtils.loadAnimation(container.getContext(), R.anim.fab_close);
        rotateOpen = AnimationUtils.loadAnimation(container.getContext(), R.anim.fab_rotate_open);
        rotateClose = AnimationUtils.loadAnimation(container.getContext(), R.anim.fab_rotate_close);


        isOpen = false;
        mainFabIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen){
                    assignedIcon.setAnimation(fabCloseAnim);
                    newListIcon.setAnimation(fabCloseAnim);
                    mainFabIcon.setAnimation(rotateClose);

                    assignedIcon.setVisibility(View.INVISIBLE);
                    newListIcon.setVisibility(View.INVISIBLE);

                    isOpen = false;
                }else {
                    assignedIcon.setAnimation(fabOpenAnim);
                    newListIcon.setAnimation(fabOpenAnim);
                    mainFabIcon.setAnimation(rotateOpen);

                    assignedIcon.setVisibility(View.VISIBLE);
                    newListIcon.setVisibility(View.VISIBLE);

                    isOpen = true;
                }
            }
        });


        return mainFragmentHolder;
    }
}