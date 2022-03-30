package exposed.hydrogen.resources;

import lombok.Getter;
import team.unnamed.creative.ResourcePack;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ResourcePackHandler {
    public static final Path RESOURCE_PACK_DIR = new File(Resources.getInstance().getDataFolder().getAbsolutePath() + "/temp/downloadedpack.zip").toPath();
    @Getter private ResourcePack resourcePack;
    @Getter protected boolean isResourcePackDownloaded = false;

    /**
     * Resource pack handler constructor, downloads the resource pack and loads it as ResourcePack.
     * @param url URL of the resource pack
     * @throws IOException if resource pack is not found
     * @throws NoSuchAlgorithmException if algorithm is not found (should never happen)
     */
    public ResourcePackHandler(URL url) throws IOException, NoSuchAlgorithmException {
        downloadResourcePack(url);
        resourcePack = new ResourcePack(new FileInputStream(RESOURCE_PACK_DIR.toFile()).readAllBytes(), getSHA1Hash(RESOURCE_PACK_DIR.toFile()));
    }

    public void setResourcePack(ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        try {
            Files.copy(new ByteArrayInputStream(resourcePack.bytes()), RESOURCE_PACK_DIR, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadResourcePack(URL url) throws IOException {
        Resources.getInstance().getLogger().info("Downloading resource pack...");

        InputStream packStream = url.openStream();
        Files.copy(packStream, RESOURCE_PACK_DIR, StandardCopyOption.REPLACE_EXISTING);
        packStream.close();
    }

    /**
     * This code is completely generated by github copilot. I have no idea what it does.
     * @param file file to get hash from
     * @return hash of the file in string form
     * @throws IOException if file is not found
     * @throws NoSuchAlgorithmException if algorithm is not found (should never happen)
     */
    public static String getSHA1Hash(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(Files.readAllBytes(file.toPath()));
        return bytesToHex(md.digest());
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
