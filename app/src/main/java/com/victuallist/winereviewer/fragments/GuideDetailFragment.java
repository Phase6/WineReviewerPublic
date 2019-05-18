package com.victuallist.winereviewer.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.victuallist.winereviewer.R;
import com.victuallist.winereviewer.data.objects.GuideObject;

public class GuideDetailFragment extends Fragment {

    String LOG_TAG = "Guide Detail Fragment";

    Context parentContext;

    GuideObject guideObject;

    TextView selectedTypeLabelOrigin, selectedTypeLabelGrape, selectedTypeLabelFlavor, selectedTypeLabelPairing;
    TextView selectedTypeName, selectedTypeOrigin, selectedTypeGrape, selectedTypeFlavor, selectedTypePairing;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        parentContext = context;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle != null){
            try{
                guideObject = (GuideObject) bundle.getSerializable("SELECTEDGUIDEDETAILOBJECT");
            }catch (ClassCastException e){
                e.printStackTrace();
            }

        }else{

        }

        View rootView = inflater.inflate(R.layout.fragment_guide_detail, container, false);

        selectedTypeName = (TextView) rootView.findViewById(R.id.guideDetailTextViewName);
        selectedTypeOrigin = (TextView) rootView.findViewById(R.id.guideDetailTextViewOrigin);
        selectedTypeGrape = (TextView) rootView.findViewById(R.id.guideDetailTextViewGrapes);
        selectedTypeFlavor = (TextView) rootView.findViewById(R.id.guideDetailTextViewFlavor);
        selectedTypePairing = (TextView) rootView.findViewById(R.id.guideDetailTextViewPairing);

        selectedTypeLabelOrigin = (TextView) rootView.findViewById(R.id.guideDetailTextViewOriginLabel);
        selectedTypeLabelGrape = (TextView) rootView.findViewById(R.id.guideDetailTextViewGrapesLabel);
        selectedTypeLabelFlavor = (TextView) rootView.findViewById(R.id.guideDetailTextViewFlavorLabel);
        selectedTypeLabelPairing = (TextView) rootView.findViewById(R.id.guideDetailTextViewPairingLabel);


        if(guideObject != null){
            populateGuideDetailCard();
        }

        return rootView;

    }


    public void populateGuideDetailCard(){

        selectedTypeName.setText(guideObject.getVariety());

        if(guideObject.getOrigin().length() > 0){
            selectedTypeOrigin.setText(guideObject.getOrigin());
        }else{
            selectedTypeOrigin.setVisibility(View.GONE);
            selectedTypeLabelOrigin.setVisibility(View.GONE);
        }

        if(guideObject.getGrapes().length() > 0){
            selectedTypeGrape.setText(guideObject.getGrapes());
        }else{
            selectedTypeGrape.setVisibility(View.GONE);
            selectedTypeLabelGrape.setVisibility(View.GONE);
        }

        selectedTypeFlavor.setText(guideObject.getFlavor());
        selectedTypePairing.setText(guideObject.getPairing());


    }


}
