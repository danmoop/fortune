package Graphics;

import static Utils.Utilities.createBufferIndices;
import static Utils.Utilities.createFloatBuffer;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;

public class VertexArrayObject
{
    // VertexArrayObject is supposed to support drawing game object to game window

    private static final int VERTEX_ATTRIB = 0;
    private static final int TCOORD_ATTRIB = 1;

    public VertexArrayObject(float[] vertices, byte[] indices)
    {
        createArrayObject(vertices, indices);
    }

    public void createArrayObject(float[] vertices, byte[] indices)
    {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        createVerticesBuffer(vertices);
        createIndicesBuffer(indices);

        glBindVertexArray(0);
    }

    private void createVerticesBuffer(float[] vertices)
    {
        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vertices), GL_STATIC_DRAW);
        glVertexAttribPointer(VERTEX_ATTRIB, 3, GL_FLOAT, false, 0,0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void createIndicesBuffer(byte[] indices)
    {
        int ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, createBufferIndices(indices), GL_STATIC_DRAW);
    }
}