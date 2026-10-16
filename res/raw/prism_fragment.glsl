precision highp float;
uniform vec3 uMetal;
uniform float uBrightness;
uniform float uTime;
uniform float uFinish;
varying vec3 vNormal;
varying vec3 vPosition;
varying vec3 vBarycentric;
float studio(vec3 r, vec3 direction, float sharpness) {
    return pow(max(dot(r,normalize(direction)),0.0),sharpness);
}
void main() {
    vec3 n=normalize(vNormal);
    vec3 view=normalize(vec3(0.0,0.0,11.8)-vPosition);
    vec3 reflected=reflect(-view,n);
    float broad=studio(reflected,vec3(-.7,.85,1.0),9.0);
    float strip=studio(reflected,vec3(.8,.32,1.0),85.0);
    float top=studio(reflected,vec3(-.2,-.8,1.0),38.0);
    float rim=pow(1.0-max(dot(n,view),0.0),4.0);
    vec3 metal=uMetal;
    if(uFinish>.5) {
        vec3 coating=.5+.5*cos(vec3(0.0,2.1,4.2)+6.0*dot(n,view));
        metal=uMetal*.45+coating*.55;
    }
    vec3 color=metal*(.035+1.3*broad)+vec3(.86,.92,1.0)*strip*3.0
        +vec3(1.0,.85,.68)*top+rim*.3;
    float edge=min(vBarycentric.x,min(vBarycentric.y,vBarycentric.z));
    #ifdef PRISM_DERIVATIVES
    vec3 aa=smoothstep(vec3(0.0),max(fwidth(vBarycentric),vec3(.0001))*1.25,vBarycentric);
    float seam=min(aa.x,min(aa.y,aa.z));
    #else
    float seam=smoothstep(.004,.026,edge);
    #endif
    color*=mix(.38,1.0,seam);
    float vignette=1.0-.28*smoothstep(3.0,10.0,length(vPosition.xy));
    color*=uBrightness*vignette;
    color=clamp((color*(2.51*color+.03))/(color*(2.43*color+.59)+.14),0.0,1.0);
    color=pow(color,vec3(1.0/2.2));
    gl_FragColor=vec4(color,1.0);
}
