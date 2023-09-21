import { useState, useEffect } from "react";
import { useRef } from "react";
import { ReactCropperElement } from "react-cropper";
import { checkNickname, upadateUserInfo, changeDefaultImg } from "../../../api/account";
import { toast } from "react-toastify";
import { AxiosError } from "axios";
import { UpdateUser } from "../../../type/Accounts";
import userStore from "../../../store/userStore";
export const useProfileUpdate = () => {
  const { userInfo, setUserInfo } = userStore();
  // input에 담은 이미지 정보
  const [imgInfo, setImgInfo] = useState<File | null>(null);
  // 자르기전 이미지 url(input에 담은거 변환, cropper에 보여줘야됨)
  const [beforeCropUrl, setBeforeCropUrl] = useState<string | null>(null);
  // input파일 바뀌었는지(copper 보여줄지 결정하는 함수)
  const [isChanged, setIsChanged] = useState(false);
  // 잘랐는지
  const [isCropped, setIsCropped] = useState(false);
  const cropperRef = useRef<ReactCropperElement>(null);
  const selectImg = useRef<HTMLInputElement | null>(null);
  const [cropImg, setCropImg] = useState<string | undefined>("");
  const [croppedImgBlob, setCroppedImgBlob] = useState<Blob | null>(null);
  // 닉네임 바뀌었는데 중복검사 안했으면 못바꾸게 해야됨
  const [isChangedNickname, setIsChangedNickname] = useState(false);
  const [isCheckedNickname, setIsCheckedNickname] = useState(false);

  // 닉네임 중복확인
  const onCheckNickname = async (nickname: string) => {
    console.log(nickname);
    console.log(/^[a-zA-Z0-9가-힣ぁ-んァ-ンー]*$/.test(nickname));
    if (nickname === "") {
      toast.error("닉네임을 입력해 주세요.");
      return;
    }
    if (!/^[a-zA-Z0-9가-힣ぁ-んァ-ンー]*$/.test(nickname)) {
      toast.error("특수문자는 사용할 수 없습니다.");
      return;
    }
    try {
      await checkNickname(nickname);
      return toast.error("이미 존재하는 닉네임입니다");
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
  // 닫으면 다 리셋해야됨
  const onReset = () => {
    setImgInfo(null);
    // 자르기전 이미지 url(input에 담은거 변환, cropper에 보여줘야됨)
    setBeforeCropUrl(null);
    // input파일 바뀌었는지(copper 보여줄지 결정하는 함수)
    setIsChanged(false);
    // 잘랐는지
    setIsCropped(false);
    setCropImg("");
    // 닉네임 바뀌었는데 중복검사 안했으면 못바꾸게 해야됨
    setIsChangedNickname(false);
    setCroppedImgBlob(null);
    setIsCheckedNickname(false);
  };
  const onCrop = () => {
    const cropper = cropperRef.current?.cropper;
    cropper?.getCroppedCanvas().toBlob((blob) => {
      setCroppedImgBlob(blob);
    }, "image/png");
    setCropImg(cropperRef.current?.cropper.getCroppedCanvas().toDataURL());
    setIsCropped(true);
    setIsChanged(false);
  };

  // copper에 취소 버튼
  const onCancel = () => {
    setIsChanged(false);
  };
  const [isDefaultImg, setIsDefaultImg] = useState(false);

  // 최종 제출
  const onUpdate = async (data: UpdateUser) => {
    console.log(data);
    console.log(croppedImgBlob);
    if (isChangedNickname && !isCheckedNickname) {
      return toast.error("닉네임 중복검사를 완료해 주세요.");
    }
    const formData = new FormData();
    if (croppedImgBlob) {
      formData.append("file", croppedImgBlob, "tmp.png");
    }

    formData.append("nickname", data.nickname);
    formData.append("gender", data.gender.toString());
    if (data.birth) formData.append("birth", data.birth);
    for (const [key, value] of formData.entries()) {
      console.log(key, value);
    }
    try {
      if (isDefaultImg) {
        await changeDefaultImg(userInfo!.seq);
      }
      const res = await upadateUserInfo(userInfo!.seq, formData);

      setUserInfo({ ...userInfo!, profileImgSearchName: res.UserInfo.profileImgSearchName });
      console.log(res);
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    if (imgInfo) {
      setBeforeCropUrl(URL.createObjectURL(imgInfo));
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
    onReset,
    isChangedNickname,
    onUpdate,
  };
};
