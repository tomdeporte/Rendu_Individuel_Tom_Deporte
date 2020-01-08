#pragma  version (1)
#pragma  rs  java_package_name(com.example.imageview)

static  const  float4  weight = {0.299f, 0.587f, 0.114f, 0.0f};
float color = 0;

float3 RS_KERNEL getHSV(uchar4 in ) { // Je n'arrive pas a appeler la fonction dans colorize()

const float4 f4  = rsUnpackColor8888(in);

 // convert rgb to hsv
 float r = f4.r ;
 float g = f4.g ;
 float b = f4.b ;

 // convert rgb to hsv

 float minRGB = min(r, min(g, b));
 float maxRGB = max(r, max(g, b));
 float deltaRGB = maxRGB - minRGB;

 float h = 0.0;
 float s = maxRGB == 0 ? 0 : (maxRGB - minRGB) / maxRGB;
 float v = maxRGB;

 if (deltaRGB != 0) {

  if (r == maxRGB) {
   h = (g - b) / deltaRGB;
  } else {
   if (g == maxRGB) {
    h = 2 + (b - r) / deltaRGB;
   } else {
    h = 4 + (r - g) / deltaRGB;
   }
  }

  h *= 60;
  if (h < 0) {
   h += 360;
  }
  if (h == 360) {
   h = 0;
  }
 }
 float3 hsv = {
  h,
  s,
  v
 };
 return hsv;

}

uchar4  RS_KERNEL  toGrayExceptOneColor(uchar4  in) {
        const float4 f4  = rsUnpackColor8888(in);
        const  float  gray = dot(f4 , weight);

        // convert rgb to hsv
        float r = f4.r ;
        float g = f4.g ;
        float b = f4.b ;

        float3 hsv=getHSV(in);
        float h = hsv.x;
        if(h<color-30 || h>color+30){
           return  rsPackColorTo8888(gray , gray , gray , f4.a);
        }

        return rsPackColorTo8888(r,g,b);


}