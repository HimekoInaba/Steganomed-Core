package kz.stegano.med.service.impl;

import kz.stegano.med.service.LsbService;
import org.springframework.stereotype.Service;

@Service
public class LsbServiceImpl implements LsbService {
    private static final int[] mask = {128, 64, 32, 16, 8, 4, 2, 1};

    @Override
    public byte[] encode(byte[] image, String message) {
        byte[] plainTextByte = message.getBytes();
        if (plainTextByte.length * 8 > image.length - 54) {
            return null;
        }

        for (int i = 0; i < plainTextByte.length; i++) {
            int index = (55 + i * 8);
            byte b = plainTextByte[i];

            for (int j = 0; j < 8; j++) {
                if ((b & mask[j]) == 0) {
                    image[index + j] = setLsb(0, image[index + j]);
                } else {
                    image[index + j] = setLsb(1, image[index + j]);
                }
            }
            if (i == message.length() - 1) {
                for (byte j = 8; j < 16; j++) {
                    image[index + j] = setLsb(0, image[index + j]);
                }
            }
        }

        return image;
    }

    @Override
    public String decode(byte[] image) {
        StringBuilder message = new StringBuilder();
        for (int i = 55; i < image.length - 9; i += 8) {
            byte letter = 0;
            for (int j = 0; j < 8; j++) {
                byte b = image[i + 7];
                if (b % 2 == 0) {
                    letter = (byte) (letter & (~1));
                } else {
                    letter = (byte) (letter | 1);
                }

                if (j != 7) {
                    letter = (byte) (letter << 1);
                }
            }

            if (letter == 0) {
                break;
            }

            message.append((char)letter);
        }

        return String.valueOf(message);
    }

    private byte setLsb(int i, byte b) {
        if (i != 0) {
            b |= 1;
        } else {
            b &= (~1);
        }
        System.out.println(b);
        return b;
    }
}

