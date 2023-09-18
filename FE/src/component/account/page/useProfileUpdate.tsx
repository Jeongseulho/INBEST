import { useState, useEffect } from "react";
import { useRef } from "react";
import { ReactCropperElement } from "react-cropper";
import { checkNickname } from "../../../api/account";
import { toast } from "react-toastify";
import { AxiosError } from "axios";
export const useProfileUpdate = () => {
  // input에 담은 이미지 정보
  const [imgInfo, setImgInfo] = useState<FileList | null>(null);
  // 자르기전 이미지 url(input에 담은거 변환, cropper에 보여줘야됨)
  const [beforeCropUrl, setBeforeCropUrl] = useState<string | null>(null);
  // input파일 바뀌었는지(copper 보여줄지 결정하는 함수)
  const [isChanged, setIsChanged] = useState(false);
  // 잘랐는지
  const [isCropped, setIsCropped] = useState(false);
  const cropperRef = useRef<ReactCropperElement>(null);
  const selectImg = useRef<HTMLInputElement | null>(null);
  const [cropImg, setCropImg] = useState<string | undefined>("");

  // 닉네임 바뀌었는데 중복검사 안했으면 못바꾸게 해야됨
  const [isChangedNickname, setIsChangedNickname] = useState(false);
  const [isCheckedNickname, setIsCheckedNickname] = useState(false);

  // 닉네임 중복확인
  const onCheckNickname = async (nickname: string) => {
    if (!/^[a-zA-Z0-9가-힣ぁ-んァ-ンー]*$/.test(nickname)) {
      toast.error("특수문자는 사용할 수 없습니다.");
      return;
    }
    try {
      await checkNickname(nickname);
      toast.error("이미 존재하는 닉네임입니다");
    } catch (err: unknown) {
      const { status } = (err as AxiosError).response!;
      switch (status) {
        case 404:
          toast.success("사용가능한 닉네임입니다");
          setIsCheckedNickname(true);
          break;
        default:
          setIsCheckedNickname(false);
          toast.error("문제가 발생했습니다. 다시 시도해 주세요.");
          break;
      }
    }
  };

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
      setIsChanged(false);
    });
  };

  // copper에 취소 버튼
  const onCancel = () => {
    setIsChanged(false);
  };
  const [isDefaultImg, setIsDefaultImg] = useState(false);

  useEffect(() => {
    if (imgInfo && imgInfo.length > 0) {
      setBeforeCropUrl(URL.createObjectURL(imgInfo[0]));
    } else {
      setBeforeCropUrl(null);
    }
  }, [imgInfo]);

  useEffect(() => {});
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
    onCancel,
    setIsDefaultImg,
    isDefaultImg,
    onCheckNickname,
    isCheckedNickname,
    setIsChangedNickname,
  };
};
