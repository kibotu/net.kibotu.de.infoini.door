// Uniform variables. Set from the application.
uniform mat4 u_ModelView;
uniform mat4 u_WorldView; // The inverse camera transform.
uniform mat4 u_ProjectionWorldView;

// Light Source Data for a directional light
struct LightSource {
    vec3  ambient;
    vec3  diffuse;
    vec3  specular;
    int  type;
    vec3 position;
    vec3 direction;
    bool isOn;
} ;
uniform LightSource u_DirectionalLight;

// Vertex attributes form the mesh.
attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

// Output for the fragment shader.
varying vec3 normalV;
varying vec3 eyedirV;
varying vec3 lightdirV;
varying vec4 fposition;
varying vec2 v_texCoord0;

// The main entry point.
void main() {

  // position
  vec4 position =  u_ProjectionWorldView *  u_ModelView * vec4(a_position, 1);

  // Transform to camera coordinates.
  normalV = (u_ProjectionWorldView * u_ModelView * vec4(a_normal, 0.0)).xyz;

  // uv
  v_texCoord0 = a_texCoord0;

  // Calculate values in camera coordinates.
  eyedirV = -position.xyz;
  lightdirV = u_DirectionalLight.position - position.xyz;

  // Transform the vertex position to NDC.
  fposition = position;
  gl_Position = fposition;
}
