uniform sampler2D u_glowTexture; // the texture with the scene you want to blur
varying vec4 v_color;
varying vec2 v_texCoords;
 
uniform float blurSize; // I've chosen this size because this will result in that every step will be one pixel wide if the u_texture texture is of size 512x512
 
void main(void)
{
   vec4 sum = vec4(0.0);
   vec2 tmp = gl_FragCoord.xy;

   sum += texture2D(u_glowTexture, vec2(v_texCoords.x - 4.0*blurSize, v_texCoords.y)) * 0.05;
   sum += texture2D(u_glowTexture, vec2(v_texCoords.x - 3.0*blurSize, v_texCoords.y)) * 0.1;
   sum += texture2D(u_glowTexture, vec2(v_texCoords.x - 2.0*blurSize, v_texCoords.y)) * 0.12;
   sum += texture2D(u_glowTexture, vec2(v_texCoords.x - blurSize, v_texCoords.y)) * 0.15;
   sum += texture2D(u_glowTexture, vec2(v_texCoords.x, v_texCoords.y)) * 0.16;
   sum += texture2D(u_glowTexture, vec2(v_texCoords.x + blurSize, v_texCoords.y)) * 0.15;
   sum += texture2D(u_glowTexture, vec2(v_texCoords.x + 2.0*blurSize, v_texCoords.y)) * 0.12;
   sum += texture2D(u_glowTexture, vec2(v_texCoords.x + 3.0*blurSize, v_texCoords.y)) * 0.1;
   sum += texture2D(u_glowTexture, vec2(v_texCoords.x + 4.0*blurSize, v_texCoords.y)) * 0.05;
 
   gl_FragColor = sum;
}