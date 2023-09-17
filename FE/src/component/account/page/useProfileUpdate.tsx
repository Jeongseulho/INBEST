import { useState, useEffect } from "react";
import { useRef } from "react";
import { ReactCropperElement } from "react-cropper";
export const useProfileUpdate = () => {
  const [imgInfo, setImgInfo] = useState<FileList | null>(null);
  const [beforeCropUrl, setBeforeCropUrl] = useState<string | null>(null);
  const [isChanged, setIsChanged] = useState(false);
  const [isCropped, setIsCropped] = useState(false);
  const cropperRef = useRef<ReactCropperElement>(null);
  const selectImg = useRef<HTMLInputElement | null>(null);
  const [cropImg, setCropImg] = useState<string | undefined>("");
  const onCrop = () => {
    const cropper = cropperRef.current?.cropper;
    cropper?.getCroppedCanvas().toBlob((blob) => {
      const formData = new FormData();
      formData.append("file", blob!);
      console.log(blob);
      console.log(formData);
      for (const [value] of formData.entries()) {
        console.log(value);
      }
      setCropImg(cropperRef.current?.cropper.getCroppedCanvas().toDataURL());
      setIsCropped(true);
    });
  };
  useEffect(() => {
    if (imgInfo && imgInfo.length > 0) {
      setBeforeCropUrl(URL.createObjectURL(imgInfo[0]));
    } else {
      setBeforeCropUrl(null);
    }
  }, [imgInfo]);
  return {
    imgInfo,
    setImgInfo,
    beforeCropUrl,
    isChanged,
    setIsChanged,
    selectImg,
    onCrop,
    cropperRef,
    cropImg,
    isCropped,
  };
};
