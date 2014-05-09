uniform sampler2D u_positionTexture;
uniform sampler2D u_texture;
uniform vec4      u_cameraPosition;
uniform vec4      u_fogColor;
varying vec2      v_texCoords;

void main() {
  vec4 position = texture2D(u_positionTexture, v_texCoords);
  vec4 color    = texture2D(u_texture, v_texCoords);

  vec3 flen     = u_cameraPosition.xyz - position.xyz;
  float fog     = dot(flen, flen) * u_cameraPosition.w;
  fog           = min(fog, 1.0);
  color.rgb     = mix(color.rgb, u_fogColor.rgb, fog);

  gl_FragColor  = color;
}