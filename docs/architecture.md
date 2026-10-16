# Architecture

`PrismMesh` creates a conforming equilateral triangle grid. Adjacent faces share the same base coordinates. Each vertex carries all three corners of its face and a barycentric coordinate. The buffer is uploaded once to a GLES2 vertex buffer.

The vertex shader displaces the three corners with a continuous wave field, computes their flat face normal and projects the vertex. Adjacent triangles remain joined because the displacement is a function of the shared coordinate and elapsed scene time. The fragment shader uses view-dependent studio reflection lobes, metal tint, Fresnel response and thin barycentric seams. These are procedural reflections, not a claim of physically exact ray tracing.

A native Canvas overlay draws actual local time and optional date. Its bounded multi-frequency drift keeps the clock moving without frame-edge jumps. A 33-millisecond scheduler requests redraws while the scene is active. This is a scheduling target, not proof of displayed frame rate. Elapsed animation steps are capped after delays or resume so a stalled frame does not cause a large leap.

`PreviewActivity` and `PrismDreamService` share `PrismSceneView`. Activity pause and Dream stop remove callbacks and pause the GL surface. Detach also stops scheduling. Android owns the surface dimensions; no 4K buffer is forced. Context loss causes the shader and static buffer to be recreated.

`PrismOptions` resolves persisted values and random choices on each scene start. Settings and its provider use the same schema. There are no runtime libraries, content downloads, wake locks, foreground services or background update checks.
