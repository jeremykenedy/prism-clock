precision highp float;
attribute vec2 aPosition;
attribute vec2 aCorner0;
attribute vec2 aCorner1;
attribute vec2 aCorner2;
attribute vec3 aBarycentric;
uniform mat4 uMatrix;
uniform float uTime;
uniform float uDepth;
varying vec3 vNormal;
varying vec3 vPosition;
varying vec3 vBarycentric;
float height(vec2 p) {
    return uDepth * (sin(p.x*.56 + uTime*.71)*cos(p.y*.43-uTime*.31)
         + .57*sin(p.x*.27+p.y*.72-uTime*.58)
         + .31*cos(length(p-vec2(1.8,-.9))*.9-uTime*.83));
}
vec3 surface(vec2 p) { return vec3(p,height(p)); }
void main() {
    vec3 p0=surface(aCorner0),p1=surface(aCorner1),p2=surface(aCorner2);
    vNormal=normalize(cross(p1-p0,p2-p0));
    vPosition=vec3(aPosition,p0.z*aBarycentric.x+p1.z*aBarycentric.y+p2.z*aBarycentric.z);
    vBarycentric=aBarycentric;
    gl_Position=uMatrix*vec4(vPosition,1.0);
}
