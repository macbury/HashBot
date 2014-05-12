struct Light {
  vec4 color;
  vec3 position;
  float radius;
  float constantAttenuation;
  float linearAttenuation;
  float quadraticAttenuation;
};

uniform sampler2D u_colorTexture;
uniform sampler2D u_normalTexture;
uniform sampler2D u_positionTexture;
uniform mat4 u_projViewTrans;
uniform Light u_light;
uniform vec4 u_cameraPosition;
uniform vec2 u_screenSize;
varying vec4 v_position;

vec2 calcTexCoord() {
   return gl_FragCoord.xy / u_screenSize;
}

vec4 calcPointLight(vec3 position, vec3 normal){
    vec3 lightDirection = position - u_light.position;
    float distance      = length(lightDirection);
    lightDirection      = normalize(lightDirection);

    vec4  color = vec4(0,0,0,0);
    float NdotL = max(dot(normal,lightDirection),0.0);

    if (NdotL > 0.0) {
        float att = 1.0 / (u_light.constantAttenuation +
                        u_light.linearAttenuation * distance +
                        u_light.quadraticAttenuation * distance * distance);
        color += att * (u_light.color * NdotL);
    }

    return color;
}

void main() {
  vec2 textCoords = calcTexCoord();
  vec4 color      = texture2D(u_colorTexture, textCoords);
  vec4 normal     = texture2D(u_normalTexture, textCoords);
  vec4 position   = texture2D(u_positionTexture, textCoords);

  gl_FragColor    = calcPointLight(position.xyz, normal.xyz);

}