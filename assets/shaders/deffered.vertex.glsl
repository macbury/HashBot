attribute vec3 a_position;
attribute vec4 a_color;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

varying vec4   v_position;
varying vec4   v_color;
varying vec3   v_normal;
varying vec2   v_texCoords0;

uniform mat4   u_projViewTrans;
uniform vec4   u_cameraPosition;
uniform mat3   u_normalMatrix;
uniform mat4   u_worldTrans;

void main() {
	v_texCoords0 = a_texCoord0;
    v_color      = a_color;
    v_normal     = normalize(u_normalMatrix * a_normal);
    vec4 pos     = u_worldTrans * vec4(a_position, 1.0);
	v_position   = u_projViewTrans * pos;
    gl_Position  = v_position;
}
