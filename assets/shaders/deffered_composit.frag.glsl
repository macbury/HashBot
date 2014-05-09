varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_colorTexture;
uniform sampler2D u_normalTexture;
uniform sampler2D u_positionTexture;
uniform vec4      u_ambientLight;

void main() {
  vec4 color    = texture2D(u_colorTexture, v_texCoords);
  vec4 normal   = texture2D(u_normalTexture, v_texCoords);
  vec4 position = texture2D(u_positionTexture, v_texCoords);

  vec4 final    = v_color * color * u_ambientLight;
  //final.a = 0.3f;
  gl_FragColor  = final;
}