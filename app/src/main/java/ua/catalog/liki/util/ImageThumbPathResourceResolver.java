package ua.catalog.liki.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageThumbPathResourceResolver extends PathResourceResolver {

    private final int width;
    private final int height;
    private Resource watermark;

    public ImageThumbPathResourceResolver(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    public ImageThumbPathResourceResolver(int width, int height, Resource watermark) {
        super();
        this.width = width;
        this.height = height;
        this.watermark = watermark;
    }

    @Override
    protected Resource getResource(String resourcePath, Resource location) throws IOException {

        Path path = Paths.get(location.getURL().getPath(), resourcePath);

        if (path.toFile().exists()) {
            return super.getResource(resourcePath, location);
        }

        Path originalPath = Paths.get(FilenameUtils.concat(path.toString(), "../../" + resourcePath));

        if (!originalPath.toFile().exists()) {
            return null;
        }
        return create(originalPath, path) ? super.getResource(resourcePath, location) : null;
    }

    private boolean create(Path origin, Path output) throws IOException {

        Path directory = Paths.get(FilenameUtils.concat(output.toString(), "../"));

        if (!directory.toFile().exists()) {
            if (!directory.toFile().mkdir()) {
                return false;
            }
        }

        var thumb = Thumbnails.of(origin.toFile());

        if (watermark != null  && watermark.getInputStream().available() > 0) {
            BufferedImage watermarkImage = ImageIO.read(watermark.getInputStream());
            thumb.watermark(Positions.CENTER, watermarkImage, 0.5f);
        }

        thumb.size(width, height)
                .toFile(output.toFile());

        return true;

    }

}
