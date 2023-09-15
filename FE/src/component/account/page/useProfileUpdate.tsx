import { useState, useEffect } from "react";

export const useProfileUpdate = () => {
  const [imgInfo, setImgInfo] = useState<FileList | null>(null);
  const [beforeCropUrl, setBeforeCropUrl] = useState<string | null>(null);
  const [isChanged, setIsChanged] = useState(false);
  useEffect(() => {
    if (imgInfo && imgInfo.length > 0) {
      setBeforeCropUrl(URL.createObjectURL(imgInfo[0]));
    } else {
      setBeforeCropUrl(null);
    }
  }, [imgInfo]);
  return { imgInfo, setImgInfo, beforeCropUrl, isChanged, setIsChanged };
};
