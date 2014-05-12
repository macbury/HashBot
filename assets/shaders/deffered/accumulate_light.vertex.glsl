uniform mat4 u_projViewTrans;
uniform mat4 u_modelTrans;

attribute vec4 a_position;
varying   vec4 v_position;

void main() {
  v_position  = u_modelTrans * a_position;
  gl_Position = u_projViewTrans * v_position;
}