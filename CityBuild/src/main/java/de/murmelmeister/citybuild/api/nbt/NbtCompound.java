package de.murmelmeister.citybuild.api.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NbtCompound extends NbtTag {
    private final Map<String, NbtTag> tags = new HashMap<>();

    public void setTag(String key, NbtTag tag) {
        tags.put(key, tag);
    }

    public NbtTag getTag(String key) {
        return tags.get(key);
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        for (Map.Entry<String, NbtTag> entry : tags.entrySet()) {
            out.writeUTF(entry.getKey());
            entry.getValue().write(out);
        }
    }

    @Override
    public void read(DataInputStream in) throws IOException, EOFException {
        while (in.available() > 0) {
            String key = in.readUTF();
            if (key.equals("lastKnownName")) {
                NbtTagString tag = new NbtTagString("");
                tag.read(in);
                tags.put(key, tag);
            } else if (key.equals("Health")) {
                NbtTagInt tag = new NbtTagInt(0);
                tag.read(in);
                tags.put(key, tag);
            }
        }
    }
}
