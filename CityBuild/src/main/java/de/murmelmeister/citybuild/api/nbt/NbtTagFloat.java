package de.murmelmeister.citybuild.api.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NbtTagFloat extends NbtTag {
    private float value;

    public NbtTagFloat(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeFloat(value);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        value = in.readFloat();
    }
}
