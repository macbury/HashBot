uniform sampler2D u_glowTexture;
uniform sampler2D u_texture;
uniform sampler2D u_blurTexture;
varying vec2      v_texCoords;

void main() {
  vec4 glow     = texture2D(u_glowTexture, v_texCoords);
  vec4 color    = texture2D(u_texture, v_texCoords);
  vec4 blur     = texture2D(u_blurTexture, v_texCoords);
  gl_FragColor  = color + blur + glow;
}