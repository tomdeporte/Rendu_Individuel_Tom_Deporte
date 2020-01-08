#pragma version(1)
#pragma rs java_package_name(com.example.android.imageview)
#pragma rs_fp_relaxed

int32_t *histogram;
int32_t *LUT;

static  const  float4  weight = {0.299f, 0.587f, 0.114f, 0.0f};

float minI=255;
float maxI=0;

void RS_KERNEL compute_histogram(uchar4 in)
{
    volatile int32_t *addr = &histogram[in.r];

    rsAtomicInc(addr);
}

void RS_KERNEL fillHisto(uchar4 in)
{
    const  float4  pixelf = rsUnpackColor8888(in);
    const  float  gray = dot(pixelf , weight);
    int g = gray*255;
    histogram[g]++;
}

void RS_KERNEL minMax(uchar4 in)
{
    const  float4  pixelf = rsUnpackColor8888(in);
    const  float  gray = dot(pixelf , weight);

    int g = gray*255;

    if(g<minI){
        minI=g;
    }
    if(g>maxI){
        maxI=g;
    }
}

uchar4 RS_KERNEL apply_histogram(uchar4 in)
{
    uchar val = LUT[in.r];
    uchar4 result;
    result.r = result.g = result.b = val;
    result.a = in.a;

    return(result);
}

uchar4 RS_KERNEL egalHisto(uchar4 in)
{
 const  float4  pixelf = rsUnpackColor8888(in);
 const  float  gray = dot(pixelf , weight);
 int g = gray*255;
 int newI =(255*(g-minI))/(maxI-minI);
 // System.out.println("offset = "+offset+" I: "+I+" newI : "+newI);
 uchar4 result = rsPackColorTo8888(newI,newI,newI);
 return  result;
}

uchar4 RS_KERNEL ELDDrs(uchar4 in)
{
 int value =200;
 float contrast = pow(((100 + value) / 100),2.0f);
 // convert rgb to hsv
 float R = in.r;
 int gr = in.r*255;

 R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
 if(R < 0) { R = 0; }
 else if(R > 255) { R = 255; }

 uchar4 result = rsPackColorTo8888(R,R,R);
 return  result;
}

uchar4 RS_KERNEL dimConrs(uchar4 in){

 int difference = 10;
 minI = minI+difference;
 maxI = maxI-difference;

 float gr = in.r;
// rsDebug("gr:",gr);
  //float newI =(((255)*(gr-minI))/(maxI-minI));//RESULTAT TOUJOURS 255/2
 float newI = gr;
 //rsDebug("newI:",newI);
 uchar4 result ;
 if(newI>0+difference && newI<255-difference){
                                //rsDebug("newI:",newI);
    result = rsPackColorTo8888(newI/255,newI/255,newI/255);
    //rsDebug("result.r:",result.r);
 }else{
    if(newI<255-difference){
       result = rsPackColorTo8888(0+difference,0+difference,0+difference);
    }else{
        if(newI>0+difference){
            result = rsPackColorTo8888(255-difference,255-difference,255-difference);
        }
    }
 }
 return result;
}

uchar4 RS_KERNEL ELDDColor(uchar4 in){
        int value =100;
        double contrast = ((100 + value) / 100, 2)*((100 + value) / 100, 2);
         // convert rgb to hsv
         float R = in.r;
         float G = in.g;
         float B = in.b ;

        R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if(R < 0) { R = 0; }
        else if(R > 255) { R = 255; }

        G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if(G < 0) { G = 0; }
        else if(G > 255) { G = 255; }

        B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if(B < 0) { B = 0; }
        else if(B > 255) { B = 255; }

        uchar4 result = rsPackColorTo8888(R,G,B);
        return result;
    }

