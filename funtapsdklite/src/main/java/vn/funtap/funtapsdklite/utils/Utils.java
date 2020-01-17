package vn.funtap.funtapsdklite.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Utils {

    public static String decryptionDataECB(byte[] encrypted) {
        try {
            byte[] key = k().getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(key, "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] decrypted = cipher.doFinal(encrypted);
            String decrytionToString = new String(decrypted, "UTF-8");
            return decrytionToString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static final String K1 = "aG4U5ZLJAR7IkCOdrku2UlfvLRVRyl07juehXuOK0XNax9YnVvI=";
    private static final String P2 = "DAF6kfGhYHCv9Vf1xzjBPSWLXnE+pClY5obPOYb+uRopsLlVMoE=";

    private static String k() {
        byte[] x0 = Base64.decode(P2, 0);
        byte[] x1 = Base64.decode(K1, 0);

        byte[] x = new byte[x0.length];
        for (int i = 0; i < x1.length; i++) {
            x[i] = (byte) (x0[i] ^ x1[i]);
        }
        return new String(x);
    }
}
