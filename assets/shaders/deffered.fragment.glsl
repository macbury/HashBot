varying vec4 v_position;
varying vec3 v_normal;
varying vec4 v_color;

varying vec2 v_texCoords0;
uniform vec4 u_diffuseColor;
uniform sampler2D u_diffuseTexture;
uniform sampler2D u_glowTexture;
void main() {
    vec4 diffuse    = texture2D(u_diffuseTexture, v_texCoords0) * u_diffuseColor;
    vec4 glow       = texture2D(u_glowTexture, v_texCoords0);

    gl_FragData[0] = vec4(diffuse.rgb, 1);
    gl_FragData[1] = vec4(v_normal.xyz, 1);
    gl_FragData[2] = vec4(v_position.xyz, 1);
    gl_FragData[3] = diffuse * glow;
}
