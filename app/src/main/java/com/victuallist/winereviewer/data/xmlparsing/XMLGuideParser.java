package com.victuallist.winereviewer.data.xmlparsing;

import android.content.Context;
import android.util.Xml;

import com.victuallist.winereviewer.data.objects.GuideObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by john on 2/3/15.
 */
public class XMLGuideParser {

    private static final String ns = null;

    InputStream inputStream;

    public XMLGuideParser(Context context){
        try {

            inputStream =  context.getAssets().open("guide.xml");

        }catch(IOException e){

        }
    }

    public ArrayList<GuideObject> parse () throws XmlPullParserException, IOException{

        try{

            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);

        }finally{
            inputStream.close();
        }

    }

    private ArrayList<GuideObject> readFeed (XmlPullParser parser) throws XmlPullParserException, IOException{

        ArrayList<GuideObject> items = new ArrayList<>();
        GuideObject guideObject = new GuideObject();

        parser.require(XmlPullParser.START_TAG, ns, "guide");
//        parser.require(XmlPullParser.END_TAG, ns, "guide");

        while(parser.next() != XmlPullParser.END_DOCUMENT){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if(name.equals("wine")){
                guideObject = new GuideObject();
            }else if(name.equals("type")){
                if (parser.next() == XmlPullParser.TEXT) {
                    String type = parser.getText();
                    guideObject.setType(type);
                    parser.nextTag();
                }else{
                    parser.next();
                }

            }else if(name.equals("variety")){
                if (parser.next() == XmlPullParser.TEXT) {
                    String variety = parser.getText();
                    guideObject.setVariety(variety);
                    parser.nextTag();
                }else{
                    parser.next();
                }

            }else if (name.equals("origin")){
                if (parser.next() == XmlPullParser.TEXT) {
                    String origin = parser.getText();
                    guideObject.setOrigin(origin);
                    parser.nextTag();
                }else{
                    parser.next();
                }
            }else if(name.equals("grapes")){
                if (parser.next() == XmlPullParser.TEXT) {
                    String grapes = parser.getText();
                    guideObject.setGrapes(grapes);
                    parser.nextTag();
                }else{
                    parser.next();
                }
            }else if(name.equals("flavor")){
                if (parser.next() == XmlPullParser.TEXT) {
                    String flavor = parser.getText();
                    guideObject.setFlavor(flavor);
                    parser.nextTag();
                }else{
                    parser.next();
                }

            }else if(name.equals("pairing")){
                if (parser.next() == XmlPullParser.TEXT) {
                    String pairing = parser.getText();
                    guideObject.setPairing(pairing);
                }else{
                    parser.next();
                }
                items.add(guideObject);

            }else{
                skip(parser);
            }
        }

        return items;

    }

//    private ArrayList<GameSquare> readRow(XmlPullParser parser) throws XmlPullParserException, IOException{
//
//        ArrayList<GameSquare> row = new ArrayList<GameSquare>();
//
//        while (parser.next() != XmlPullParser.END_TAG) {
//
//            int color = 0;
//
//            if (parser.getEventType() != XmlPullParser.START_TAG) {
//                continue;
//            }
//            String name = parser.getName();
//            if (name.equals("column")) {
//                color = readColumn(parser);
//                row.add(new GameSquare(color));
//            }else {
//                skip(parser);
//            }
//        }
//
//        return row;
//
//    }
//
//    private int readColumn(XmlPullParser parser) throws XmlPullParserException, IOException{
//        parser.require(XmlPullParser.START_TAG, ns, "column");
//        int color = readColor(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "column");
//        return color;
//    }
//
//    private int readColor(XmlPullParser parser) throws IOException, XmlPullParserException {
//        int result = 6;
//        if (parser.next() == XmlPullParser.TEXT) {
//            result = Integer.parseInt(parser.getText());
//            parser.nextTag();
//        }
//        return result;
//    }
//
//
    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
