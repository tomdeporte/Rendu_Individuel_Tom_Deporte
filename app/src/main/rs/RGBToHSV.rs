#pragma version(1)
#pragma rs java_package_name(com.example.imageview)

float3 RS_KERNEL getHSV(uchar4 in) {

    float r = in.r / 255.0;
    float g = in.g / 255.0;
    float b = in.b / 255.0;

    // convert rgb to hsv

    float minRGB = min( r, min( g, b ) );
    float maxRGB = max( r, max( g, b ) );
    float deltaRGB = maxRGB - minRGB;

    float h = 0.0;
    float s = maxRGB == 0 ? 0 : (maxRGB - minRGB) / maxRGB;
    float v = maxRGB;

    if (deltaRGB != 0) {

        if (r == maxRGB) {
            h = (g - b) / deltaRGB;
        }
        else {
            if (g == maxRGB) {
                h = 2 + (b - r) / deltaRGB;
            }
            else {
                h = 4 + (r - g) / deltaRGB;
            }
        }

        h *= 60;
        if (h < 0) { h += 360; }
        if (h == 360) { h = 0; }
    }
    float3 hsv = {h,s,v};
    return hsv;

}


