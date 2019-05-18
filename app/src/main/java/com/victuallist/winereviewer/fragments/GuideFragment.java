package com.victuallist.winereviewer.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.victuallist.winereviewer.MainActivity;
import com.victuallist.winereviewer.R;
import com.victuallist.winereviewer.data.objects.GuideObject;
import com.victuallist.winereviewer.data.xmlparsing.XMLGuideParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;


public class GuideFragment extends Fragment implements View.OnClickListener {

    String LOG_TAG = "Guide Fragment";

    Context parentContext;

    ArrayList<GuideObject> guideObjects, redVarietalObjects, redAppellationObjects, whiteVarietalObjects,
                            whiteAppellationObjects, sparklingWineObjects, fortifiedWineObjects;

    LinearLayout redVarietalsLinearLayout, redAppellationLinearLayout, whiteVarietalsLinearLayout,
            whiteAppellationLinearLayout, sparklingWineLinearLayout, fortifiedWineLinearLayout;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        parentContext = context;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        XMLGuideParser xmlGuideParser = new XMLGuideParser(parentContext);
        guideObjects = new ArrayList<>();

        try{
            guideObjects = xmlGuideParser.parse();
            Log.i(LOG_TAG, "Guide Objects: " + String.valueOf(guideObjects.size()));
        }catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        redVarietalObjects = new ArrayList<>();
        redAppellationObjects = new ArrayList<>();
        whiteVarietalObjects = new ArrayList<>();
        whiteAppellationObjects = new ArrayList<>();
        sparklingWineObjects = new ArrayList<>();
        fortifiedWineObjects = new ArrayList<>();

        for(int i = 0; i < guideObjects.size(); i++){
            if(guideObjects.get(i).getType().equals("Red Varietal")){
                redVarietalObjects.add(guideObjects.get(i));
            }else if(guideObjects.get(i).getType().equals("Red Appellation")){
                redAppellationObjects.add(guideObjects.get(i));
            }else if(guideObjects.get(i).getType().equals("White Varietal")){
                whiteVarietalObjects.add(guideObjects.get(i));
            }else if(guideObjects.get(i).getType().equals("White Appellation")){
                whiteAppellationObjects.add(guideObjects.get(i));
            }else if(guideObjects.get(i).getType().equals("Sparkling Wine")){
                sparklingWineObjects.add(guideObjects.get(i));
            }else if(guideObjects.get(i).getType().equals("Fortified Wine")){
                fortifiedWineObjects.add(guideObjects.get(i));
            }
        }

        View rootView = inflater.inflate(R.layout.fragment_guide, container, false);

        redVarietalsLinearLayout = (LinearLayout) rootView.findViewById(R.id.redVarietalsLinearLayout);
        addButtonsToCards(redVarietalsLinearLayout, redVarietalObjects);

        redAppellationLinearLayout = (LinearLayout) rootView.findViewById(R.id.redAppellationsLinearLayout);
        addButtonsToCards(redAppellationLinearLayout, redAppellationObjects);

        whiteVarietalsLinearLayout = (LinearLayout) rootView.findViewById(R.id.whiteVarietalsLinearLayout);
        addButtonsToCards(whiteVarietalsLinearLayout, whiteVarietalObjects);

        whiteAppellationLinearLayout = (LinearLayout) rootView.findViewById(R.id.whiteAppellationsLinearLayout);
        addButtonsToCards(whiteAppellationLinearLayout, whiteAppellationObjects);

        sparklingWineLinearLayout = (LinearLayout) rootView.findViewById(R.id.bubblesLinearLayout);
        addButtonsToCards(sparklingWineLinearLayout, sparklingWineObjects);

        fortifiedWineLinearLayout = (LinearLayout) rootView.findViewById(R.id.fortifiedWineLinearLayout);
        addButtonsToCards(fortifiedWineLinearLayout, fortifiedWineObjects);

        return rootView;

    }


    public void addButtonsToCards(LinearLayout cardLayout, ArrayList<GuideObject> items){

        LayoutInflater inflater = LayoutInflater.from(parentContext);
        View buttonRow = null;

        for(int i = 1; i < (items.size() + 1); i++){

            if(i%2 != 0 && i != items.size()){

                buttonRow= inflater.inflate(R.layout.guide_button_row, null, false);

                Button button1 = (Button) buttonRow.findViewById(R.id.guideRowButton1);
                button1.setText(items.get(i - 1).getVariety());
                button1.setOnClickListener(this);

            }else if(i%2 == 0){
                if(buttonRow != null){

                    Button button2 = (Button) buttonRow.findViewById(R.id.guideRowButton2);
                    button2.setText(items.get(i - 1).getVariety());
                    button2.setOnClickListener(this);

                    cardLayout.addView(buttonRow);
                }
            }else if(i%2 != 0 && i == items.size()){

                buttonRow= inflater.inflate(R.layout.guide_button_row, null, false);

                Button button1 = (Button) buttonRow.findViewById(R.id.guideRowButton1);
                button1.setText(items.get(i - 1).getVariety());
                button1.setOnClickListener(this);

                cardLayout.addView(buttonRow);
            }

        }

    }


    @Override
    public void onClick(View view) {

        Button pressedButton = (Button) view;
        String buttonText = pressedButton.getText().toString();
        GuideObject guideObject = new GuideObject();
        for(int i = 0; i < guideObjects.size(); i++){
            if(guideObjects.get(i).getVariety().equals(buttonText)){
                guideObject = guideObjects.get(i);
                break;
            }
        }
        ((MainActivity)getActivity()).setGuideDetailFragment(guideObject);


    }


}
