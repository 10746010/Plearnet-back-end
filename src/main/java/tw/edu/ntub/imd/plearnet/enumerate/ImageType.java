package tw.edu.ntub.imd.plearnet.enumerate;

public enum ImageType {
    PNG, JPG, GIF, BMP, SVG;

    public static ImageType getByFormatName(String formatName) {
        switch (formatName.toLowerCase()) {
            case "png":
                return PNG;
            case "jpg":
            case "jpeg":
            case "jpe":
            case "jif":
            case "jfif":
            case "jfi":
                return JPG;
            case "gif":
                return GIF;
            case "bmp":
                return BMP;
            case "svg":
                return SVG;
            default:
                throw new IllegalArgumentException("未知圖片類型: " + formatName);
        }
    }
}
