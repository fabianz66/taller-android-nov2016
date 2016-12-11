package com.fabian.firmadigital;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * https://docs.oracle.com/javase/tutorial/security/apisign/gensig.html
 * https://developer.android.com/training/articles/keystore.html
 * <p>
 * Pasos:
 * 1. Crear u obtener claves publicas y privadas.
 * 2. Firmar el documento.
 */
public class FirmaDigital {

    public static String sign(String certPath,
                              char[] certPass,
                              String alias,
                              char[] alias_pass,
                              String docToSignPath,
                              String signaturePath,
                              String publicCertPath) {

        //El error en caso de haberlo.
        String error = null;

        try {

            //Obtiene la llave privada del certificado
            KeyStore ks = KeyStore.getInstance("PKCS12");
            FileInputStream ksfis = new FileInputStream(certPath);
            BufferedInputStream ksbufin = new BufferedInputStream(ksfis);
            ks.load(ksbufin, certPass);
            PrivateKey priv = (PrivateKey) ks.getKey(alias, alias_pass);

            //Crea la clase que se utiliza para firmar el documento
            Signature dsa = Signature.getInstance("SHA1withRSA");
            dsa.initSign(priv);

            //Abre el documento que se va a firmar
            FileInputStream fis = new FileInputStream(docToSignPath);
            BufferedInputStream bufin = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufin.read(buffer)) >= 0) {
                dsa.update(buffer, 0, len);
            }
            ;
            bufin.close();

            //Guarda la firma
            byte[] realSig = dsa.sign();
            FileOutputStream sigfos = new FileOutputStream(signaturePath);
            sigfos.write(realSig);
            sigfos.close();

            //Genera una llave publica para enviar al recipiente
            java.security.cert.Certificate cert = ks.getCertificate(alias);
            byte[] encodedCert = cert.getEncoded();
            FileOutputStream certfos = new FileOutputStream(publicCertPath);
            certfos.write(encodedCert);
            certfos.close();

        } catch (IOException e) {
            error = e.toString();
        } catch (KeyStoreException e) {
            error = e.toString();
        } catch (NoSuchAlgorithmException e) {
            error = e.toString();
        } catch (CertificateException e) {
            error = e.toString();
        } catch (UnrecoverableKeyException e) {
            error = e.toString();
        } catch (InvalidKeyException e) {
            error = e.toString();
        } catch (SignatureException e) {
            error = e.toString();
        }
        return error;
    }

    public static String verify(String publicCertPath, String signaturePath, String docToSignPath) {

        //El error en caso de haberlo.
        String error = null;

        try {

            //Get public key from certificate
            FileInputStream certfis = new FileInputStream(publicCertPath);
            java.security.cert.CertificateFactory cf = java.security.cert.CertificateFactory.getInstance("X.509");
            java.security.cert.Certificate cert = cf.generateCertificate(certfis);
            PublicKey pubKey = cert.getPublicKey();

            //Input the Signature Bytes
            //Input the signature bytes into a byte array named sigToVerify
            FileInputStream sigfis = new FileInputStream(signaturePath);
            byte[] sigToVerify = new byte[sigfis.available()];
            sigfis.read(sigToVerify);
            sigfis.close();

            //You can now proceed to do the verification.
            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(pubKey);

            //
            FileInputStream datafis = new FileInputStream(docToSignPath);
            BufferedInputStream bufin = new BufferedInputStream(datafis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
            }
            ;
            bufin.close();

            //
            boolean verifies = sig.verify(sigToVerify);
            System.out.println("signature verifies: " + verifies);
        } catch (IOException e) {
            error = e.toString();
        } catch (NoSuchAlgorithmException e) {
            error = e.toString();
        } catch (InvalidKeyException e) {
            error = e.toString();
        } catch (SignatureException e) {
            error = e.toString();
        } catch (CertificateException e) {
            error = e.toString();
        }
        return error;
    }
}