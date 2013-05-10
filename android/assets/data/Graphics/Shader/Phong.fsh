precision mediump float;

// Vertex attributes form the mesh. Transformed by the vertex shader.
varying vec3 normalV;
varying vec3 eyedirV;
varying vec3 lightdirV;
varying vec2 v_texCoord0;

struct PhongMaterial
{
   vec4 ambient;     // Acm
   vec4 diffuse;     // Dcm
   vec4 specular;    // Scm
   vec4 emissive;    // Ecm
   float shininess;  // Srm
};
uniform PhongMaterial u_PhongMaterial;
uniform sampler2D u_texture01;

vec3 lightintensity = vec3(0.9, 0.9, 0.9);

// Simple implementation of the phong illumination model.
vec4 phong(vec3 n, vec3 v, vec3 s, vec3 l, vec3 ka, vec3 kd, vec3 ks, float ke) {

  if(dot(v,n) <= 0.0) return vec4(0,0,0,0); // back-face

  // Normalization is good.
  n = normalize(n);
  //n = normalize(texture2D(normalmap, uvV).xyz*n);
  v = normalize(v);
  s = normalize(s);
  //s = normalize(texture2D(specularmap, uvV).xyz*s);

  // The ambient term.
  vec3 color = ka * l;

  float cosns = dot(n, s);
  if (cosns > 0.0) {
    // The diffuse term.
    color += (kd * l * cosns);

    // The specular term.
    vec3 r = 2.0 * (cosns * n) - s;
    float cosrv = dot(r, v);
    if (cosrv > 0.0)
      color += ks * l * pow(cosrv, ke);
  }

  return vec4(color,1.0);
}

void main() {
  vec4 phongColor = phong(normalV, eyedirV, lightdirV, lightintensity, u_PhongMaterial.ambient.rgb,texture2D(u_texture01, v_texCoord0).rgb, u_PhongMaterial.specular.rgb, u_PhongMaterial.shininess) ;
  gl_FragColor = phongColor;
}