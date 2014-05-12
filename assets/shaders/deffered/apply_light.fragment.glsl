varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform sampler2D u_colorTexture;
uniform sampler2D u_normalTexture;
uniform vec4      u_ambientLight;
uniform vec4      u_cameraPosition;
uniform mat4      u_projViewTrans;

struct DirectionalLight {
    vec4 color;
    vec3 direction;
};

uniform DirectionalLight u_dirLight;

void main() {
  vec4 color    = texture2D(u_colorTexture, v_texCoords);
  vec4 normal   = texture2D(u_normalTexture, v_texCoords);
  vec4 lightMap = texture2D(u_texture, v_texCoords);
  vec3 lightDir         = -u_dirLight.direction;
  float NdotL           = clamp(dot(normal.xyz, lightDir), 0.0, 1.0);
  vec4 lightDiffuse     = vec4(u_dirLight.color.rgb * NdotL, 0);

  vec4 final            = v_color * color * (u_ambientLight + lightDiffuse + lightMap);

  gl_FragColor          = final;
}