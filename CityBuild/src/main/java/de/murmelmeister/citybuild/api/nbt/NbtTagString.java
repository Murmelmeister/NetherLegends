package de.murmelmeister.citybuild.api.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NbtTagString extends NbtTag {
    private String value;

    public NbtTagString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeUTF(value);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        value = in.readUTF();
    }
}
