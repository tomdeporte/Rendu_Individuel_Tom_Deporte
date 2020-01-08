#pragma  version (1)
#pragma  rs  java_package_name(com.example.imageview)

float hue = 0;

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

uchar4 RS_KERNEL HSVToUCHAR4(float3 hsv) {
 float r = 0;
 float g = 0;
 float b = 0;

 float h = hsv.x;
 float s = hsv.y;
 float v = hsv.z;

 int ti = (int)fmod((h / 60), 6);
 float f = (h / 60) - ti;
 float l = v * (1 - s);
 float m = v * (1 - f * s);
 float n = v * (1 - f) * s;
 if (ti == 0) {
  r = v;
  g = n;
  b = l;
 }
 if (ti == 1) {
  r = m;
  g = v;
  b = 1;
 }
 if (ti == 2) {
  r = 1;
  g = v;
  b = n;
 }
 if (ti == 3) {
  r = 1;
  g = m;
  b = v;
 }
 if (ti == 4) {
  r = n;
  g = 1;
  b = v;
 }
 if (ti == 5) {
  r = v;
  g = 1;
  b = m;
 }


 return rsPackColorTo8888(r, g, b);
}



uchar4 RS_KERNEL colorize(uchar4 in ) {
 //Convert input uchar4 to float4
 float4 f4 = rsUnpackColor8888( in );
 // convert rgb to hsv
 float3 hsv=getHSV(in);
 hsv.x = hue;
 uchar4 rgb = HSVToUCHAR4(hsv);
 return rgb;
}