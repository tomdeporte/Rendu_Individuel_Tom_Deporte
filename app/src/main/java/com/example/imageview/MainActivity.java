package com.example.imageview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;



    public class MainActivity extends AppCompatActivity {

        int[] CustomHSVtoRGB(float[] hsv) {

            int rgb[] = new int[3];
            int ti = (int) (hsv[0] / 60) % 6;
            int f = (int) (hsv[0] / 60) - ti;
            int l = (int) (hsv[2] * (1 - hsv[1]));
            int m = (int) (hsv[2] * (1 - f * hsv[1]));
            int n = (int) (hsv[2] * (1 - f) * hsv[1]);
            switch (ti) {
                case 0:
                    rgb[0] = (int) hsv[2];
                    rgb[1] = n;
                    rgb[2] = l;
                    break;
                case 1:
                    rgb[0] = m;
                    rgb[1] = (int) hsv[2];
                    rgb[2] = l;
                    break;
                case 2:
                    rgb[0] = l;
                    rgb[1] = (int) hsv[2];
                    rgb[2] = n;
                    break;
                case 3:
                    rgb[0] = l;
                    rgb[1] = m;
                    rgb[2] = (int) hsv[2];
                    break;
                case 4:
                    rgb[0] = n;
                    rgb[1] = l;
                    rgb[2] = (int) hsv[2];
                    break;
                case 5:
                    rgb[0] = (int) hsv[2];
                    rgb[1] = l;
                    rgb[2] = m;
                    break;
                default:
                    rgb[0] = 0;
                    rgb[1] = 0;
                    rgb[2] = 0;
                    break;
            }
            return rgb;
        }

        float[] CustomColortoHSV(int pixel) {
            float[] hsv = new float[3];
            int[] rgb = {Color.red(pixel), Color.green(pixel), Color.blue(pixel)};
            float min = rgb[0];
            float max = rgb[0];
            for (int i = 0; i < rgb.length; i++) {
                if (rgb[i] < min) {
                    min = rgb[i];
                }
                if (rgb[i] > max) {
                    max = rgb[i];
                }
            }
            if (max == min) {
                hsv[0] = 0;
            } else {
                if (max == rgb[0]) {
                    hsv[0] = (float) (((60) * ((rgb[1] - rgb[2]) / (max - min)) + (360) % 360));
                } else {
                    if (max == rgb[1]) {
                        hsv[0] = (float) ((60) * ((rgb[2] - rgb[0]) / (max - min)) + (120));
                    } else {
                        if (max == rgb[2]) {
                            hsv[0] = (float) ((60) * ((rgb[0] - rgb[1]) / (max - min)) + (240));
                        }
                    }
                }
            }
            if (max == 0) {
                hsv[1] = 0;
            } else {
                hsv[1] = 1 - (min / max);
            }
            hsv[2] = max;
            return hsv;
        }

        int pixelToGray(int pixel) {
            int r = Color.red(pixel);
            int g = Color.green(pixel);
            int b = Color.blue(pixel);

            return (r + b + g) / 3;
        }

        int pixelToGray2(int pixel) {
            int alpha = 0xFF << 24;
            int grey = pixel;

            int r = ((grey & 0x00FF0000) >> 16);
            int g = ((grey & 0x0000FF00) >> 8);
            int b = (grey & 0x000000FF);

            grey = (int) ((float) r * 0.3 + (float) g * 0.59 + (float) b * 0.11);
            grey = alpha | (grey << 16) | (grey << 8) | grey;
            return grey;

        }

        void toGray(Bitmap bmp) {

            int width = bmp.getWidth();
            int height = bmp.getHeight();
            final int[] pixels = new int[width * height];
            bmp.getPixels(pixels, 0, width, 0, 0, width, height);
            for (int i = 0; i < width; i++) {
                for (int y = 0; y < height; y++) {
                    final int offset = y * width + i;
                    pixels[offset] = pixelToGray2(pixels[offset]);
                    //System.out.println("offest : "+offset+" gray : "+pixels[offset]);
                }
            }
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);
        }


        int greyPixelToColor(int pixel, float h) {
            int color;
            int rgb[];

            float[] hsv = CustomColortoHSV(pixel);
            hsv[0] = h;
            rgb = CustomHSVtoRGB(hsv);
            color = Color.rgb(rgb[0], rgb[1], rgb[2]);
            return color;
        }

        public void colorize(Bitmap bmp) {
            Random random = new Random();
            float h = 1 + (360 - 1) * random.nextFloat();
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            final int[] pixels = new int[width * height];
            bmp.getPixels(pixels, 0, width, 0, 0, width, height);
            for (int i = 0; i < width; i++) {
                for (int y = 0; y < height; y++) {
                    final int offset = y * width + i;
                    pixels[offset] = greyPixelToColor(pixels[offset], h);
                }
            }
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);
        }

        void toGrayExceptOneColor(Bitmap bmp, float c) {
            float min = c - 30;
            float max = c + 30;

            int width = bmp.getWidth();
            int height = bmp.getHeight();
            final int[] pixels = new int[width * height];
            bmp.getPixels(pixels, 0, width, 0, 0, width, height);
            for (int i = 0; i < width; i++) {
                for (int y = 0; y < height; y++) {
                    final int offset = y * width + i;
                    float[] hsv;
                    hsv = CustomColortoHSV(pixels[offset]);
                    if (!(hsv[0] > min && hsv[0] < max)) {
                        pixels[offset] = pixelToGray2(pixels[offset]);
                    }
                }
            }
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);
        }


        int[] returnHistotgram(Bitmap bmp) {
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            final int[] pixels = new int[width * height];
            bmp.getPixels(pixels, 0, width, 0, 0, width, height);
            int[] histo = new int[256];
            for (int i = 0; i < height; i++) {
                for (int y = 0; y < width; y++) {
                    final int offset = y * width + i;
                    histo[pixelToGray(pixels[offset])]++;
                }
            }
            return histo;
        }


        void ELDD(Bitmap bmp) {

            int min = 255;
            int max = 0;
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            final int[] pixels = new int[width * height];
            bmp.getPixels(pixels, 0, width, 0, 0, width, height);
            for (int i = 0; i < width; i++) {
                for (int y = 0; y < height; y++) {
                    final int offset = y * width + i;
                    int pixel = pixelToGray(pixels[offset]);
                    if (pixel < min) {

                        min = pixel;
                    } else {
                        if (pixel > max) {
                            max = pixel;
                        }
                    }
                }

            }

            for (int i = 0; i < width; i++) {
                for (int y = 0; y < height; y++) {
                    final int offset = y * width + i;
                    int I = pixelToGray(pixels[offset]);
                    int newI = (255 * (I - min)) / (max - min);
                    // System.out.println("offset = "+offset+" I: "+I+" newI : "+newI);
                    pixels[offset] = Color.rgb(newI, newI, newI);
                }
            }
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);
        }

        void DimCon(Bitmap bmp) {

            int min = 255;
            int max = 0;
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            final int[] pixels = new int[width * height];
            bmp.getPixels(pixels, 0, width, 0, 0, width, height);
            for (int i = 0; i < width; i++) {
                for (int y = 0; y < height; y++) {
                    final int offset = y * width + i;
                    int pixel = pixelToGray(pixels[offset]);
                    if (pixel < min) {

                        min = pixel;
                    } else {
                        if (pixel > max) {
                            max = pixel;
                        }
                    }
                }

            }
            min += 30;
            max -= 30;
            for (int i = 0; i < width; i++) {
                for (int y = 0; y < height; y++) {
                    final int offset = y * width + i;
                    int I = pixelToGray(pixels[offset]);
                    int newI = ((255) * (I - min)) / (max - min);
                    //System.out.println("offset = "+offset+" I: "+I+" newI : "+newI);
                    if (newI > 30 && newI < 225) {
                        pixels[offset] = Color.rgb(newI, newI, newI);
                    } else {
                        if (newI < 30) {
                            pixels[offset] = Color.rgb(30, 30, 30);
                        }
                        if (newI > 225) {
                            pixels[offset] = Color.rgb(225, 225, 225);
                        }
                    }

                }
            }
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);
        }


        void ELDDColor(Bitmap bmp) {
            int value = 100;
            double contrast = Math.pow((100 + value) / 100, 2);
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            final int[] pixels = new int[width * height];
            bmp.getPixels(pixels, 0, width, 0, 0, width, height);

            for (int i = 0; i < width; i++) {
                for (int y = 0; y < height; y++) {
                    final int offset = y * width + i;
                    int pixel = bmp.getPixel(i, y);
                    int R = Color.red(pixel);
                    int G = Color.green(pixel);
                    int B = Color.blue(pixel);
                    R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                    if (R < 0) {
                        R = 0;
                    } else if (R > 255) {
                        R = 255;
                    }

                    G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                    if (G < 0) {
                        G = 0;
                    } else if (G > 255) {
                        G = 255;
                    }

                    B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                    if (B < 0) {
                        B = 0;
                    } else if (B > 255) {
                        B = 255;
                    }


                    pixels[offset] = Color.rgb(R, G, B);

                }
            }
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);

        }

        void EgalH(Bitmap bmp) {
            int[] hist = returnHistotgram(bmp);
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            final int[] pixels = new int[width * height];
            bmp.getPixels(pixels, 0, width, 0, 0, width, height);


            for (int i = 1; i < 255; i++) {
                hist[i] = hist[i - 1] + hist[i];
            }
            for (int i = 0; i < width; i++) {
                for (int y = 0; y < height; y++) {
                    final int offset = y * width + i;
                    int I = pixelToGray(pixels[offset]);
                    int newI = (hist[I] * 255) / (width * height);

                    pixels[offset] = Color.rgb(newI, newI, newI);

                }
            }
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);


        }


        int[] returnHistotgramColor(Bitmap bmp) {
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            int hist[] = new int[360];
            final int[] pixels = new int[width * height];
            bmp.getPixels(pixels, 0, width, 0, 0, width, height);
            for (int i = 0; i < width; i++) {
                for (int y = 0; y < height; y++) {
                    final int offset = y * width + i;
                    float[] hsv = new float[3];
                    Color.colorToHSV(pixels[offset], hsv);
                    hist[(int) hsv[0]]++;
                }
            }
            return hist;
        }

        void EgalHColor(Bitmap bmp) {
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            final int[] pixels = new int[width * height];
            bmp.getPixels(pixels, 0, width, 0, 0, width, height);
            int[] hist = returnHistotgramColor(bmp);

            for (int i = 1; i < 360; i++) {
                hist[i] = hist[i - 1] + hist[i];
            }
            for (int i = 0; i < width; i++) {
                for (int y = 0; y < height; y++) {
                    final int offset = y * width + i;

                    float[] hsv = new float[3];
                    Color.colorToHSV(pixels[offset], hsv);
                    hsv[0] = (360 * hist[(int) hsv[0]]) / (width * height);
                    int[] rgb = CustomHSVtoRGB(hsv);


                    pixels[offset] = Color.rgb(rgb[0], rgb[1], rgb[2]);

                }
            }

        /*
        for(int i=0;i<width;i++) {
            for (int y = 0; y < height; y++) {
                final int offset = y * width + i;
                float[] hsv = new float[3];
                Color.colorToHSV(pixels[offset],hsv);
                hsv[0]=hist[(int)hsv[0]];
                int[] rgb =CustomHSVtoRGB(hsv);

                pixels[offset] = (360*rgb[0])/(width*height);
                pixels[offset]  = (pixels[offset]  << 8) + rgb[1];
                pixels[offset]  = (pixels[offset]  << 8) + rgb[2];
            }
        }*/
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);

        }


        //Convolution


        void convolutionMoyenneurNxN(Bitmap bmp, int i, int y, int[] initPixels, int w) {
            int width = bmp.getWidth();
            int somme = 0;
            int compteur = 0;

            for (int x = i - w; x <= i + w; x++) {
                for (int z = y - w; z <= y + w; z++) {
                    final int offset = z * width + x;
                    int greyK = pixelToGray(initPixels[offset]);
                    compteur++;
                    somme = somme + (greyK);

                }
            }
            int moyenne = somme / compteur;
            for (int x = i - w; x <= i + w; x++) {
                for (int z = y - w; z <= y + w; z++) {
                    final int offset = z * width + x;
                    initPixels[offset] = Color.rgb(moyenne, moyenne, moyenne);
                }
            }


        }

    /* TRANSCRIRE EN JAVA
    double magX = 0.0; // this is your magnitude

    for(int a = 0; a < 3; a++)
    {
        for(int b = 0; b < 3; b++)
        {
            int xn = x + a - 1;
            int yn = y + b - 1;

            int index = xn + yn * width;
            magX += image[index] * kernelx[a][b];
        }
 }

    */


        void contourDetection(Bitmap bmp) {
            toGray(bmp);
            int maxGval = 0;
            int maxGradient = -1;
            float sobel_x[][] = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
            float sobel_y[][] = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            int[][] edgeColors = new int[width][height];
            final int[] pixels = new int[width * height];
            bmp.getPixels(pixels, 0, width, 0, 0, width, height);

            for (int x = 1; x < width - 1; x++) {
                for (int y = 1; y < height - 1; y++) {

                    float pixel_x = (sobel_x[0][0] * pixelToGray2(pixels[(y - 1) * width + (x - 1)]) + (sobel_x[0][1] * pixelToGray2(pixels[(y - 1) * width + (x)]) + (sobel_x[0][2] * pixelToGray2(pixels[(y - 1) * width + (x + 1)]) +
                            (sobel_x[1][0] * pixelToGray2(pixels[y * width + (x - 1)]) + (sobel_x[1][1] * pixelToGray2(pixels[(y * width + x)]) + (sobel_x[1][2] * pixelToGray2(pixels[y * width + (x + 1)])) +
                                    (sobel_x[2][0] * pixelToGray2(pixels[(y + 1) * width + (x - 1)]) + (sobel_x[2][1] * pixelToGray2(pixels[(y + 1) * width + x]) + (sobel_x[2][2] * pixelToGray2(pixels[(y + 1) * width + (x + 1)])))))))));

                    float pixel_y = (sobel_y[0][0] * pixelToGray2(pixels[(y - 1) * width + (x - 1)]) + (sobel_y[0][1] * pixelToGray2(pixels[(y - 1) * width + (x)]) + (sobel_y[0][2] * pixelToGray2(pixels[(y - 1) * width + (x + 1)]) +
                            (sobel_y[1][0] * pixelToGray2(pixels[y * width + (x - 1)]) + (sobel_y[1][1] * pixelToGray2(pixels[(y * width + x)]) + (sobel_y[1][2] * pixelToGray2(pixels[y * width + (x + 1)])) +
                                    (sobel_y[2][0] * pixelToGray2(pixels[(y + 1) * width + (x - 1)]) + (sobel_y[2][1] * pixelToGray2(pixels[(y + 1) * width + x]) + (sobel_y[2][2] * pixelToGray2(pixels[(y + 1) * width + (x + 1)])))))))));

                    double gval = Math.sqrt((pixel_x * pixel_x) + (pixel_y * pixel_y));
                    int g = (int) gval;

                    if (maxGradient < g) {
                        maxGradient = g;
                    }
                    edgeColors[x][y] = g;
                }
            }

            double scale = 255.0 / maxGradient;

            for (int i = 1; i < width - 1; i++) {
                for (int j = 1; j < height - 1; j++) {
                    int edgeColor = edgeColors[i][j];
                    edgeColor = (int) (edgeColor * scale);
                    edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

                    pixels[j * width + i] = edgeColor;
                }
            }

            bmp.setPixels(pixels, 0, width, 0, 0, width, height);


        }

        void convolutionMoyenneur(Bitmap bmp) {
            int w = 3;
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            final int[] pixels = new int[width * height];
            bmp.getPixels(pixels, 0, width, 0, 0, width, height);

            for (int i = w; i < width - w; i++) {
                for (int y = w; y < height - w; y++) {
                    convolutionMoyenneurNxN(bmp, i, y, pixels, w);
                }
            }
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

        public void onClickBtn(View v) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;

            ImageView image = (ImageView) findViewById(R.id.imageView);
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
            b = b.copy(Bitmap.Config.ARGB_8888, true);
            image.setImageBitmap(b);
            toGray(b);
        }

        public void onClickBtnColor(View v) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;

            ImageView image = (ImageView) findViewById(R.id.imageView);
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
            b = b.copy(Bitmap.Config.ARGB_8888, true);
            image.setImageBitmap(b);
            colorize(b);
        }

        public void onClickBtntoGrayExcept(View v) {
            Random random = new Random();
            int h = random.nextInt(360);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;

            ImageView image = (ImageView) findViewById(R.id.imageView);
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
            b = b.copy(Bitmap.Config.ARGB_8888, true);
            image.setImageBitmap(b);
            toGrayExceptOneColor(b, h);
        }

        public void onClickBtntELDD(View v) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            ImageView image = (ImageView) findViewById(R.id.imageView);
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
            b = b.copy(Bitmap.Config.ARGB_8888, true);
            image.setImageBitmap(b);
            ELDD(b);
        }

        public void onClickBtntELDDColor(View v) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            ImageView image = (ImageView) findViewById(R.id.imageView);
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
            b = b.copy(Bitmap.Config.ARGB_8888, true);
            image.setImageBitmap(b);
            ELDDColor(b);
        }

        public void onClickBtntDimCon(View v) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            ImageView image = (ImageView) findViewById(R.id.imageView3);
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image5);
            b = b.copy(Bitmap.Config.ARGB_8888, true);
            image.setImageBitmap(b);
            DimCon(b);
        }

        public void onClickButtonEgalH(View v) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            ImageView image = (ImageView) findViewById(R.id.imageView2);
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
            b = b.copy(Bitmap.Config.ARGB_8888, true);
            image.setImageBitmap(b);
            EgalH(b);
        }

        public void onClickButtonEgalHColor(View v) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            ImageView image = (ImageView) findViewById(R.id.imageView2);
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
            b = b.copy(Bitmap.Config.ARGB_8888, true);
            image.setImageBitmap(b);
            EgalHColor(b);
        }

        public void onClickButtonConvolutionMoyenneur(View v) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            ImageView image = (ImageView) findViewById(R.id.imageView2);
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
            b = b.copy(Bitmap.Config.ARGB_8888, true);
            image.setImageBitmap(b);
            convolutionMoyenneur(b);
        }

        public void onClickButtonContourDetection(View v) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            ImageView image = (ImageView) findViewById(R.id.imageView);
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
            b = b.copy(Bitmap.Config.ARGB_8888, true);
            image.setImageBitmap(b);
            contourDetection(b);
        }

        public void onClickButtonReset(View v) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;

            ImageView image = (ImageView) findViewById(R.id.imageView2);
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
            b = b.copy(Bitmap.Config.ARGB_8888, true);
            image.setImageBitmap(b);

            ImageView image1 = (ImageView) findViewById(R.id.imageView);
            Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
            b1 = b1.copy(Bitmap.Config.ARGB_8888, true);
            image1.setImageBitmap(b1);

            ImageView image2 = (ImageView) findViewById(R.id.imageView3);
            Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.image5);
            b2 = b2.copy(Bitmap.Config.ARGB_8888, true);
            image2.setImageBitmap(b2);

        }

        public void nextActivity(View v) {
            Intent myIntent = new Intent(getBaseContext(), Main2Activity.class);
            startActivity(myIntent);
        }
    }

