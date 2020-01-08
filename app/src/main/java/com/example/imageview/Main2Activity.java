package com.example.imageview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.view.View;
import android.widget.ImageView;


import com.example.android.imageview.ScriptC_histogram;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    private  void  toGrayRS(Bitmap bmp) {
        //1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
        //2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation input = Allocation.createFromBitmap(rs , bmp);
        Allocation  output= Allocation.createTyped(rs , input.getType ());
        //3)  Creer le  script
        ScriptC_gray grayScript = new ScriptC_gray(rs);
        //4)  Copier  les  donnees  dans  les  Allocations
        // ...
        // 5)  Initialiser  les  variables  globales  potentielles
        // ...
        // 6)  Lancer  le noyaugray
        grayScript.forEach_toGray(input , output);
        // 7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
        //8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy ();
        output.destroy ();
        grayScript.destroy ();
        rs.destroy ();
    }

    private  void  toGrayExceptOneColorRS(Bitmap bmp) {
        //1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
        //2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation input = Allocation.createFromBitmap(rs , bmp);
        Allocation  output= Allocation.createTyped(rs , input.getType ());
        //3)  Creer le  script
        ScriptC_grayExceptOneColor grayExceptOneColorScript = new ScriptC_grayExceptOneColor(rs);
        //4)  Copier  les  donnees  dans  les  Allocations
        // ...
        // 5)  Initialiser  les  variables  globales  potentielles
        Random r = new Random();
        grayExceptOneColorScript.set_color(r.nextInt(255));
        // 6)  Lancer  le noyau
        grayExceptOneColorScript.forEach_toGrayExceptOneColor(input , output);

        // 7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
        //8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy ();
        output.destroy ();
        grayExceptOneColorScript.destroy ();
        rs.destroy ();
    }

    private  void  colorizeRS(Bitmap bmp) {

        //1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
        //2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation  input = Allocation.createFromBitmap(rs , bmp);
        Allocation  output= Allocation.createTyped(rs , input.getType ());
        //3)  Creer le  script
        ScriptC_colorize colorScript = new ScriptC_colorize(rs);
        //4)  Copier  les  donnees  dans  les  Allocations
        // ...
        // 5)  Initialiser  les  variables  globales  potentielles
        Random r = new Random();
        colorScript.set_hue(r.nextInt(360));
        //colorScript.set_hue(50);
        // 6)  Lancer  le noyau
        colorScript.forEach_colorize(input , output);
        //7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
        //8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy ();
        output.destroy ();
        colorScript.destroy ();
        rs.destroy ();
    }

    private  void  ELDDrs(Bitmap bmp) {

        //1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
        //2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation  input = Allocation.createFromBitmap(rs , bmp);
        Allocation  output= Allocation.createTyped(rs , input.getType ());
        //3)  Creer le  script
        ScriptC_histogram histoScript = new ScriptC_histogram(rs);
        //4)  Copier  les  donnees  dans  les  Allocations
        // ...
        // 5)  Initialiser  les  variables  globales  potentielles

        // 6)  Lancer  le noyau
        histoScript.forEach_minMax(input);
        histoScript.forEach_ELDDrs(input,output);
        //7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
        //8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy ();
        output.destroy ();
        histoScript.destroy ();
        rs.destroy ();
    }


    private  void  dimConrs(Bitmap bmp) {

        //1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
        //2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation  input = Allocation.createFromBitmap(rs , bmp);
        Allocation  output= Allocation.createTyped(rs , input.getType ());
        //3)  Creer le  script
        ScriptC_histogram histoScript = new ScriptC_histogram(rs);
        //4)  Copier  les  donnees  dans  les  Allocations
        // ...
        // 5)  Initialiser  les  variables  globales  potentielles

        // 6)  Lancer  le noyau
        histoScript.forEach_minMax(input);
        histoScript.forEach_dimConrs(input,output);
        //7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
        //8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy ();
        output.destroy ();
        histoScript.destroy ();
        rs.destroy ();
    }

    private  void  ELDDColorrs(Bitmap bmp) {

        //1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
        //2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation  input = Allocation.createFromBitmap(rs , bmp);
        Allocation  output= Allocation.createTyped(rs , input.getType ());
        //3)  Creer le  script
        ScriptC_histogram histoScript = new ScriptC_histogram(rs);
        //4)  Copier  les  donnees  dans  les  Allocations
        // ...
        // 5)  Initialiser  les  variables  globales  potentielles

        // 6)  Lancer  le noyau
        histoScript.forEach_minMax(input);
        histoScript.forEach_ELDDColor(input,output);
        //7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
        //8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy ();
        output.destroy ();
        histoScript.destroy ();
        rs.destroy ();
    }

    private  void  egaliserHistors(Bitmap bmp) {

        //1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
        //2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation  input = Allocation.createFromBitmap(rs , bmp);
        Allocation  output= Allocation.createTyped(rs , input.getType ());
        //3)  Creer le  script
        ScriptC_histogram histoScript = new ScriptC_histogram(rs);
        //4)  Copier  les  donnees  dans  les  Allocations
        // ...
        // 5)  Initialiser  les  variables  globales  potentielles

        // 6)  Lancer  le noyau
        histoScript.forEach_minMax(input);
        histoScript.forEach_fillHisto(input);
        histoScript.forEach_egalHisto(input,output);
        //7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
        //8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy ();
        output.destroy ();
        histoScript.destroy ();
        rs.destroy ();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBtn(View v)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;

        ImageView image = (ImageView) findViewById(R.id.imageView);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
        b = b.copy(Bitmap.Config.ARGB_8888,true);
        image.setImageBitmap(b);
        toGrayRS(b);
    }

    public void onClickBtnColorize(View v)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;

        ImageView image = (ImageView) findViewById(R.id.imageView);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
        b = b.copy(Bitmap.Config.ARGB_8888,true);
        image.setImageBitmap(b);
        colorizeRS(b);
    }

    public void onClickBtnToGrayExceptOneColor(View v)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;

        ImageView image = (ImageView) findViewById(R.id.imageView);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
        b = b.copy(Bitmap.Config.ARGB_8888,true);
        image.setImageBitmap(b);
        toGrayExceptOneColorRS(b);
    }

    public void onClickBtnELDD(View v)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;

        ImageView image = (ImageView) findViewById(R.id.imageView2);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
        b = b.copy(Bitmap.Config.ARGB_8888,true);
        image.setImageBitmap(b);
        ELDDrs(b);
    }

    public void onClickBtnDimCon(View v)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;

        ImageView image = (ImageView) findViewById(R.id.imageView3);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image5);
        b = b.copy(Bitmap.Config.ARGB_8888,true);
        image.setImageBitmap(b);
        dimConrs(b);
    }

    public void onClickBtnELDDColor(View v)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;

        ImageView image = (ImageView) findViewById(R.id.imageView);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
        b = b.copy(Bitmap.Config.ARGB_8888,true);
        image.setImageBitmap(b);
        ELDDColorrs(b);
    }

    public void onClickBtnEgaliserHisto(View v)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;

        ImageView image = (ImageView) findViewById(R.id.imageView2);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
        b = b.copy(Bitmap.Config.ARGB_8888,true);
        image.setImageBitmap(b);
        ELDDColorrs(b);
    }

    public void onClickButtonReset(View v)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;

        ImageView image = (ImageView) findViewById(R.id.imageView);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
        b = b.copy(Bitmap.Config.ARGB_8888,true);
        image.setImageBitmap(b);

        ImageView image1 = (ImageView) findViewById(R.id.imageView2);
        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
        b1 = b1.copy(Bitmap.Config.ARGB_8888,true);
        image1.setImageBitmap(b1);

        ImageView image2 = (ImageView) findViewById(R.id.imageView3);
        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.image5);
        b2 = b2.copy(Bitmap.Config.ARGB_8888,true);
        image2.setImageBitmap(b2);

    }




}