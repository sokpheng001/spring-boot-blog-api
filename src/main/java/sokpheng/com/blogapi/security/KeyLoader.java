package sokpheng.com.blogapi.security;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;


import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


@Getter
@Component
public class KeyLoader {

    @Value("${jwt.private-key:NOT_FOUND}")
    private String privateKeyPath;

    @Value("${jwt.public-key}")
    public String publicKeyPath;


    private final   ResourceLoader resourceLoader;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeyLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws Exception {
        this.privateKey = loadPrivateKey(privateKeyPath);
        this.publicKey = loadPublicKey(publicKeyPath);
    }

    /**
     * <p>Load the private key from file</p>
     * @return
     * @throws Exception
     */
    private PrivateKey loadPrivateKey(String location) throws Exception {
        // 1. Actually LOAD the file from the path
        Resource resource = resourceLoader.getResource(location);

        try (InputStream is = resource.getInputStream()) {
            String content = new String(is.readAllBytes());

            // 2. Clean the string
            String privateKeyPEM = content
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", ""); // Removes all newlines and spaces

            // 3. Decode the actual file bytes
            byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(keySpec);
        }
    }

    /**
     * <p>Load public key from file</p>
     * @param location
     * @return
     * @throws Exception
     */
    private PublicKey loadPublicKey(String location) throws Exception {
        Resource resource = resourceLoader.getResource(location);
        try (InputStream is = resource.getInputStream()) {
            String key = new String(is.readAllBytes())
                    .replaceAll("-----\\w+ PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] decoded = Base64.getDecoder().decode(key);
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        }
    }

}