package com.example.ayana.chekikkov1;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ayana.chekikkov1.Adapter.DoodleAdapter;
import com.example.ayana.chekikkov1.Paint.PaintView;
import com.example.ayana.chekikkov1.Utils.SpacesItemDecoration;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoodleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoodleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoodleFragment extends Fragment implements RecyclerPaletteClick{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    DoodleAdapter mDoodleAdapter;

    Bitmap bmp;
    Bitmap frameBmp;
    Bitmap paintBitmap;

//    ImageView mPhotoView;
//    ImageView mFrameView;
//    PaintView mPaintView;

    int[] paletteList = {R.color.black, R.color.deep_koamaru, R.color.pastel_blue, R.color.lavender_gray,
            R.color.queen_pink, R.color.orange_yellow, R.color.white};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DoodleFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoodleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoodleFragment newInstance(String param1, String param2) {
        DoodleFragment fragment = new DoodleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doodle, container, false);

        recyclerView = view.findViewById(R.id.doodle_recycler_view);

//        mPhotoView = view.findViewById(R.id.doodleImageView);
//        mFrameView = view.findViewById(R.id.doodleFrameView);
//        mPaintView = view.findViewById(R.id.paintView);

        mDoodleAdapter = new DoodleAdapter(getActivity(), paletteList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new SpacesItemDecoration(space));
        recyclerView.setAdapter(mDoodleAdapter);

//        paintBitmap = Bitmap.createBitmap(frameBmp.getWidth(), frameBmp.getHeight(), Bitmap.Config.ARGB_8888);
//        mPaintView.init(paintBitmap, 0x00, 0x00, 0x00);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int pos) {
        if (mListener != null) {
            mListener.onDoodleFragmentInteraction(pos);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPaletteColorChange(int pos) {
        mListener.onDoodleFragmentInteraction(pos);
//        switch (pos) {
//            case 0:
//                // black
//                mPaintView.init(paintBitmap, 0x16, 0x16, 0x16);
//                break;
//            case 1:
//                // deep_koamaru
//                mPaintView.init(paintBitmap, 35, 54, 104);
//                break;
//            case 2:
//                // pastel_blue
//                mPaintView.init(paintBitmap, 160, 195, 210);
//                break;
//            case 3:
//                // lavender_gray
//                mPaintView.init(paintBitmap, 190, 190, 209);
//                break;
//            case 4:
//                // queen_pink
//                mPaintView.init(paintBitmap, 248, 205, 210);
//                break;
//            case 5:
//                // orange_yellow
//                mPaintView.init(paintBitmap, 249, 200, 99);
//                break;
//            case 6:
//                // white
//                mPaintView.init(paintBitmap, 255, 255, 255);
//                break;
//            default:
//                return;
//        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDoodleFragmentInteraction(int pos);
    }
}
