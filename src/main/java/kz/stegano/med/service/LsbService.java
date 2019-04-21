package kz.stegano.med.service;

public interface LsbService {
    byte[] encode(byte[] image, String plainText);
    String decode(byte[] image);
}
