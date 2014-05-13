struct Light {
  vec4 color;
  vec3 position;
  float radius;
  float constantAttenuation;
  float linearAttenuation;
  float quadraticAttenuation;
};

uniform sampler2D u_normalTexture;
uniform sampler2D u_positionTexture;
uniform mat4 u_projViewTrans;
uniform Light u_light;
uniform vec4 u_cameraPosition;
uniform vec2 u_screenSize;
varying vec4 v_position;

uniform sampler2D u_colorTexture; //TODO REMOVE

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
        float att = 1.0 / (0.2f +
                        0.3f * distance +
                        0.1f * distance * distance);
        color += att * (u_light.color * NdotL);
    }

    return color;
}

void main() {
  vec2 textCoords = calcTexCoord();
  vec4 normal     = texture2D(u_normalTexture, textCoords);
  vec4 position   = texture2D(u_positionTexture, textCoords);
  vec4 color      = texture2D(u_colorTexture, textCoords);
  gl_FragColor    = color + calcPointLight(position.xyz, normal.xyz);

}