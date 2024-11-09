package de.murmelmeister.citybuild.api.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NbtTagInt extends NbtTag {
    private int value;

    public NbtTagInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(value);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        value = in.readInt();
    }
}
