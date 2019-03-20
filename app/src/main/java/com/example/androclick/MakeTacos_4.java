package com.example.androclick;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}

public class MakeTacos_4 extends Fragment {

    private Bundle bundle;
    private Recette recette;

    ArrayList<Supplement> listeSupplements;

    private RecyclerView rvSupplements;
    private RecyclerView.Adapter supplementsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public MakeTacos_4() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        recette = (Recette) bundle.getSerializable("recette");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_tacos_fragment_4, container, false);

        rvSupplements = (RecyclerView) view.findViewById(R.id.list_supplements);
        displayListeSupplements();

        rvSupplements.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Supplement s = listeSupplements.get(position);
                        s.setSelected(!s.isSelected());
                        if (s.isSelected()) {
                            recette.addSupplement(s);
                        } else {
                            recette.removeSupplement(s);
                        }
                        bundle.putSerializable("recette", recette);
                        setArguments(bundle);
                    }
                })
        );

        bundle.putSerializable("recette", recette);
        setArguments(bundle);

        return view;
    }

    private void displayListeSupplements() {
        listeSupplements = ((MyApplication) this.getActivity().getApplicationContext()).getListeSupplements();

        rvSupplements.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvSupplements.setLayoutManager(layoutManager);

        supplementsAdapter = new SupplementsAdapter(listeSupplements);
        rvSupplements.setAdapter(supplementsAdapter);
    }
}
