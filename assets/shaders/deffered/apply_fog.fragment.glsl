uniform sampler2D u_positionTexture;
uniform sampler2D u_texture;
uniform sampler2D u_fogTexture;
uniform vec2      u_mapSize;
uniform vec4      u_cameraPosition;
uniform vec4      u_fogColor;
varying vec2      v_texCoords;

void main() {
  vec4 position = texture2D(u_positionTexture, v_texCoords);
  vec4 color    = texture2D(u_texture, v_texCoords);

  vec2 fogCords = vec2(position.x / u_mapSize.x, position.z / u_mapSize.y);

  vec4 fogPixel = texture2D(u_fogTexture, fogCords);

  vec3 flen     = u_cameraPosition.xyz - position.xyz;
  float fog     = dot(flen, flen) * u_cameraPosition.w;
  fog           = max(fog, 1.0 - fogPixel.r);
  fog           = min(fog, 1.0);
  color.rgb     = mix(color.rgb, u_fogColor.rgb, fog);

  gl_FragColor  = color;
}