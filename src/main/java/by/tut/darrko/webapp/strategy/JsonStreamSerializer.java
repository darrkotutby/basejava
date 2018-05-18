package by.tut.darrko.webapp.strategy;

import by.tut.darrko.webapp.model.Resume;
import by.tut.darrko.webapp.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements SerializationMethod {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (java.io.Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            JsonParser.write(r, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (java.io.Reader readerWriter = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return JsonParser.read(readerWriter, Resume.class);
        }
    }
}

